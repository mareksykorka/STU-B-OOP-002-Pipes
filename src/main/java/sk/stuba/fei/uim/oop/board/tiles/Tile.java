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
        this.setBackground(GameDefs.COLOR_LIGHT_GRAY);
    }

    protected void initConnector() {
        this.connector = new HashMap<>();
        this.connector.put(Direction.UP, false);
        this.connector.put(Direction.LEFT, false);
        this.connector.put(Direction.DOWN, false);
        this.connector.put(Direction.RIGHT, false);
    }

    public boolean checkConnection(Direction checkDirection) {
        if (checkDirection != null) {
            return this.connector.get(checkDirection);
        }
        return false;
    }

    public Direction getDirection() {
        for (Direction dir : this.connector.keySet()) {
            if (this.connector.get(dir)) {
                return dir;
            }
        }
        return null;
    }

    public Direction getDirection(Direction excludedDirection) {
        for (Direction dir : this.connector.keySet()) {
            if (dir != excludedDirection) {
                if (this.connector.get(dir)) {
                    return dir;
                }
            }
        }
        return null;
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
        float averageTileDimension = (float) ((dim.width + dim.height) * 0.5);

        if (this.highlight) {
            if (this.playable) {
                g2D.setColor(GameDefs.HIGHLIGHT_PLAYABLE);
            } else {
                g2D.setColor(GameDefs.HIGHLIGHT_NON_PLAYABLE);
            }
            g2D.setStroke(new BasicStroke(averageTileDimension * GameDefs.STROKE_HIGHLIGHT_RATIO));
            g2D.drawRect(0, 0, dim.width, dim.height);
            this.highlight = false;
        }
    }

    protected void paintPipe(Graphics2D g2D, Dimension dim) {
        float averageTileDimension = (float) ((dim.width + dim.height) * 0.5);
        float xCenter = (float) (dim.width * 0.5);
        float yCenter = (float) (dim.height * 0.5);
        float xPipeEndWidth = dim.width * GameDefs.PIPE_END_WIDTH_RATIO;
        float yPipeEndWidth = dim.height * GameDefs.PIPE_END_WIDTH_RATIO;

        g2D.setColor(GameDefs.COLOR_BLACK);
        g2D.setStroke(new BasicStroke(averageTileDimension * GameDefs.STROKE_PIPE_WALL_RATIO));
        paintLine(g2D, dim, xCenter, yCenter, xPipeEndWidth, yPipeEndWidth);
        paintLine(g2D, dim, xCenter, yCenter);

        g2D.setColor(GameDefs.COLOR_DARK_GRAY);
        g2D.setStroke(new BasicStroke(averageTileDimension * GameDefs.STROKE_PIPE_IN_RATIO));
        paintLine(g2D, dim, xCenter, yCenter, xPipeEndWidth, yPipeEndWidth);
        paintLine(g2D, dim, xCenter, yCenter);
    }

    protected void paintWater(Graphics2D g2D, Dimension dim) {
        float averageTileDimension = (float) ((dim.width + dim.height) * 0.5);
        float xCenter = (float) dim.width / 2;
        float yCenter = (float) dim.height / 2;

        g2D.setColor(GameDefs.COLOR_BLUE);
        g2D.setStroke(new BasicStroke(averageTileDimension * GameDefs.STROKE_WATER_RATIO));
        paintLine(g2D, dim, xCenter, yCenter);
    }

    protected void paintStartEnd(Graphics2D g2D, Dimension dim, boolean water) {
        g2D.setColor(GameDefs.COLOR_BLACK);
        g2D.fillRect((int) (dim.width * 0.2), (int) (dim.height * 0.2), (int) (dim.width * (1 - 2 * 0.2)), (int) (dim.height * (1 - 2 * 0.2)));

        g2D.setColor(GameDefs.COLOR_DARK_GRAY);
        g2D.fillRect((int) (dim.width * 0.23), (int) (dim.height * 0.23), (int) (dim.width * (1 - 2 * 0.23)), (int) (dim.height * (1 - 2 * 0.23)));

        if (water) {
            g2D.setColor(GameDefs.COLOR_BLUE);
            g2D.fillRect((int) (dim.width * 0.26), (int) (dim.height * 0.26), (int) (dim.width * (1 - 2 * 0.26)), (int) (dim.height * (1 - 2 * 0.26)));
        }
    }

    private void paintLine(Graphics2D g2D, Dimension dim, float xCenter, float yCenter) {
        this.paintLine(g2D, dim, xCenter, yCenter, 0, 0, false);
    }

    private void paintLine(Graphics2D g2D, Dimension dim, float xCenter, float yCenter, float xOffset, float yOffset) {
        this.paintLine(g2D, dim, xCenter, yCenter, xOffset, yOffset, true);
    }

    private void paintLine(Graphics2D g2D, Dimension dim, float xCenter, float yCenter, float xOffset, float yOffset, boolean isEdge) {
        float upY, rgX, dwY, lfX;
        if (isEdge) {
            upY = 0;
            rgX = dim.width;
            dwY = dim.height;
            lfX = 0;
        } else {
            upY = yCenter;
            rgX = xCenter;
            dwY = yCenter;
            lfX = xCenter;
        }

        if (connector.get(Direction.UP)) {
            g2D.draw(new Line2D.Float(xCenter + xOffset, 0, xCenter - xOffset, upY));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.draw(new Line2D.Float(dim.width, yCenter + yOffset, rgX, yCenter - yOffset));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.draw(new Line2D.Float(xCenter + xOffset, dim.height, xCenter - xOffset, dwY));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.draw(new Line2D.Float(0, yCenter + yOffset, lfX, yCenter - yOffset));
        }
    }
}