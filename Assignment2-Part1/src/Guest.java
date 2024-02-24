class Guest extends Thread {
    private final Labyrinth labyrinth;
    private final int guestNum;
    public int timesEaten = 0;
    private boolean leftCupcake = false;
    public int timesEntered = 0;

    public Guest(Labyrinth labyrinth, int guestNum) {
        this.labyrinth = labyrinth;
        this.guestNum = guestNum + 1;
        this.setName("Guest " + this.guestNum);
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (labyrinth) {
                    while (labyrinth.getIsEmpty()) {
                        labyrinth.wait();
                    }

                    if (labyrinth.getEveryoneEntered())
                        return;

                    // Enter labyrinth
                    timesEntered++;

                    // Reach the end
                    CupcakeDecision();

                    // Exit
                    if (!labyrinth.getEveryoneEntered())
                        labyrinth.setIsEmpty(true);
                    else
                        App.PartyOver();

                    labyrinth.notifyAll();
                }
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    private void CupcakeDecision() {
        // Guest 1 keeps track of how many people have entered the labyrinth 
        // using the number of cupcakes they have eaten
        if (guestNum == 1) {
            if (!labyrinth.getHasCupcake())
                return;

            labyrinth.setHasCupcake(false);
            timesEaten++;

            if (timesEaten >= App.NUMBER_OF_GUESTS) {
                App.PartyOver();
            }
        } else {
            if (!leftCupcake && !labyrinth.getHasCupcake()) {
                // Request a cupcake, but don't eat it
                labyrinth.setHasCupcake(true);
                leftCupcake = true;
            }
        }
    }
}