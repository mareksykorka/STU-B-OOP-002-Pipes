package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Random;

public class StraightPipe extends Tile{

    public StraightPipe(HashMap<Direction, Boolean> connector, Random randomGenerator) {
        this.playable = true;
        this.highlight = false;
        this.connector = connector;
        this.rotateTile(randomGenerator.nextInt(10));
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.defaultColor = Color.GREEN;
        this.setBackground(this.defaultColor);
    }

    @Override
    public boolean rotateTile(int amount) {
        for (int i = 0; i < amount; i++) {
            HashMap<Direction, Boolean> oldConnector = new HashMap<>(this.connector);
            this.connector.put(Direction.UP,oldConnector.get(Direction.RIGHT));
            this.connector.put(Direction.LEFT,oldConnector.get(Direction.UP));
            this.connector.put(Direction.DOWN,oldConnector.get(Direction.LEFT));
            this.connector.put(Direction.RIGHT,oldConnector.get(Direction.DOWN));
        }
        return true;
    }

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Dimension dimen = this.getSize();

        Graphics2D g2D = (Graphics2D)g;
        g2D.setStroke(new BasicStroke(10));
        g2D.setColor(Color.BLACK);
        if(connector.get(Direction.UP)){
            g2D.draw(new Line2D.Float(dimen.width/2,0,dimen.width/2,dimen.height/2));
        }
        if(connector.get(Direction.RIGHT)){
            g2D.draw(new Line2D.Float(dimen.width,dimen.height/2,dimen.width/2,dimen.height/2));
        }
        if(connector.get(Direction.DOWN)){
            g2D.draw(new Line2D.Float(dimen.width/2,dimen.height,dimen.width/2,dimen.height/2));
        }
        if(connector.get(Direction.LEFT)){
            g2D.draw(new Line2D.Float(0,dimen.height/2,dimen.width/2,dimen.height/2));
        }


        if(this.highlight){
            setBackground(Color.RED);
            this.highlight = false;
        } else {
            setBackground(this.defaultColor);
        }

        if(this.checked) {
            setBackground(Color.WHITE);
        }
    }
}
