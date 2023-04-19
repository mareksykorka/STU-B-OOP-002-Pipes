package sk.stuba.fei.uim.oop.board.tiles;

import sk.stuba.fei.uim.oop.board.Direction;

import java.awt.*;
import java.util.Random;

public class StraightPipe extends Tile {
    public StraightPipe(Random randomGenerator) {
        super();
        this.playable = true;
        this.highlight = false;
        this.checked = false;
        this.connector.put(Direction.UP, true);
        this.connector.put(Direction.DOWN, true);
        this.rotateClockwise(randomGenerator.nextInt(2));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension dim = this.getSize();

        this.paintPipe(g2D, dim);
        if (this.checked) {
            this.paintWater(g2D, dim);
        }
    }
}
