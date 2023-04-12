package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.board.tile.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Board extends JPanel {

    private Random randomGenerator;
    private Tile[][] board;
    private Tile startTile;
    private Stack<Tile> path;
    private Tile endTile;

    public Board(int size){
        this.setLayout(new GridLayout(size,size));
        this.randomGenerator = new Random();
        this.path = new Stack<>();
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBoard(size);
        this.setBackground(new Color(135,135,135));
    }

    private void setBoard(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.add(board[i][j]);
            }
        }
    }

    private void initializeBoard(int size) {
        this.board = new Tile[size][size];

        this.getStartEndTile(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] == null){
                    board[i][j] = new EmptyTile(i,j);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != 0) {
                    this.board[i][j].addNeighbour(Direction.UP, this.board[i-1][j]);
                }
                if (i != this.board.length - 1) {
                    this.board[i][j].addNeighbour(Direction.DOWN, this.board[i+1][j]);
                }
                if (j != 0) {
                    this.board[i][j].addNeighbour(Direction.LEFT, this.board[i][j-1]);
                }
                if (j != this.board.length - 1) {
                    this.board[i][j].addNeighbour(Direction.RIGHT, this.board[i][j+1]);
                }
            }
        }

        Stack<Tile> visitedTiles = new Stack<>();
        visitedTiles.add(this.startTile);
        this.path.add(this.startTile);
        Tile currTile = this.startTile;
        Tile nextTile;

        while(currTile != this.endTile){
            HashMap<Direction, Tile> neighboursMap = currTile.getNeighbour();
            List<Tile> neighboursList = new ArrayList<Tile>(neighboursMap.values());
            List<Tile> unusedNeighboursList = new ArrayList<Tile>();
            for (Tile tile:neighboursList) {
                if(!visitedTiles.contains(tile)) {
                    unusedNeighboursList.add(tile);
                }
            }
            if(unusedNeighboursList.size() > 0) {
                Collections.shuffle(unusedNeighboursList);
                nextTile = unusedNeighboursList.get(0);
                nextTile.setPath(true);
                this.path.add(nextTile);
                visitedTiles.add(nextTile);
                currTile = nextTile;
            } else {
                for (int i = this.path.size()-1; i > 0 ; i--) {
                    HashMap<Direction, Tile> retNeighboursMap = this.path.get(i).getNeighbour();
                    List<Tile> retNeighboursList = new ArrayList<Tile>(retNeighboursMap.values());
                    List<Tile> retUnusedNeighboursList = new ArrayList<Tile>();
                    for (Tile tile:retNeighboursList) {
                        if(!visitedTiles.contains(tile)) {
                            retUnusedNeighboursList.add(tile);
                        }
                    }
                    if(retUnusedNeighboursList.size() > 0){
                        currTile = this.path.get(i);
                        break;
                    } else {
                        this.path.get(i).setPath(false);
                        this.path.remove(i);
                    }
                }
            }
        }

        int pathIndex = 1;
        for (Tile tile:this.path) {
            if(tile instanceof EmptyTile){
                ((EmptyTile)tile).setPathIndex(pathIndex);
                pathIndex++;
            }
        }

        for (int i = 1; i < this.path.size(); i++){
            this.path.get(i-1).setConnector(this.path.get(i));
        }

        for (int i = this.path.size()-1; i > 0; i--){
            this.path.get(i).setConnector(this.path.get(i-1));
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (path.contains(this.board[i][j])) {
                    if(!this.board[i][j].equals(this.startTile) || !this.board[i][j].equals(this.endTile)){
                        if(this.board[i][j] instanceof EmptyTile){
                            Tile oldTile = this.board[i][j];
                            if((((EmptyTile) this.board[i][j]).isPipeStraight())){
                                this.board[i][j] = new StraightPipe(oldTile.getConnector(), randomGenerator);
                                /*for (int k = 0; k < this.path.size(); k++) {
                                    if(this.path.get(k) == oldTile){
                                        this.path.set(k,this.board[i][j]);
                                    }
                                }*/
                            } else {
                                this.board[i][j] = new BentPipe(oldTile.getConnector(), randomGenerator);
                                /*for (int k = 0; k < this.path.size(); k++) {
                                    if(this.path.get(k) == oldTile){
                                        this.path.set(k,this.board[i][j]);
                                    }
                                }*/
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != 0) {
                    this.board[i][j].addNeighbour(Direction.UP, this.board[i-1][j]);
                }
                if (i != this.board.length - 1) {
                    this.board[i][j].addNeighbour(Direction.DOWN, this.board[i+1][j]);
                }
                if (j != 0) {
                    this.board[i][j].addNeighbour(Direction.LEFT, this.board[i][j-1]);
                }
                if (j != this.board.length - 1) {
                    this.board[i][j].addNeighbour(Direction.RIGHT, this.board[i][j+1]);
                }
            }
        }
    }

    private void getStartEndTile(int size) {
        Stack<Direction> edges = new Stack<>();
        edges.add(Direction.UP);
        edges.add(Direction.RIGHT);
        edges.add(Direction.DOWN);
        edges.add(Direction.LEFT);

        Collections.shuffle(edges);

        switch (edges.get(0)) {
            case UP:
                this.startTile = this.board[0][randomGenerator.nextInt(size)] = new StartPipe();
                this.endTile = this.board[size-1][randomGenerator.nextInt(size)] = new EndPipe();
                break;
            case RIGHT:
                this.startTile = this.board[randomGenerator.nextInt(size)][size-1] = new StartPipe();
                this.endTile = this.board[randomGenerator.nextInt(size)][0] = new EndPipe();
                break;
            case DOWN:
                this.startTile = this.board[size-1][randomGenerator.nextInt(size)] = new StartPipe();
                this.endTile = this.board[0][randomGenerator.nextInt(size)] = new EndPipe();
                break;
            case LEFT:
                this.startTile = this.board[randomGenerator.nextInt(size)][0] = new StartPipe();
                this.endTile = this.board[randomGenerator.nextInt(size)][size-1] = new EndPipe();
                break;
            default:
                break;
        }
    }

    private boolean isOnBoard(int i,int j){
        if(i > 0 && i < this.board.length){
            if(j > 0 && j < this.board.length){
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        Stack<Tile> checkedTiles = new Stack<>();

        Tile currTile = this.startTile;
        while(currTile != null){
            Tile nextTile = currTile.getConnectedNeighbours(checkedTiles);
            if(nextTile == null || nextTile instanceof EmptyTile){
                currTile.setChecked(true);
                checkedTiles.add(currTile);
                break;
            }
            if(nextTile.checkCorrectOrientation(currTile)){
                currTile.setChecked(true);
                checkedTiles.add(currTile);
                currTile = nextTile;
            } else {
                currTile.setChecked(true);
                checkedTiles.add(currTile);
                break;
            }
        }

        return checkedTiles.contains(this.endTile);
    }

    public void uncheck() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                this.board[i][j].setChecked(false);
            }
        }
    }
}
