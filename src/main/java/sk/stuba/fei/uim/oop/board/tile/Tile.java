package sk.stuba.fei.uim.oop.board.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public abstract class Tile extends JPanel {

    //Debug
    protected Color defaultColor;

    protected boolean playable;
    @Setter
    protected boolean highlight;

    @Setter
    protected boolean checked;

    @Setter
    protected boolean path;

    @Getter
    protected HashMap<Direction, Boolean> connector;

    @Getter
    protected HashMap<Direction, Tile> neighbour;

    public abstract boolean rotateTile(int amount);

    public boolean getPlayable(){
        return playable;
    }

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

    public boolean checkConnection(){

        return false;
    }

    public void setConnector(Tile tile) {
        for (Direction dir:neighbour.keySet()) {
            if(neighbour.get(dir).equals(tile)){
                this.connector.put(dir,true);
            }
        }
    }

    public Tile getConnectedNeighbours(Stack<Tile> checkedTiles) {
        Tile outTile = null;
        for (Direction dir:this.connector.keySet()) {
            if(connector.get(dir)){
                if(checkedTiles.isEmpty()){
                    outTile = (this.neighbour.get(dir));
                } else if (!checkedTiles.contains(this.neighbour.get(dir))) {
                    outTile = (this.neighbour.get(dir));
                }
            }
        }
        return outTile;
    }

    public boolean checkCorrectOrientation(Tile prevTile) {
        /*HashMap<Direction, Direction> oppositeDirections = new HashMap<>();
        oppositeDirections.put(Direction.UP, Direction.DOWN);
        oppositeDirections.put(Direction.RIGHT, Direction.LEFT);
        oppositeDirections.put(Direction.DOWN, Direction.UP);
        oppositeDirections.put(Direction.LEFT, Direction.RIGHT);*/

        for (Direction direction : neighbour.keySet()) {
            if(neighbour.get(direction).equals(prevTile)){
                if (this.connector.get(direction)) {
                    return true;
                }
            }
        }
        return false;
    }
}
