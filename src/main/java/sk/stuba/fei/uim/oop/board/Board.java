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
        this.allDirections = new ArrayList<>();
        this.allDirections.add(Direction.UP);
        this.allDirections.add(Direction.RIGHT);
        this.allDirections.add(Direction.DOWN);
        this.allDirections.add(Direction.LEFT);
        this.boardSize = size;

        Stack<Node> dfsPath = this.generatePath();
        this.populateWithPipes(dfsPath);
        this.setBoard();

        this.setBackground(GameDefs.COLOR_GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

    private Direction getInitialDirection(Node node1, Node node2) {
        int deltaX = node2.getX() - node1.getX();
        int deltaY = node2.getY() - node1.getY();
        for (Direction dir : this.allDirections) {
            if (deltaX == dir.getX() && deltaY == dir.getY()) {
                return dir;
            }
        }
        return null;
    }

    private boolean isStraight(Node prevNode, Node nextNode) {
        return ((prevNode.getX() == nextNode.getX()) || (prevNode.getY() == nextNode.getY()));
    }

    private void setBoard() {
        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                this.add(board[x][y]);
            }
        }
    }

    private Tile getNextTile(int x, int y) {
        if (isInBounds(x) && isInBounds(y)) {
            return this.board[x][y];
        }
        return null;
    }

    private Stack<Node> generatePath() {
        Node[][] allNodes = new Node[this.boardSize][this.boardSize];
        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                allNodes[x][y] = new Node(x, y);
            }
        }

        ArrayList<Node> nonVisitedNeighbours;
        Stack<Node> path = new Stack<>();
        Node node = this.generateStartEndNode(allNodes);

        if (node != null) {
            path.add(node);
            while (!node.isFinish()) {
                node.setVisited(true);
                nonVisitedNeighbours = this.getNonVisitedNodes(node, allNodes);
                if (!nonVisitedNeighbours.isEmpty()) {
                    Collections.shuffle(nonVisitedNeighbours);
                    node = nonVisitedNeighbours.get(0);
                    path.add(node);
                } else {
                    for (int i = path.size() - 1; i > 0; i--) {
                        nonVisitedNeighbours = this.getNonVisitedNodes(path.get(i), allNodes);
                        if (!nonVisitedNeighbours.isEmpty()) {
                            node = path.get(i);
                            break;
                        } else {
                            path.remove(i);
                        }
                    }
                }
            }
        }
        return path;
    }

    private Node generateStartEndNode(Node[][] nodes) {
        Node startNode = null;
        ArrayList<Direction> edges = new ArrayList<>(this.allDirections);
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

    private void populateWithPipes(Stack<Node> dfsPath) {
        this.board = new Tile[this.boardSize][this.boardSize];
        Node currNode;
        int boardX, boardY;

        for (int i = 0; i < dfsPath.size(); i++) {
            currNode = dfsPath.get(i);
            boardX = currNode.getX();
            boardY = currNode.getY();
            if (currNode.isStart()) {
                this.board[boardX][boardY] = new StartEndPipe(true, boardX, boardY, this.getInitialDirection(currNode, dfsPath.get(i + 1)));
                this.startTile = this.board[boardX][boardY];
            } else if (currNode.isFinish()) {
                this.board[boardX][boardY] = new StartEndPipe(false, boardX, boardY, this.getInitialDirection(currNode, dfsPath.get(i - 1)));
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

    public boolean checkWin() {
        Stack<Tile> checkedTiles = new Stack<>();
        if (this.startTile instanceof StartEndPipe) {
            Tile currTile = this.startTile;
            Direction direction = currTile.getDirection();
            if (direction != null) {
                int checkX = ((StartEndPipe) this.startTile).getBoardX() + direction.getX();
                int checkY = ((StartEndPipe) this.startTile).getBoardY() + direction.getY();
                Tile nextTile = getNextTile(checkX, checkY);

                while (currTile != null) {
                    currTile.setChecked(true);
                    checkedTiles.add(currTile);
                    if ((nextTile != null) && !(nextTile instanceof EmptyTile)) {
                        if (nextTile.checkConnection(direction.getOppositeDirection())) {
                            currTile = nextTile;
                            Direction nextDirection = nextTile.getDirection(direction.getOppositeDirection());
                            if (nextDirection != null) {
                                direction = nextDirection;
                                checkX = checkX + nextDirection.getX();
                                checkY = checkY + nextDirection.getY();
                                nextTile = getNextTile(checkX, checkY);
                            } else {
                                nextTile = null;
                            }
                        } else {
                            currTile = null;
                        }
                    } else {
                        currTile = null;
                    }
                }
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
