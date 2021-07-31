import java.util.ArrayList;

public class Trick {
    private final ArrayList<Card> cards;

    public Trick(Card lead) {
        cards = new ArrayList<>(Seats.NUM_SEATS);
        cards.add(lead);

    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Suits getLeadSuit() {
        return cards.get(0).getSuit();
    }

    public int whichPlayerLost() {
        Suits leadSuit = getLeadSuit();
        int max = 0, maxPlayer = 0;
        for (int i = 0; i < 4; i++) {
            if (cards.get(i).getSuit() == leadSuit) {
                if (cards.get(i).getRank().ordinal() > max) {
                    max = cards.get(i).getRank().ordinal();
                    maxPlayer = i;
                }
            };
        };
        return maxPlayer;
    }

    public void add(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return "Trick{" +
                "cards=" + cards +
                ", suit=" + getLeadSuit() +
                '}';
    }
}
