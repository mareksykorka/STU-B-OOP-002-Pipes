package sk.stuba.fei.uim.oop.board.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static java.lang.Math.atan2;

public abstract class Tile extends JPanel {
    @Getter
    protected boolean playable;
    @Getter @Setter
    protected boolean highlight;
    @Getter @Setter
    protected boolean checked;
    @Getter
    protected HashMap<Direction, Boolean> connector;
    @Getter
    protected HashMap<Direction, Tile> neighbour;

    protected void initConnector() {
        this.connector = new HashMap<>();
        this.connector.put(Direction.UP, false);
        this.connector.put(Direction.LEFT, false);
        this.connector.put(Direction.DOWN, false);
        this.connector.put(Direction.RIGHT, false);
    }

    public void setConnector(Tile tile) {
        for (Direction dir : this.neighbour.keySet()) {
            if (this.neighbour.get(dir).equals(tile)) {
                this.connector.put(dir, true);
            }
        }
    }

    public boolean checkCorrectOrientation(Tile prevTile) {
        for (Direction dir : this.neighbour.keySet()) {
            if (this.neighbour.get(dir).equals(prevTile)) {
                return this.connector.get(dir);
            }
        }
        return false;
    }

    public void addNeighbour(Direction dir, Tile tile) {
        this.neighbour.put(dir, tile);
    }

    public Tile getConnectedNeighbour(Stack<Tile> checkedTiles) {
        Tile outTile = null;
        for (Direction dir : this.connector.keySet()) {
            if (this.connector.get(dir)) {
                if (checkedTiles.isEmpty()) {
                    outTile = this.neighbour.get(dir);
                } else if (!checkedTiles.contains(this.neighbour.get(dir))) {
                    outTile = this.neighbour.get(dir);
                }
            }
        }
        return outTile;
    }

    public ArrayList<Tile> getUnusedNeighbours(Stack<Tile> visitedTiles){
        ArrayList<Tile> neighboursList = new ArrayList<>(this.neighbour.values());
        ArrayList<Tile> unusedNeighboursList = new ArrayList<>();
        for (Tile tile : neighboursList) {
            if (!visitedTiles.contains(tile)) {
                unusedNeighboursList.add(tile);
            }
        }
        return unusedNeighboursList;
    }

    public void rotateClockwise(int amount) {
        for (int i = 0; i < amount; i++) {
            HashMap<Direction, Boolean> oldConnector = new HashMap<>(this.connector);
            this.connector.put(Direction.UP, oldConnector.get(Direction.LEFT));
            this.connector.put(Direction.LEFT, oldConnector.get(Direction.DOWN));
            this.connector.put(Direction.DOWN, oldConnector.get(Direction.RIGHT));
            this.connector.put(Direction.RIGHT, oldConnector.get(Direction.UP));
        }
    }

    public void rotateCounterClockwise(int amount) {
        for (int i = 0; i < amount; i++) {
            HashMap<Direction, Boolean> oldConnector = new HashMap<>(this.connector);
            this.connector.put(Direction.UP, oldConnector.get(Direction.RIGHT));
            this.connector.put(Direction.LEFT, oldConnector.get(Direction.UP));
            this.connector.put(Direction.DOWN, oldConnector.get(Direction.LEFT));
            this.connector.put(Direction.RIGHT, oldConnector.get(Direction.DOWN));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension dim = this.getSize();

        if (this.highlight) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.2)));
            g2D.setColor(Color.RED);
            g2D.drawRect(0,0,dim.width,dim.height);
            this.highlight = false;
        }
    }
}