import java.util.ArrayList;

public class App
{
   public static final int NUMBER_OF_GUESTS = 15;
   public static ArrayList<Guest> guests = new ArrayList<Guest>();
   private static Minotaur minotaur;
   private static double time;

   public static void main(String[] args)
   {
      Labyrinth labyrinth = new Labyrinth();
      time = System.nanoTime();
      
      minotaur = new Minotaur(labyrinth);
      minotaur.start();

      for (int i = 0; i < NUMBER_OF_GUESTS; i++) 
      {
         guests.add(new Guest(labyrinth, i));
         guests.get(i).start();
      }
   }

   public static void PartyOver()
   {
      // Interrupt all threads
      minotaur.interrupt();
      for (int i = 0; i < NUMBER_OF_GUESTS; i++)
         guests.get(i).interrupt();

      // Stop the timer
      time = ((System.nanoTime() - time) / 1000000000.0);

      // Print the results
      System.out.println("\nThe Party is over");
      int guestsEntered = 0;
      for (int i = 0; i < NUMBER_OF_GUESTS; i++)
      {
         if (guests.get(i).timesEntered > 0)
            guestsEntered++;
         System.out.println("  " + guests.get(i).getName() + ": Entered " + guests.get(i).timesEntered + " times, ate " + guests.get(i).timesEaten + " cupcakes.");
      }
      System.out.println("  " + guestsEntered + "/" + NUMBER_OF_GUESTS + " guests entered the labyrinth");
      System.out.println("  The party lasted " + time + " seconds");      
   }
}