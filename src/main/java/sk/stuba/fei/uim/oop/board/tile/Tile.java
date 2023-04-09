package sk.stuba.fei.uim.oop.board.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Connection;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Tile extends JPanel {

    public Tile() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.DARK_GRAY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
