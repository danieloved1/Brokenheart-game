import java.util.Arrays;

public class Card implements Comparable<Card> {
    private final Suits suit;
    private final Ranks rank;

    public Card(Suits suit, Ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suits getSuit() {
        return this.suit;
    }

    public Ranks getRank() {
        return this.rank;
    }

    @Override
    public int hashCode() {
        return this.suit.ordinal() * Ranks.NUMBER_OF_RANKS + this.rank.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return hashCode() == card.hashCode();
    }

    @Override
    public int compareTo(final Card other) {
        return Integer.compare(this.hashCode(), other.hashCode());
    }

    public String getFullName() {
        return rank.toString() + " of " + suit.toString();
    }

    public String getShortName() {
        return rank.getSymbol() + suit.getSymbol();
    }

    @Override
    public String toString() {
        return getShortName();
    }
}
