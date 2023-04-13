package sk.stuba.fei.uim.oop.board.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class Tile extends JPanel {
    @Getter
    protected boolean playable;
    @Getter
    @Setter
    protected boolean highlight;
    @Getter
    @Setter
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

    public ArrayList<Tile> getUnusedNeighbours(Stack<Tile> visitedTiles) {
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
            if(this.playable){
                g2D.setColor(GameDefs.HIGHLIGHT_PLAYABLE);
            } else {
                g2D.setColor(GameDefs.HIGHLIGHT_NON_PLAYABLE);
            }
            g2D.setStroke(new BasicStroke((float) (dim.width * 0.2)));
            g2D.drawRect(0, 0, dim.width, dim.height);
            this.highlight = false;
        }
    }

    protected void paintPipe(Graphics2D g2D, Dimension dim) {
        BasicStroke pipeWall = new BasicStroke((float) (dim.width * 0.32));
        BasicStroke pipeIn = new BasicStroke((float) (dim.width * 0.25));
        float xCenter = (float) dim.width / 2;
        float yCenter = (float) dim.height / 2;
        float xPipeEndWidth = (float) dim.width / 8;
        float yPipeEndWidth = (float) dim.height / 8;

        g2D.setColor(GameDefs.BLACK);
        g2D.setStroke(pipeWall);
        if (connector.get(Direction.UP)) {
            g2D.draw(new Line2D.Float(xCenter + xPipeEndWidth, 0, xCenter - xPipeEndWidth, 0));
            g2D.draw(new Line2D.Float(xCenter, 0, xCenter, yCenter));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.draw(new Line2D.Float(dim.width, yCenter + yPipeEndWidth, dim.width, yCenter - yPipeEndWidth));
            g2D.draw(new Line2D.Float(dim.width, yCenter, xCenter, yCenter));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.draw(new Line2D.Float(xCenter + xPipeEndWidth, dim.height, xCenter - xPipeEndWidth, dim.height));
            g2D.draw(new Line2D.Float(xCenter, dim.height, xCenter, yCenter));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.draw(new Line2D.Float(0, yCenter + yPipeEndWidth, 0, yCenter - yPipeEndWidth));
            g2D.draw(new Line2D.Float(0, yCenter, xCenter, yCenter));
        }


        g2D.setColor(GameDefs.DARK_GRAY);
        g2D.setStroke(pipeIn);
        if (connector.get(Direction.UP)) {
            g2D.draw(new Line2D.Float(xCenter + xPipeEndWidth, 0, xCenter - xPipeEndWidth, 0));
            g2D.draw(new Line2D.Float(xCenter, 0, xCenter, yCenter));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.draw(new Line2D.Float(dim.width, yCenter + yPipeEndWidth, dim.width, yCenter - yPipeEndWidth));
            g2D.draw(new Line2D.Float(dim.width, yCenter, xCenter, yCenter));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.draw(new Line2D.Float(xCenter + xPipeEndWidth, dim.height, xCenter - xPipeEndWidth, dim.height));
            g2D.draw(new Line2D.Float(xCenter, dim.height, xCenter, yCenter));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.draw(new Line2D.Float(0, yCenter + yPipeEndWidth, 0, yCenter - yPipeEndWidth));
            g2D.draw(new Line2D.Float(0, yCenter, xCenter, yCenter));
        }
    }

    protected void paintWater(Graphics2D g2D, Dimension dim) {
        BasicStroke water = new BasicStroke((float) (dim.width * 0.2));
        float xCenter = (float) dim.width / 2;
        float yCenter = (float) dim.height / 2;

        g2D.setColor(GameDefs.BLUE);
        g2D.setStroke(water);
        if (connector.get(Direction.UP)) {
            g2D.draw(new Line2D.Float(xCenter, 0, xCenter, yCenter));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.draw(new Line2D.Float(dim.width, yCenter, xCenter, yCenter));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.draw(new Line2D.Float(xCenter, dim.height, xCenter, yCenter));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.draw(new Line2D.Float(0, yCenter, xCenter, yCenter));
        }
    }

    protected void paintPipeEnd(Graphics2D g2D, Dimension dim, boolean water) {
        g2D.setColor(GameDefs.BLACK);
        g2D.fillRect((int) (dim.width * 0.2), (int) (dim.height * 0.2), (int) (dim.width * (1-2*0.2)), (int) (dim.height * (1-2*0.2)));

        g2D.setColor(GameDefs.DARK_GRAY);
        g2D.fillRect((int) (dim.width * 0.23), (int) (dim.height * 0.23), (int) (dim.width * (1-2*0.23)), (int) (dim.height * (1-2*0.23)));

        if (water){
            g2D.setColor(GameDefs.BLUE);
            g2D.fillRect((int) (dim.width * 0.26), (int) (dim.height * 0.26), (int) (dim.width * (1-2*0.26)), (int) (dim.height * (1-2*0.26)));
        }
    }
}