import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Only one guest can be in the vase room at a time. Allow the quests to line in
 * a queue.
 * Every guest exiting the room was responsible to notify the guest standing in
 * front of the
 * queue that the room is available. Guests were allowed to queue multiple
 * times.
 */

public class App 
{
    public static final int NUMBER_OF_GUESTS = 100;
    public static final boolean PRINT_QUEUE = false;
    public static ArrayList<Guest> guests = new ArrayList<Guest>();
    public static LockBasedQueue queue = new LockBasedQueue(NUMBER_OF_GUESTS + 1);
    public static VaseRoom vaseRoom = new VaseRoom();
    private static double time;

    public static void main(String[] args) 
    {
        // Start the timer
        time = System.nanoTime();

        // Create a thread for each guest
        for (int i = 0; i < NUMBER_OF_GUESTS; i++) 
        {
            guests.add(new Guest(i));
            guests.get(i).start();
        }

        // Notify the first guest to start the line
        Guest nextGuest = null;
        while (nextGuest == null)
            nextGuest = queue.deq();

        // Wait for all the guests to finish
        for (int i = 0; i < NUMBER_OF_GUESTS; i++) 
        {
            try 
            {
                guests.get(i).join();
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }

        time = ((System.nanoTime() - time) / 1000000000.0);
        System.out.println("\nFinished in " + time + " seconds.");
    }
}

class LockBasedQueue
{
    int head, tail;
    Guest[] items;
    ReentrantLock lock;

    public LockBasedQueue(int capacity) 
    {
        head = 0;
        tail = 0;
        lock = new ReentrantLock();
        items = new Guest[capacity];
    }

    public int size()
    {
        return tail-head;
    }

    public void printQueue(String msg)
    {
        if (!App.PRINT_QUEUE) return;

        System.out.print("\nQueue (" + msg + "):\n"
        + "  Head: " + head + "\n"
        + "  Tail: " + tail + "\n"
        + "  Items: ");
        
        for (int i = head; i < tail; i++)
        {
            System.out.print(items[i % items.length].guestNum + ", ");
        }
        System.out.print("\n");
    }

    public Guest deq()
    {
        lock.lock();
        try 
        {
            // Return if the list is empty
            if (tail == head)
                return null;
            
            // Get the last guest that entered the queue
            Guest x = items[head % items.length];

            // Remove that guest from the queue
            items[head % items.length] = null;
            head++;
            
            // Notify the guest to enter the vase room
            synchronized(App.vaseRoom)
            {
                VaseRoom.guestCanEnter = x.guestNum;
                App.vaseRoom.notifyAll();
            }
            
            return x;
        } 
        finally 
        {
            printQueue("deq");
            lock.unlock();
        }
    }

    public void enq(Guest x)
    {
        lock.lock();
        try
        {
            // Add the guest to the queue
            items[tail % items.length] = x;
            tail++;
        }
        finally
        {
            printQueue("enq");
            lock.unlock();
        }
    }

}
