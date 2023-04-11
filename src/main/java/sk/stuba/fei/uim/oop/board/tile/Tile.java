package sk.stuba.fei.uim.oop.board.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile extends JPanel {

    //Debug
    protected Color defaultColor;

    protected boolean playable;
    @Setter
    protected boolean highlight;

    @Setter
    protected boolean path;

    @Getter
    protected HashMap<Direction, Boolean> connector;

    @Getter
    protected HashMap<Direction, Tile> neighbour;

    public abstract boolean rotateTile();

    public abstract boolean checkConnection();

    protected void initConnector(){
        this.connector = new HashMap<>();
        this.connector.put(Direction.UP,false);
        this.connector.put(Direction.LEFT,false);
        this.connector.put(Direction.DOWN,false);
        this.connector.put(Direction.RIGHT,false);
    }

    public void addNeighbour(Direction dir, Tile tile) {
        this.neighbour.put(dir,tile);
    }

    public void setConnector(Tile tile) {
        for (Direction dir:neighbour.keySet()) {
            if(neighbour.get(dir).equals(tile)){
                this.connector.put(dir,true);
            }
        }
    }
}
