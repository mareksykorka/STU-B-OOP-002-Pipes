package sk.stuba.fei.uim.oop.board;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Stack;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    @Getter
    private int x;
    @Getter
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getCounterClockwise() {
        switch (this) {
            case UP:
                return LEFT;
            case RIGHT:
                return UP;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            default:
                break;
        }
        return null;
    }

    public Direction getOpposite() {
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
                break;
        }
        return null;
    }

    public Direction getClockwise() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            default:
                break;
        }
        return null;
    }
}
