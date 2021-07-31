public class Player {
    private String name;
    private int score;
    private Hand hand;
    private Seats seat;

    public Player(String name, Seats seat) {
        this.name = name;
        this.score = 0;
        this.hand =new Hand();
        this.seat = seat;
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                ", seat=" + seat +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Seats getSeat() {
        return seat;
    }
}
