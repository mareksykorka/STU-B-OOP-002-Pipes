package sk.stuba.fei.uim.oop.board.tile;

import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EmptyTile extends Tile {

    @Setter
    private int pathIndex;

    public EmptyTile() {
        this.playable = false;
        this.highlight = false;
        this.checked = false;
        this.initConnector();
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(new Color(187, 187, 187));
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
