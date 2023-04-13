package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Random;

public class Pipe extends Tile {

    public Pipe(HashMap<Direction, Boolean> connector, Random randomGenerator) {
        this.playable = true;
        this.highlight = false;
        this.checked = false;
        this.connector = connector;
        this.rotateClockwise(randomGenerator.nextInt(4));
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(GameDefs.LIGHT_GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension dim = this.getSize();

        this.paintPipe(g2D, dim);

        if(this.checked) {
            this.paintWater(g2D, dim);
        }
    }
}
