package sk.stuba.fei.uim.oop.board.tile;

import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EmptyTile extends Tile {
    private JLabel index;

    @Setter
    private int pathIndex;

    public EmptyTile(int i, int j) {
        this.index = new JLabel("[" + i + "]" + "[" + j + "]");
        this.pathIndex = 0;
        this.add(this.index);
        this.playable = false;
        this.highlight = false;
        this.initConnector();
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.defaultColor = new Color(135, 135, 135);
        this.setBackground(this.defaultColor);
    }

    public boolean isPipeStraight() {
        HashMap<Direction, Direction> oppositeDirections = new HashMap<>();
        oppositeDirections.put(Direction.UP, Direction.DOWN);
        oppositeDirections.put(Direction.RIGHT, Direction.LEFT);
        oppositeDirections.put(Direction.DOWN, Direction.UP);
        oppositeDirections.put(Direction.LEFT, Direction.RIGHT);

        for (Direction direction : connector.keySet()) {
            if (connector.get(direction) && connector.get(oppositeDirections.get(direction))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dimen = this.getSize();

        if (connector.get(Direction.UP)) {
            g.drawLine(dimen.width / 2, 0, dimen.width / 2, dimen.height / 2);
        }
        if (connector.get(Direction.RIGHT)) {
            g.drawLine(dimen.width, dimen.height / 2, dimen.width / 2, dimen.height / 2);
        }
        if (connector.get(Direction.DOWN)) {
            g.drawLine(dimen.width / 2, dimen.height, dimen.width / 2, dimen.height / 2);
        }
        if (connector.get(Direction.LEFT)) {
            g.drawLine(0, dimen.height / 2, dimen.width / 2, dimen.height / 2);
        }

        if (this.highlight) {
            setBackground(Color.RED);
            this.highlight = false;
        } else {
            if (this.path) {
                setBackground(Color.GREEN);
                this.index.setText("path" + pathIndex);
            } else {
                setBackground(this.defaultColor);
            }
        }
    }
}
