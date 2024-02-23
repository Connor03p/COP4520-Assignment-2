class Minotaur extends Thread 
{
   private Labyrinth labyrinth;

   public Minotaur(Labyrinth labyrinth) 
   {
      this.labyrinth = labyrinth;
      this.setName("Minotaur");
   }

   @Override
   public void run() 
   {
      while (true) {
         try {
            synchronized (labyrinth) 
            {
               // Wait until labyrinth is empty
               while (!labyrinth.getIsEmpty())
                  labyrinth.wait();

               // If the guests claim everyone entered, stop
               if (labyrinth.getEveryoneEntered())
                  return;

               // Invite a guest into the labyrinth
               labyrinth.setIsEmpty(false);
               labyrinth.notify();
            }
         } catch (InterruptedException ex) {
            return;
         }
      }
   }
}