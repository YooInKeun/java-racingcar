package racing;

public class Position {
    private static final int INITIAL_POSITION = 0;
    private static final int ONE_SPACE = 1;

    private int position;

    Position() {
        this.position = INITIAL_POSITION;
    }

    void increaseOne() {
        position += ONE_SPACE;
    }

    int getPosition() {
        return position;
    }
}
