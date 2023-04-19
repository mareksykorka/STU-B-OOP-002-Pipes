package sk.stuba.fei.uim.oop.board;

import lombok.Getter;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);
    @Getter
    private int x;
    @Getter
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getOppositeDirection() {
        switch (this) {
            case UP:
                return DOWN;
            case RIGHT:
                return LEFT;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            default:
                return NONE;
        }
    }
}
