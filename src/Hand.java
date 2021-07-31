import java.util.*;
import java.util.TreeSet;


public class Hand {
    private final Set<Card> cards = new TreeSet<>();

    public Card getCard(int cardNumber) {
        Object[] temp = (Object[]) cards.toArray();
        return (Card) temp[cardNumber % cards.size()];
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public Card dropCard(int cardNumber) {
        Object[] temp = cards.toArray();
        Card card = (Card) temp[cardNumber % cards.size()];
        cards.remove(card);
        return card;
    }

    public boolean equalDuce() {
        Card card = new Card(Suits.Clubs, Ranks.Deuce);
        for (Card c : cards) {
            if (c.compareTo(card) == 0)
                return true;
        }
        return false;
    }
    //Exits in the first card founded
    public boolean checkSuitExist(Suits leadSuit) {
        for (Card c : cards) {
            if (c.getSuit() != leadSuit) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Hand{" + cards + '}';
    }

    /* Check all the cards, until the end */
    public boolean checkNonSameSuit(Suits leadsuit) {
        boolean foundSame = false;
        for (Card c : cards) {
            if (c.getSuit() == leadsuit) {
                foundSame = true;
            }
        }
        return foundSame;
    }
}

