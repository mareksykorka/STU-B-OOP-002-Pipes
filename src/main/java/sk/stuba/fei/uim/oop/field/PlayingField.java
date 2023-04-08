package sk.stuba.fei.uim.oop.field;

import sk.stuba.fei.uim.oop.field.tile.Tile;

import javax.swing.*;
import java.awt.*;

public class PlayingField extends JPanel{

    private Tile[][] field;

    public PlayingField(int dimension) {
        this.initializePlayingField(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.LIGHT_GRAY);
    }
    private void initializePlayingField(int dimension) {
        this.field = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.field[i][j] = new Tile();
                this.add(this.field[i][j]);
            }
        }
    }
}
