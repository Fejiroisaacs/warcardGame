// Author name - Oghenefejiro Isaacs Anigboro
/** Pre-condition --- cards Array contains a standard deck of 52 cards, duplicateArr ArrayList is empty.
*/
/** Post-condition --- p1CollectedCard and p2CollectedCard ArrayLists contain a total of 52 cards. A winner
 is decided when either player collects every card from their opponent or which player has the most card after 100000 runs. 
*/

import java.util.*;

class Main {

  // creating instance variables for most important variables that are going to be changed in different methods to avoid error resulting from one method not being able to get the exact value/element when needed.
  private static ArrayList<String> duplicateArr = new ArrayList<String>();
  private static int p1Val;
  private static int p2Val;

  public static void main(String[] args) {
    //creating an ArrayList, allCards and an Array, cards which stores the deck of cards.
    //the ArrayList is created because cards are randomly taken form the ArrayList and moved to another ArrayList
    //either p1Cards or p2Cards. 
    ArrayList<String> allCards = new ArrayList<String>();
    String[] cards = { "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A", "K", "Q", "J", "10", "9",
        "8", "7", "6", "5", "4", "3", "2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A", "K",
        "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" };
    //for-each loop that adds all the cards in the array to the arraylist
    for (String i : cards) {
      allCards.add(i);
    }
    //cards are randomly drawn from allCards, removed and placed in either p1Cards or p2Cards
    int decider = 1;
    ArrayList<String> p1Cards = new ArrayList<String>();
    ArrayList<String> p2Cards = new ArrayList<String>();
    for (int i = 0; i < 52; i++) {
      int randomNum = (int) (Math.random() * allCards.size() + 0);
      if (decider == 1) {
        p1Cards.add(allCards.get(randomNum));
        allCards.remove(randomNum);
        decider = 2;
      } else {
        p2Cards.add(allCards.get(randomNum));
        allCards.remove(randomNum);
        decider = 1;
      }
    }
    //after cards are put in the arraylists, they are shuffled and then the gameRun method is called.
    Collections.shuffle(p1Cards);
    Collections.shuffle(p2Cards);
    gameRun(p1Cards, p2Cards);

  }

  //method that runs the main game
  public static void gameRun(ArrayList<String> a1, ArrayList<String> a2) {
    //Creates the arraylist that stores each individuals cards
    ArrayList<String> p1CollectedCard = new ArrayList<String>();
    ArrayList<String> p2CollectedCard = new ArrayList<String>();
    String p1Card;
    String p2Card;
    p1CollectedCard = a1;
    p2CollectedCard = a2;
    int runTime = 0;

    //loops through the smaller arraylist until either becomes empty or the run time gets to 100000
    while(p1CollectedCard.size() > 0 && p2CollectedCard.size() > 0 && runTime < 100000){
      runTime++;
      int index;
      //determines the smaller arraylist and stores its size in the index variable which is used for the for loop
      if(p1CollectedCard.size() >= p2CollectedCard.size()){
        index = p2CollectedCard.size();
      } else { 
        index = p1CollectedCard.size();
      }     

      // for loop which draws a card from each players collected list and calls the other methods which check who gets the card.
      for (int i = index - 1; i >= 0; i--) {    
        p1Card = p1CollectedCard.get(i);
        p2Card = p2CollectedCard.get(i);
        p1CollectedCard.remove(i);
        p2CollectedCard.remove(i);
        System.out.println(p1Card + " " + p2Card);
        Main.p1Val = 0;
        Main.p2Val = 0;

          //checks for duplicate cards then wars. three cards are drawn then the fourth card is compared to see who gets to have all the cards.
        if (p1Card.equals(p2Card)) {
          // wars until the card selected are not the same.
          while (p1Card.equals(p2Card) && i > 2) {
            //adds the initially drawn card to the duplicate arraylist
            duplicateArr.add(p1Card);
            duplicateArr.add(p2Card);
            //adds two more cards from each player to the duplicate arraylist
            for (int j = 0; j < 2; j++) {
              i--;
              String dup1;
              String dup2;
              dup1 = p1CollectedCard.get(i);
              dup2 = p2CollectedCard.get(i);
              p1CollectedCard.remove(i);
              p2CollectedCard.remove(i);
              duplicateArr.add(dup1);
              duplicateArr.add(dup2);
            }
            // draws the next card to be checked again
            i--;
            p1Card = p1CollectedCard.get(i);
            p2Card = p2CollectedCard.get(i);
            p1CollectedCard.remove(i);
            p2CollectedCard.remove(i);
          }
          // checks if theres a duplicate card towards the end of the run and readds it to its arraylist again 
          if (i <= 2 && p1Card.equals(p2Card)) {
            p1CollectedCard.add(p1Card);
            p2CollectedCard.add(p2Card);
            i = -1;
          } else {
            //calls to methods that determine who gets the cards drawn
            cardIsIntChecker(p1Card, p2Card, Main.p1Val, Main.p2Val);
            cardWin(p1Card, p2Card, Main.p1Val, Main.p2Val, p1CollectedCard, p2CollectedCard);          
          }
        } else {
          //calls to methods that determine who gets the cards drawn
          cardIsIntChecker(p1Card, p2Card, Main.p1Val, Main.p2Val);
          cardWin(p1Card, p2Card, Main.p1Val, Main.p2Val, p1CollectedCard, p2CollectedCard);
        
        }
      }
      //cards in both arrayLists are shuffled again to avoid a pattern which might occur during the game,
      //ultimately causing both players to have equal cards after the 100000 run
      Collections.shuffle(p1CollectedCard);
      Collections.shuffle(p2CollectedCard);
      System.out.println("Player one has " + p1CollectedCard.size() + " cards and player two has " + p2CollectedCard.size() + " cards, after " + runTime + " runs");
      System.out.println();
    }
    //after the 100000 run - if necessary - , a winner is displayed. 
    if (p1CollectedCard.size() > p2CollectedCard.size()) {
      System.out.println("Player one wins, they collected all " + p1CollectedCard.size() + " cards"
          + " Player two collected " + p2CollectedCard.size() + " cards, it took " + runTime + " runs");

    } else if (p2CollectedCard.size() > p1CollectedCard.size()) {
      System.out.println("Player two wins, they collected all " + p2CollectedCard.size() + " cards"
          + " Player one collected " + p1CollectedCard.size() + " cards, it took " + runTime + " runs");
    } else {
      System.out.println("Game ends a tie, after " + runTime + " runs.");
      System.out.println("Player one has " + p1CollectedCard.size() + " cards.");
      System.out.println("Player two has " + p2CollectedCard.size() + " cards.");
    }
    System.out.println(p1CollectedCard.size() + p2CollectedCard.size());
  }

  //method that checks if p1Card or p2Card is a number stored as a String, then converts it to an integer with the parseInt() method
  public static void cardIsIntChecker(String p1Card, String p2Card, int p1Val, int p2Val) {
    if (!(p1Card.equals("A")) && !(p1Card.equals("J")) && !(p1Card.equals("K")) && !(p1Card.equals("Q"))) {
      Main.p1Val = Integer.parseInt(p1Card);
    }
    if (!(p2Card.equals("A")) && !(p2Card.equals("J")) && !(p2Card.equals("K")) && !(p2Card.equals("Q"))) {
      Main.p2Val = Integer.parseInt(p2Card);
    }
  }

  //method that determins who wins the card after each card draw
  public static void cardWin(String p1Card, String p2Card, int p1Val, int p2Val, ArrayList<String> p1CollectedCard,
      ArrayList<String> p2CollectedCard) {
    if (p1Val != 0 && p2Val != 0) {
      if (p1Val > p2Val) {
        p1CollectedCard.add(p1Card);
        p1CollectedCard.add(p2Card);
        p1CollectedCard.addAll(duplicateArr);
      } else if (p2Val > p1Val) {
        p2CollectedCard.add(p1Card);
        p2CollectedCard.add(p2Card);
        p2CollectedCard.addAll(duplicateArr);  
      }
    } else if (p1Val != 0 && p2Val == 0) {
      p2CollectedCard.add(p1Card);
      p2CollectedCard.add(p2Card);
      p2CollectedCard.addAll(duplicateArr); 
    } else if (Main.p1Val == 0 && Main.p2Val != 0) {
      p1CollectedCard.add(p1Card);
      p1CollectedCard.add(p2Card);
      p1CollectedCard.addAll(duplicateArr);  
    } else {
      if (p1Card.equals("A") && !(p2Card.equals("A"))) {
        p1CollectedCard.add(p1Card);
        p1CollectedCard.add(p2Card);
        p1CollectedCard.addAll(duplicateArr);        
      } else if (p2Card.equals("A")) {
        p2CollectedCard.add(p1Card);
        p2CollectedCard.add(p2Card);
        p2CollectedCard.addAll(duplicateArr);        
      } else if (p1Card.equals("K") && !(p2Card.equals("K"))) {
        p1CollectedCard.add(p1Card);
        p1CollectedCard.add(p2Card);
        p1CollectedCard.addAll(duplicateArr);       
      } else if (p2Card.equals("K")) {
        p2CollectedCard.add(p1Card);
        p2CollectedCard.add(p2Card);
        p2CollectedCard.addAll(duplicateArr);       
      } else if (p1Card.equals("Q") && !(p2Card.equals("Q"))) {
        p1CollectedCard.add(p1Card);
        p1CollectedCard.add(p2Card);
        p1CollectedCard.addAll(duplicateArr); 
      } else if (p2Card.equals("Q")) {
        p2CollectedCard.add(p1Card);
        p2CollectedCard.add(p2Card);
        p2CollectedCard.addAll(duplicateArr); 
      } else if (p1Card.equals("J") && !(p2Card.equals("J"))) {
        p1CollectedCard.add(p1Card);
        p1CollectedCard.add(p2Card);
        p1CollectedCard.addAll(duplicateArr);  
      } else if (p2Card.equals("J")) {
        p2CollectedCard.add(p1Card);
        p2CollectedCard.add(p2Card);
        p2CollectedCard.addAll(duplicateArr);  
      }
    }    
    duplicateArr.clear();
  }
}