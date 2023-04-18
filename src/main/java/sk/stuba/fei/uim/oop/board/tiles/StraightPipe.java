package sk.stuba.fei.uim.oop.board.tiles;

import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StraightPipe extends Tile {
    public StraightPipe(Random randomGenerator) {
        this.playable = true;
        this.highlight = false;
        this.checked = false;

        this.initConnector();
        this.connector.put(Direction.UP, true);
        this.connector.put(Direction.DOWN, true);
        this.rotateClockwise(randomGenerator.nextInt(2));

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(GameDefs.LIGHT_GRAY);
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
