package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;

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
        this.setBackground(new Color(187, 187, 187));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        Dimension dim = this.getSize();

        if (connector.get(Direction.UP)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(dim.width / 2 + dim.width / 8, 0, dim.width / 2 - dim.width / 8, 0));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(dim.width / 2, 0, dim.width / 2, dim.height / 2));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(dim.width, dim.height / 2 + dim.height / 8, dim.width, dim.height / 2 - dim.height / 8));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(dim.width, dim.height / 2, dim.width / 2, dim.height / 2));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(dim.width / 2 + dim.width / 8, dim.height, dim.width / 2 - dim.width / 8, dim.height));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(dim.width / 2, dim.height, dim.width / 2, dim.height / 2));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(0, dim.height / 2 + dim.height / 8, 0, dim.height / 2 - dim.height / 8));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.3)));
            g2D.setColor(new Color(32, 32, 32));
            g2D.draw(new Line2D.Float(0, dim.height / 2, dim.width / 2, dim.height / 2));
        }


        if (connector.get(Direction.UP)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(dim.width / 2 + dim.width / 8, 0, dim.width / 2 - dim.width / 8, 0));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(dim.width / 2, 0, dim.width / 2, dim.height / 2));
        }
        if (connector.get(Direction.RIGHT)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(dim.width, dim.height / 2 + dim.height / 8, dim.width, dim.height / 2 - dim.height / 8));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(dim.width, dim.height / 2, dim.width / 2, dim.height / 2));
        }
        if (connector.get(Direction.DOWN)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(dim.width / 2 + dim.width / 8, dim.height, dim.width / 2 - dim.width / 8, dim.height));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(dim.width / 2, dim.height, dim.width / 2, dim.height / 2));
        }
        if (connector.get(Direction.LEFT)) {
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(0, dim.height / 2 + dim.height / 8, 0, dim.height / 2 - dim.height / 8));
            g2D.setStroke(new BasicStroke((float) (dim.width*0.25)));
            g2D.setColor(new Color(108, 108, 108));
            g2D.draw(new Line2D.Float(0, dim.height / 2, dim.width / 2, dim.height / 2));
        }

        if(this.checked) {
            if (connector.get(Direction.UP)) {
                g2D.setStroke(new BasicStroke((float) (dim.width * 0.2)));
                g2D.setColor(new Color(0, 0, 255));
                g2D.draw(new Line2D.Float(dim.width / 2, 0, dim.width / 2, dim.height / 2));
            }
            if (connector.get(Direction.RIGHT)) {
                g2D.setStroke(new BasicStroke((float) (dim.width * 0.2)));
                g2D.setColor(new Color(0, 0, 255));
                g2D.draw(new Line2D.Float(dim.width, dim.height / 2, dim.width / 2, dim.height / 2));
            }
            if (connector.get(Direction.DOWN)) {
                g2D.setStroke(new BasicStroke((float) (dim.width * 0.2)));
                g2D.setColor(new Color(0, 0, 255));
                g2D.draw(new Line2D.Float(dim.width / 2, dim.height, dim.width / 2, dim.height / 2));
            }
            if (connector.get(Direction.LEFT)) {
                g2D.setStroke(new BasicStroke((float) (dim.width * 0.2)));
                g2D.setColor(new Color(0, 0, 255));
                g2D.draw(new Line2D.Float(0, dim.height / 2, dim.width / 2, dim.height / 2));
            }
        }
    }
}
