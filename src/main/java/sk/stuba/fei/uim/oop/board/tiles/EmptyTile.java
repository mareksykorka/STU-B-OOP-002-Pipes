package sk.stuba.fei.uim.oop.board.tiles;

import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;

public class EmptyTile extends Tile {
    public EmptyTile(int x, int y) {
        this.playable = false;
        this.highlight = false;
        this.checked = false;

        this.initConnector();

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(GameDefs.LIGHT_GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
