package sk.stuba.fei.uim.oop.field.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.field.*;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    @Getter @Setter
    private State state;
    @Getter @Setter
    private Direction direction;
    @Setter
    private boolean highlight;
    @Getter @Setter
    private boolean playable;

    public Tile() {
        this.state = State.EMPTY;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawRect(0,0,10,10);
    }
}
