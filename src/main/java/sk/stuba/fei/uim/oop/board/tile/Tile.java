package sk.stuba.fei.uim.oop.board.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Connection;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Tile extends JPanel {
    @Setter @Getter
    private Direction direction;
    @Setter @Getter
    private Connection connection;
    @Getter
    private Map<Direction, Connection> neighbours;

    @Setter
    private boolean highlight;

    public Tile() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.DARK_GRAY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.highlight) {
            setBackground(Color.RED);
            this.highlight = false;
        } else {
            setBackground(Color.DARK_GRAY);
        }
    }
}
