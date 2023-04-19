package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.board.tiles.*;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel {
    private Random randomGenerator;
    private ArrayList<Direction> allDirections;
    private int boardSize;
    private Tile[][] board;
    private Tile startTile;
    private Tile endTile;

    public Board(int size) {
        this.setLayout(new GridLayout(size, size));
        this.randomGenerator = new Random();
        this.boardSize = size;

        this.allDirections = new ArrayList<>();
        this.allDirections.add(Direction.UP);
        this.allDirections.add(Direction.RIGHT);
        this.allDirections.add(Direction.DOWN);
        this.allDirections.add(Direction.LEFT);

        Stack<Node> dfsPath = this.generatePath();
        this.populateWithPipes(dfsPath);
        this.setBoard(size);

        this.setBackground(GameDefs.GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private Stack<Node> generatePath() {
        Node nodes[][] = new Node[this.boardSize][this.boardSize];
        Stack<Node> path = new Stack<>();
        ArrayList<Node> nonVisitedNeighbours;

        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                nodes[x][y] = new Node(x, y);
            }
        }

        Node node = this.generateStartEndNode(nodes);
        path.add(node);
        Node nextNode;

        while (!node.isFinish()) {
            node.setVisited(true);
            nonVisitedNeighbours = this.getNonVisitedNodes(node, nodes);
            if (!nonVisitedNeighbours.isEmpty()) {
                Collections.shuffle(nonVisitedNeighbours);
                nextNode = nonVisitedNeighbours.get(0);
                path.add(nextNode);
                node = nextNode;
            } else {
                for (int i = path.size() - 1; i > 0; i--) {
                    nonVisitedNeighbours = this.getNonVisitedNodes(path.get(i), nodes);
                    if (!nonVisitedNeighbours.isEmpty()) {
                        node = path.get(i);
                        break;
                    } else {
                        path.remove(i);
                    }
                }
            }
        }

        return path;
    }

    private Node generateStartEndNode(Node[][] nodes) {
        Node startNode = null;
        ArrayList<Direction> edges = new ArrayList<>();
        edges.add(Direction.UP);
        edges.add(Direction.RIGHT);
        edges.add(Direction.DOWN);
        edges.add(Direction.LEFT);
        Collections.shuffle(edges);

        switch (edges.get(0)) {
            case UP:
                startNode = nodes[this.randomGenerator.nextInt(this.boardSize)][0];
                startNode.setStart(true);
                nodes[this.randomGenerator.nextInt(this.boardSize)][this.boardSize - 1].setFinish(true);
                break;
            case RIGHT:
                startNode = nodes[this.boardSize - 1][this.randomGenerator.nextInt(this.boardSize)];
                startNode.setStart(true);
                nodes[0][this.randomGenerator.nextInt(this.boardSize)].setFinish(true);
                break;
            case DOWN:
                startNode = nodes[this.randomGenerator.nextInt(this.boardSize)][this.boardSize - 1];
                startNode.setStart(true);
                nodes[this.randomGenerator.nextInt(this.boardSize)][0].setFinish(true);
                break;
            case LEFT:
                startNode = nodes[0][this.randomGenerator.nextInt(this.boardSize)];
                startNode.setStart(true);
                nodes[this.boardSize - 1][this.randomGenerator.nextInt(this.boardSize)].setFinish(true);
                break;
            default:
                break;
        }
        return startNode;
    }

    private ArrayList<Node> getNonVisitedNodes(Node node, Node[][] nodes) {
        ArrayList<Node> nonVisitedNeighbours = new ArrayList<>();
        for (Direction dir : this.allDirections) {
            int nextX = node.getX() + dir.getX();
            int nextY = node.getY() + dir.getY();
            if (isInBounds(nextX) && isInBounds(nextY)) {
                if (!nodes[nextX][nextY].isVisited()) {
                    nonVisitedNeighbours.add(nodes[nextX][nextY]);
                }
            }
        }

        return nonVisitedNeighbours;
    }

    private boolean isInBounds(int index) {
        return ((index >= 0) && (index < this.boardSize));
    }

    private void populateWithPipes(Stack<Node> dfsPath) {
        this.board = new Tile[this.boardSize][this.boardSize];

        for (int i = 0; i < dfsPath.size(); i++) {
            Node currNode = dfsPath.get(i);
            int boardX = currNode.getX();
            int boardY = currNode.getY();
            if (currNode.isStart()) {
                this.board[boardX][boardY] = new StartEndPipe(true, this.getDirection(currNode, dfsPath.get(i + 1)));
                this.startTile = this.board[boardX][boardY];
            } else if (currNode.isFinish()) {
                this.board[boardX][boardY] = new StartEndPipe(false, this.getDirection(currNode, dfsPath.get(i - 1)));
                this.endTile = this.board[boardX][boardY];
            } else if (this.isStraight(dfsPath.get(i - 1), dfsPath.get(i + 1))) {
                this.board[boardX][boardY] = new StraightPipe(this.randomGenerator);
            } else {
                this.board[boardX][boardY] = new BentPipe(this.randomGenerator);
            }
        }

        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                if (this.board[x][y] == null) {
                    this.board[x][y] = new EmptyTile();
                }
            }
        }
    }

    private boolean isStraight(Node prevNode, Node nextNode) {
        return prevNode.getX() == nextNode.getX() || prevNode.getY() == nextNode.getY();
    }

    private Direction getDirection(Node node1, Node node2) {
        int dirX = node2.getX() - node1.getX();
        int dirY = node2.getY() - node1.getY();

        if (dirX == Direction.UP.getX() && dirY == Direction.UP.getY()) {
            return Direction.UP;
        }
        if (dirX == Direction.RIGHT.getX() && dirY == Direction.RIGHT.getY()) {
            return Direction.RIGHT;
        }
        if (dirX == Direction.DOWN.getX() && dirY == Direction.DOWN.getY()) {
            return Direction.DOWN;
        }
        if (dirX == Direction.LEFT.getX() && dirY == Direction.LEFT.getY()) {
            return Direction.LEFT;
        }
        return Direction.NULL;
    }

    private void setBoard(int size) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.add(board[x][y]);
            }
        }
    }

    public boolean checkWin() {
        Stack<Tile> checkedTiles = new Stack<>();
        Direction incomingDirection = null;
        Tile currTile = null;
        Tile nextTile = null;
        int tileX = 0;
        int tileY = 0;

        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                if (this.board[x][y] == startTile) {
                    currTile = this.board[x][y];
                    tileX = x;
                    tileY = y;
                }
            }
        }

        incomingDirection = currTile.getNextDirection(null);

        tileX = tileX + incomingDirection.getX();
        tileY = tileY + incomingDirection.getY();
        if (isInBounds(tileX) && isInBounds(tileY)) {
            nextTile = this.board[tileX][tileY];
        } else {
            nextTile = null;
        }

        while (currTile != null) {
            if (nextTile == null || nextTile instanceof EmptyTile) {
                currTile.setChecked(true);
                checkedTiles.add(currTile);
                break;
            }
            if (nextTile.checkCorrectOrientation(incomingDirection.getOppositeDirection())) {
                currTile.setChecked(true);
                checkedTiles.add(currTile);
                currTile = nextTile;
                if (nextTile.getNextDirection(incomingDirection.getOppositeDirection()) != null) {
                    incomingDirection = nextTile.getNextDirection(incomingDirection.getOppositeDirection());
                    tileX = tileX + incomingDirection.getX();
                    tileY = tileY + incomingDirection.getY();

                    if (isInBounds(tileX) && isInBounds(tileY)) {
                        nextTile = this.board[tileX][tileY];
                    } else {
                        nextTile = null;
                    }
                } else {
                    nextTile = null;
                }
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
