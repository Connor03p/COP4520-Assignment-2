# COP4520 Assignment 2
## Problem 1
Minotaur’s Birthday Party (50 points): The Minotaur invited N guests to his birthday party. When the guests arrived, he made the following announcement. The guests may enter his labyrinth, one at a time and only when he invites them to do so. At the end of the labyrinth, the Minotaur placed a birthday cupcake on a plate. When a guest finds a way out of the labyrinth, he or she may decide to eat the birthday cupcake or leave it. If the cupcake is eaten by the previous guest, the next guest will find the cupcake plate empty and may request another cupcake by asking the Minotaur’s servants. When the servants bring a new cupcake the guest may decide to eat it or leave it on the plate. The Minotaur’s only request for each guest is to not talk to the other guests about her or his visit to the labyrinth after the game has started. The guests are allowed to come up with a strategy prior to the beginning of the game. There are many birthday cupcakes, so the Minotaur may pick the same guests multiple times and ask them to enter the labyrinth. Before the party is over, the Minotaur wants to know if all of his guests have had the chance to enter his labyrinth. To do so, the guests must announce that they have all visited the labyrinth at least once. Now the guests must come up with a strategy to let the Minotaur know that every guest entered the Minotaur’s labyrinth. It is known that there is already a birthday cupcake left at the labyrinth’s exit at the start of the game. How would the guests do this and not disappoint his generous and a bit temperamental host? Create a program to simulate the winning strategy (protocol) where each guest is represented by one running thread. In your program you can choose a concrete number for N or ask the user to specify N at the start.

#

The solution to this problem is to assign one guest as a counter to keep track of how many guests have entered the labyrinth. When another guest enters the labyrinth and the plate is empty, they may leave a cupcake for the first guest if they have not already done so. The counter will eventually eat one cupcake from every guests, and alert the minotaur that everyone has entered when that happens. While this solution is inefficient for a large number of guests, it guarentees that the counter will eventually and correctly claim that everyone has entered.

<br>

## Problem 2
Minotaur’s Crystal Vase (50 points): The Minotaur decided to show his favorite crystal vase to his guests in a dedicated showroom with a single door. He did not want many guests to gather around the vase and accidentally break it. For this reason, he would allow only one guest at a time into the showroom. He asked his guests to choose from one of three possible strategies for viewing the Minotaur’s favorite crystal vase:

1) Any guest could stop by and check whether the showroom’s door is open at any time and try to enter the room. While this would allow the guests to roam around the castle and enjoy the party, this strategy may also cause large crowds of eager guests to gather around the door. A particular guest wanting to see the vase would also have no guarantee that she or he will be able to do so and when.
2) The Minotaur’s second strategy allowed the guests to place a sign on the door indicating when the showroom is available. The sign would read “AVAILABLE” or “BUSY.” Every guest is responsible to set the sign to “BUSY” when entering the showroom and back to “AVAILABLE” upon exit. That way guests would not bother trying to go to the showroom if it is not available.
3) The third strategy would allow the quests to line in a queue. Every guest exiting the room was responsible to notify the guest standing in front of the queue that the showroom is available. Guests were allowed to queue multiple times.

Which of these three strategies should the guests choose? Please discuss the advantages and disadvantages. Implement the strategy/protocol of your choice where each guest is represented by 1 running thread. You can choose a concrete number for the number of guests or ask the user to specify it at the start.

#

The first strategy is similar to a test-and-set system. Guests will keep attempting to enter until the minotaur lets them in. However this system becomes very inefficient for large number of guests.

The second strategy is similar to a test-and-test-and-set system. Guests must check the sign first before attempting to enter. This will cause fewer guests to attempt to enter at the same time, reducing the time it takes for the minotaur to pick a guests. Better than the first strategy, but will still become more inefficient as the number of guests increases.

The third strategy is the best strategy overall. Guests are let in in a specific order, and any guest who would like to enter again must go to the back of the queue. However, some special cases have to be accounted for with these rules. The minotaur must notify the first guest in the queue to enter to begin the queue. The last person in the queue may not re-enter the vase room, as there will be no-one else in line to continue the queue.
