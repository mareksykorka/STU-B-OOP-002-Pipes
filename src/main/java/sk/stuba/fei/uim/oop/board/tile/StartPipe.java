package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;

public class StartPipe extends Tile{

    public StartPipe(){
        this.playable = false;
        this.highlight = false;
        this.initConnector();
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        //this.defaultColor = new Color(153, 76, 0);
        this.defaultColor = Color.CYAN;
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

        /*Graphics2D g2D = (Graphics2D)g;
        g2D.setColor(new Color(128, 128, 128));
        g2D.fillOval((dimen.width/10/2), (dimen.height/10/2), dimen.width-(dimen.width/10), dimen.height-(dimen.height/10));
        g2D.setColor(new Color(96, 96, 96));
        g2D.fillOval((dimen.width/5/2), (dimen.height/5/2), dimen.width-(dimen.width/5), dimen.height-(dimen.height/5));
        g2D.setColor(new Color(0, 0, 255));
        g2D.fillOval((dimen.width/2/2), (dimen.height/2/2), dimen.width-(dimen.width/2), dimen.height-(dimen.height/2));*/

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
    }
}
