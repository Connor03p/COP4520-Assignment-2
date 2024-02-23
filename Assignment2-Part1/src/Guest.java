class Guest extends Thread 
{
    private final Labyrinth labyrinth;
    private final int guestNum;
    public int timesEaten = 0;
    private boolean leftCupcake = false;
    public int timesEntered = 0;

    public Guest(Labyrinth labyrinth, int guestNum) 
    {
        this.labyrinth = labyrinth;
        this.guestNum = guestNum + 1;
        this.setName("Guest " + this.guestNum);
    }

    @Override
    public void run() 
    {
        while (true) 
        {
            try {
                consume();
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    private void consume() throws InterruptedException 
    {
        synchronized (labyrinth) 
        {
            while (labyrinth.getIsEmpty()) 
            {
                //System.out.println(Thread.currentThread().getName() + " is waiting, size: " + taskQueue.size());
                labyrinth.wait();
            }

            if (labyrinth.getEveryoneEntered())
                return;

            // Enter labyrinth
            //System.out.println(Thread.currentThread().getName() + " recieved invitation");
            timesEntered++;

            // Navigate the labyrinth

            // Reach the end
            CupcakeDecision();

            // Exit
            if (!labyrinth.getEveryoneEntered())
                labyrinth.setIsEmpty(true); 
            else
                App.PartyOver();    
                  
            labyrinth.notifyAll();
        }
    }

    private void CupcakeDecision()
    {
        // Guest 1 keeps track of how many people have entered the labyrinth using the number of cupcakes
        if (guestNum == 1)
        {
            if (!labyrinth.getHasCupcake())
                return;

            labyrinth.setHasCupcake(false);
            timesEaten++;

            //System.out.println("  " + this.getName() + " counts " + timesEaten + " guests have entered.");

            if (timesEaten >= App.NUMBER_OF_GUESTS)
            {
                //System.out.println("  " + this.getName() + " thinks everyone has entered and alerted minotaur.");
                App.PartyOver();
            }
        }
        else
        {
            if (!leftCupcake && !labyrinth.getHasCupcake())
            {
                // Request a cupcake, but don't eat it
                labyrinth.setHasCupcake(true);
                leftCupcake = true;
                //System.out.println("  " + this.getName() + " requested a cupcake for guest 1");
            }     
        }
    }
}