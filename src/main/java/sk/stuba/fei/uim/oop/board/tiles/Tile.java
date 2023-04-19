package sk.stuba.fei.uim.oop.board.tiles;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;

public abstract class Tile extends JPanel {
    @Getter
    protected boolean playable;
    @Setter
    protected boolean highlight;
    @Setter
    protected boolean checked;
    @Getter
    protected HashMap<Direction, Boolean> connector;

    protected Tile() {
        this.initConnector();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(GameDefs.LIGHT_GRAY);
    }

    protected void initConnector() {
        this.connector = new HashMap<>();
        this.connector.put(Direction.UP, false);
        this.connector.put(Direction.LEFT, false);
        this.connector.put(Direction.DOWN, false);
        this.connector.put(Direction.RIGHT, false);
    }

    public boolean checkConnection(Direction checkDirection) {
        if(checkDirection != Direction.NONE){
            return this.connector.get(checkDirection);
        }
        return false;
    }

    public Direction getDirection(Direction excludedDirection) {
        for (Direction dir : this.connector.keySet()) {
            if (dir != excludedDirection) {
                if (this.connector.get(dir)) {
                    return dir;
                }
            }
        }
        return Direction.NONE;
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
            if (this.playable) {
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
        paintSection(g2D, dim, xCenter, yCenter, xPipeEndWidth, yPipeEndWidth);

        g2D.setColor(GameDefs.DARK_GRAY);
        g2D.setStroke(pipeIn);
        paintSection(g2D, dim, xCenter, yCenter, xPipeEndWidth, yPipeEndWidth);
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
        g2D.fillRect((int) (dim.width * 0.2), (int) (dim.height * 0.2), (int) (dim.width * (1 - 2 * 0.2)), (int) (dim.height * (1 - 2 * 0.2)));

        g2D.setColor(GameDefs.DARK_GRAY);
        g2D.fillRect((int) (dim.width * 0.23), (int) (dim.height * 0.23), (int) (dim.width * (1 - 2 * 0.23)), (int) (dim.height * (1 - 2 * 0.23)));

        if (water) {
            g2D.setColor(GameDefs.BLUE);
            g2D.fillRect((int) (dim.width * 0.26), (int) (dim.height * 0.26), (int) (dim.width * (1 - 2 * 0.26)), (int) (dim.height * (1 - 2 * 0.26)));
        }
    }

    private void paintSection(Graphics2D g2D, Dimension dim, float xCenter, float yCenter, float xPipeEndWidth, float yPipeEndWidth) {
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

    /*
    private void paintStrokeLine(Graphics2D g2D, Dimension dim, float xCenter, float yCenter, float xPipeEndWidth, float yPipeEndWidth) {
        if (connector.get(Direction.UP)) {

        }
        if (connector.get(Direction.RIGHT)) {

        }
        if (connector.get(Direction.DOWN)) {

        }
        if (connector.get(Direction.LEFT)) {

        }
    }*/
}