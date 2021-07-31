import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Round {

    ArrayList<Player> players;
    int[] score;
    Seats nextLead;
    private Deck deck;
    private ArrayList<Trick> tricks;
    private int roundNumber;
    private int p;

    public Round(ArrayList<Player> players, int roundNumber,int p) {
        this.deck = new Deck();
        this.tricks = new ArrayList<Trick>();
        this.players = players;
        this.score = new int[4];
        this.roundNumber = roundNumber;
        this.p=p;
    }

    public void deal() {
        deck.shuffle();
        while (!deck.isEmpty()) {
            for (int i = 0; i < Seats.NUM_SEATS; i++) {
                Card card = deck.giveCard();
                players.get(i).getHand().takeCard(card);
            }
        }
    }

    public int[] getScore() {
        return score;
    }

    private void printPlayers() {
        for (int i = 0; i < 4; i++) {
            System.out.println(players.get(i));
        }
    }

    private void passThreeCards() {
        ArrayList<ArrayList<Card>> allCards = new ArrayList<>(4);

        int cardNumber;
        Scanner scanner = new Scanner(System.in);

        printPlayers();
        //Pick 3 cards from all players
        for (int i = 0; i < 4; i++) {

            ArrayList<Card> cardsToPass = new ArrayList<>(3);
            System.out.println("Pick 3 cards to pass to another player");
            for (int j = 0; j < 3; j++) {
                if(this.p==2) {
                    cardNumber = scanner.nextInt();
                }else{
                    Random rand=new Random();
                    cardNumber= rand.nextInt(13);
                }
                cardsToPass.add(this.players.get(i).getHand().dropCard(cardNumber));
            }
            allCards.add(cardsToPass);
        }

        //Pass three cards to the right player
        int passTo = roundNumber % 4;
        if (passTo == 2) {
            passTo = 3;
        } else if (passTo == 3) {
            passTo = 2;
        }

        for (int i = 0; i < Seats.NUM_SEATS; i++) {
            for (int j = 0; j < 3; j++) {
                this.players.get((i + passTo) % 4).getHand().takeCard(allCards.get(i).get(j));
            }
        }

        printPlayers();
    }

    public void play() {
        deal();

        if (roundNumber % 4 != 0) {
            passThreeCards();
        } else {
            System.out.println("Cards did not passed");
        }

        //Find who starts
        for (int i = 0; i < 4; i++) {
            if (players.get(i).getHand().equalDuce()) {
                nextLead = players.get(i).getSeat();
                break;
            }
        }
        Random rand=new Random();
        ArrayList<Trick> tricks = new ArrayList<>(13);
        boolean brokenHeart = false;
        int cardNumber;
        Scanner scanner = new Scanner(System.in);
        for (int t = 0; t < 13; t++) {
            cardNumber = 0;

            if (t != 0) {
                System.out.println(players.get(nextLead.ordinal()) + " Leads");
                System.out.println("choose a card to lead");
                if(this.p==2) {
                    cardNumber = scanner.nextInt();
                }else{
                    cardNumber= rand.nextInt(13-t);
                }
                if(!players.get(nextLead.ordinal()).getHand().checkSuitExist(Suits.Hearts)){
                    brokenHeart=true;
                }
                /* Check lead card (only if beginning with Hearts) */
                while (players.get(nextLead.ordinal()).getHand().getCard(cardNumber).getSuit() == Suits.Hearts|| brokenHeart) {
                    if (!players.get(nextLead.ordinal()).getHand().checkNonSameSuit(Suits.Hearts) || brokenHeart) {
                        break;
                    }
                    System.out.println("You still cant lead with Heart");
                    if (this.p==2) {
                        cardNumber = scanner.nextInt();
                    }
                    else{
                        cardNumber= rand.nextInt(13-t);
                    }
                }
            } else {
                System.out.println(players.get(nextLead.ordinal()) + " Starts");
            }

            Trick trick = new Trick(players.get(nextLead.ordinal()).getHand().dropCard(cardNumber));
            nextLead = nextLead.next();
            for (int i = 1; i < 4; i++) {
                System.out.println(trick);
                System.out.println(players.get(nextLead.ordinal()) + " Turn");
                System.out.println("choose a card to follow");
                if (this.p==2) {
                    cardNumber = scanner.nextInt();
                }else {
                    cardNumber= rand.nextInt(13-t);
                }
                /* If player try to break the Leadsuit */
                while (players.get(nextLead.ordinal()).getHand().getCard(cardNumber).getSuit() != trick.getLeadSuit()) {
                    /* First option: There is no same suit as the lead, should let the player drop the card */
                    /* Second option: There is same suit as the lead, but the player try to drop other suit */
                    if (!players.get(nextLead.ordinal()).getHand().checkSuitExist(trick.getLeadSuit()) || players.get(nextLead.ordinal()).getHand().checkNonSameSuit(trick.getLeadSuit())
                    ) {

                        if (this.p==2) {
                            System.out.println("You can't play with another suit when you have other Suits");
                            cardNumber = scanner.nextInt();
                        }else {
                            cardNumber= rand.nextInt(13-t);
                        }
                    } else {
                        break;
                    }

                }

                if (players.get(nextLead.ordinal()).getHand().getCard(cardNumber).getSuit() == Suits.Hearts) {
                    brokenHeart = true;
                }

                Card card = players.get(nextLead.ordinal()).getHand().dropCard(cardNumber);
                trick.add(card);
                nextLead = nextLead.next();
            }

            nextLead = players.get(trick.whichPlayerLost()).getSeat();
            int lostPlayer=players.get(trick.whichPlayerLost()).getSeat().ordinal();
            for (int i = 0; i < 4; i++) {
                if(trick.getCards().get(i).getSuit()== Suits.Hearts){
                    this.score[lostPlayer]++;
                }
                if(trick.getCards().get(i).getSuit()== Suits.Spades&&trick.getCards().get(i).getRank()==Ranks.Queen){
                    this.score[lostPlayer]+=13;
                }

            }

            //who is the next player starts
            System.out.println("the lose player is player number ->" + trick.whichPlayerLost());
            if (score[lostPlayer]==26){
                score[lostPlayer]=0;
                for (int i = 0; i <4 ; i++) {
                    if(i!=lostPlayer){
                        score[i]+=26;
                    }

                }
            }
            for (int i = 0; i <4 ; i++) {
                System.out.println(players.get(i)+"="+score[i]+"|");
            }


            System.out.println("trick = " + trick);
            tricks.add(trick);
            System.out.println("==================New Round====================");
        }
    }
}










































































//!players.get(nextLead.ordinal()).getHand().checkSuitExist(trick.getLeadSuit()) || !players.get(nextLead.ordinal()).getHand().checkNonSameSuit(trick.getLeadSuit())






