package sk.stuba.fei.uim.oop.board.tiles;

import java.awt.*;

public class EmptyTile extends Tile {
    public EmptyTile() {
        super();
        this.playable = false;
        this.highlight = false;
        this.checked = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
