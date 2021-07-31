import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>(Suits.NUMBER_OF_SUITS * Ranks.NUMBER_OF_RANKS);
        for (Suits suit : Suits.values()) {
            for (Ranks rank : Ranks.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }


    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < cards.size(); i++) {
            // Random for remaining positions.
            int r = i + rand.nextInt(cards.size() - i);
            //swapping the elements
            Card temp = this.cards.get(r);
            this.cards.set(r, this.cards.get(i));
            this.cards.set(i, temp);
        }
    }

    public Card giveCard() {
        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public String toString() {
        return "Deck{" + cards + '}';
    }
}

