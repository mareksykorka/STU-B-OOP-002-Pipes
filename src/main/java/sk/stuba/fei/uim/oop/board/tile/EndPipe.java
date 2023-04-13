package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;

public class EndPipe extends Tile {

    public EndPipe() {
        this.playable = false;
        this.highlight = false;
        this.checked = false;
        this.initConnector();
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(GameDefs.LIGHT_GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension dim = this.getSize();

        this.paintPipeEnd(g2D, dim, false);
        this.paintPipe(g2D, dim);
    }
}
