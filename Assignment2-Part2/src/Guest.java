import java.util.Random;

public class Guest extends Thread
{
    public int guestNum;
    private Random random = new Random();
    public boolean interestInVase = true;

    public Guest (int guestNum)
    {
        this.guestNum = guestNum + 1;
        this.setName("Guest " + this.guestNum);
    }

    public void run()
    {
        while (interestInVase)
        {
            App.queue.enq(this);

            synchronized (App.vaseRoom)
            {
                while(VaseRoom.guestCanEnter != this.guestNum)
                {
                    try {
                        App.vaseRoom.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                enterRoom();
                exitRoom();
            }
        }
    }

    private void enterRoom()
    {
        VaseRoom.guestCanEnter = -1;
    }

    private void exitRoom()
    {
        // Alert the next guest in line to enter the vase room
        try {
            App.queue.deq();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Guest can decide if they would like to re-enter the queue
        if (App.queue.size() != 0)
            interestInVase = random.nextBoolean();
        else
            // Handles case where the last guest in line attempts to reenter the queue but 
            // has no-one else to notify, preventing the program from continuing
            interestInVase = false;
    }
}
