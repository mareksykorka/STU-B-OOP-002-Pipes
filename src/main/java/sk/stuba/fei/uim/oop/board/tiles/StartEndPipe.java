package sk.stuba.fei.uim.oop.board.tiles;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Direction;

import java.awt.*;

public class StartEndPipe extends Tile {
    private boolean start;
    @Getter
    private int boardX;
    @Getter
    private int boardY;

    public StartEndPipe(boolean start, int x, int y,Direction direction) {
        super();
        this.start = start;
        this.boardX = x;
        this.boardY = y;
        this.playable = false;
        this.highlight = false;
        this.checked = false;
        if (direction != Direction.NONE) {
            this.connector.put(direction, true);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension dim = this.getSize();

        this.paintPipeEnd(g2D, dim, this.start);
        this.paintPipe(g2D, dim);
        if (this.start) {
            this.paintWater(g2D, dim);
        }
    }
}
