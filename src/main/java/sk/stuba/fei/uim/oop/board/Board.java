package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.board.tile.*;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel {
    private Random randomGenerator;
    private Tile[][] board;
    private Tile startTile;
    private Tile endTile;

    public Board(int size) {
        this.setLayout(new GridLayout(size, size));
        this.randomGenerator = new Random();

        this.initializeBoard(size);
        this.generateStartEndTile(size);
        this.setNeighbours(size);
        this.populateWithPipes(size, this.generatePath());
        this.setNeighbours(size);
        this.setBoard(size);

        this.setBackground(GameDefs.GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setBoard(int size) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.add(board[x][y]);
            }
        }
    }

    private void setNeighbours(int size) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (x != 0) {
                    this.board[x][y].addNeighbour(Direction.LEFT, this.board[x - 1][y]);
                }
                if (x != size - 1) {
                    this.board[x][y].addNeighbour(Direction.RIGHT, this.board[x + 1][y]);
                }
                if (y != 0) {
                    this.board[x][y].addNeighbour(Direction.UP, this.board[x][y - 1]);
                }
                if (y != size - 1) {
                    this.board[x][y].addNeighbour(Direction.DOWN, this.board[x][y + 1]);
                }
            }
        }
    }

    private void initializeBoard(int size) {
        this.board = new Tile[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.board[x][y] = new EmptyTile();
            }
        }
    }

    private void generateStartEndTile(int size) {
        ArrayList<Direction> edges = new ArrayList<>();
        edges.add(Direction.UP);
        edges.add(Direction.RIGHT);
        edges.add(Direction.DOWN);
        edges.add(Direction.LEFT);
        Collections.shuffle(edges);

        switch (edges.get(0)) {
            case UP:
                this.startTile = this.board[this.randomGenerator.nextInt(size)][0] = new StartPipe();
                this.endTile = this.board[this.randomGenerator.nextInt(size)][size - 1] = new EndPipe();
                break;
            case RIGHT:
                this.startTile = this.board[size - 1][this.randomGenerator.nextInt(size)] = new StartPipe();
                this.endTile = this.board[0][this.randomGenerator.nextInt(size)] = new EndPipe();
                break;
            case DOWN:
                this.startTile = this.board[this.randomGenerator.nextInt(size)][size - 1] = new StartPipe();
                this.endTile = this.board[this.randomGenerator.nextInt(size)][0] = new EndPipe();
                break;
            case LEFT:
                this.startTile = this.board[0][this.randomGenerator.nextInt(size)] = new StartPipe();
                this.endTile = this.board[size - 1][this.randomGenerator.nextInt(size)] = new EndPipe();
                break;
            default:
                break;
        }
    }

    private Stack<Tile> generatePath() {
        Stack<Tile> path = new Stack<>();
        path.add(this.startTile);

        Stack<Tile> visitedTiles = new Stack<>();
        visitedTiles.add(this.startTile);

        ArrayList<Tile> unusedNeighbours;

        Tile currTile = this.startTile;
        Tile nextTile;

        while (currTile != this.endTile) {
            unusedNeighbours = currTile.getUnusedNeighbours(visitedTiles);
            if (!unusedNeighbours.isEmpty()) {
                Collections.shuffle(unusedNeighbours);
                nextTile = unusedNeighbours.get(0);
                path.add(nextTile);
                visitedTiles.add(nextTile);
                currTile = nextTile;
            } else {
                for (int i = path.size() - 1; i > 0; i--) {
                    unusedNeighbours = path.get(i).getUnusedNeighbours(visitedTiles);
                    if (!unusedNeighbours.isEmpty()) {
                        currTile = path.get(i);
                        break;
                    } else {
                        path.remove(i);
                    }
                }
            }
        }

        return path;
    }

    private void populateWithPipes(int size, Stack<Tile> path) {
        for (int i = 1; i < path.size(); i++) {
            path.get(i - 1).setConnector(path.get(i));
            path.get(i).setConnector(path.get(i - 1));
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (path.contains(this.board[x][y])) {
                    if (this.board[x][y] instanceof EmptyTile) {
                        this.board[x][y] = new Pipe(this.board[x][y].getConnector(), randomGenerator);
                    }
                }
            }
        }
    }

    public boolean checkWin() {
        Stack<Tile> checkedTiles = new Stack<>();
        Tile currTile = this.startTile;

        while (currTile != null) {
            Tile nextTile = currTile.getConnectedNeighbour(checkedTiles);
            if (nextTile == null || nextTile instanceof EmptyTile) {
                currTile.setChecked(true);
                checkedTiles.add(currTile);
                break;
            }
            if (nextTile.checkCorrectOrientation(currTile)) {
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

    public void uncheckTiles() {
        for (Tile[] tiles : this.board) {
            for (Tile tile : tiles) {
                tile.setChecked(false);
            }
        }
    }
}
