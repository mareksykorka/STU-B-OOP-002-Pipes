package sk.stuba.fei.uim.oop.board.tile;

import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StartPipe extends Tile{

    public StartPipe(){
        this.playable = false;
        this.highlight = false;
        this.initConnector();
        this.neighbour = new HashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.defaultColor = Color.CYAN;
        this.setBackground(this.defaultColor);
    }

    @Override
    public boolean rotateTile() {
        return false;
    }

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension dimen = this.getSize();

        if(connector.get(Direction.UP)){
            g.drawLine(dimen.width/2,0,dimen.width/2,dimen.height/2);
        }
        if(connector.get(Direction.RIGHT)){
            g.drawLine(dimen.width,dimen.height/2,dimen.width/2,dimen.height/2);
        }
        if(connector.get(Direction.DOWN)){
            g.drawLine(dimen.width/2,dimen.height,dimen.width/2,dimen.height/2);
        }
        if(connector.get(Direction.LEFT)){
            g.drawLine(0,dimen.height/2,dimen.width/2,dimen.height/2);
        }


        if(this.highlight){
            setBackground(Color.RED);
            this.highlight = false;
        } else {
            setBackground(this.defaultColor);
        }
    }
}