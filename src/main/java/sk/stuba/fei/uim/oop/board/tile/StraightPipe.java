package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Random;

public class StraightPipe extends Tile {

    public StraightPipe(HashMap<Direction, Boolean> connector, Random randomGenerator) {
        this.playable = true;
        this.highlight = false;
        this.connector = connector;
        this.rotateClockwise(randomGenerator.nextInt(10));
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.defaultColor = Color.GREEN;
        this.setBackground(this.defaultColor);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension dimen = this.getSize();

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(10));
        g2D.setColor(Color.BLACK);
        if (connector.get(Direction.UP)) {
            g2D.draw(new Line2D.Float(dimen.width / 2, 0, dimen.width / 2, dimen.height / 2));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.draw(new Line2D.Float(dimen.width, dimen.height / 2, dimen.width / 2, dimen.height / 2));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.draw(new Line2D.Float(dimen.width / 2, dimen.height, dimen.width / 2, dimen.height / 2));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.draw(new Line2D.Float(0, dimen.height / 2, dimen.width / 2, dimen.height / 2));
        }


        if (this.highlight) {
            setBackground(Color.RED);
            this.highlight = false;
        } else {
            setBackground(this.defaultColor);
        }

        if (this.checked) {
            setBackground(Color.WHITE);
        }
    }
}
