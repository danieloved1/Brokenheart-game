
public enum Seats {
    North,
    East,
    South,
    West;

    // Static values()
    public static Seats[] values = Seats.values();

    public static Seats fromOrdinal(int ord) {
        return values[ord];
    }

    public Seats next() {
        return values[(ordinal() + 1) % NUM_SEATS];
    }


    // Number of seats is last seats + 1
    public static final int NUM_SEATS = Seats.values.length;

}


