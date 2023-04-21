package sk.stuba.fei.uim.oop.board;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Node {
    private int x;
    private int y;
    private boolean visited;
    private boolean start;
    private boolean finish;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.start = false;
        this.finish = false;
    }
}
