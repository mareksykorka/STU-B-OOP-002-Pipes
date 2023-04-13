package sk.stuba.fei.uim.oop.logic;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.tile.Tile;
import sk.stuba.fei.uim.oop.gui.GameGraphics;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter {
    public static final int INITIAL_BOARD_SIZE = 8;
    private GameGraphics mainWindow;
    @Getter
    private JLabel labelBoardSize;
    private int boardSize;
    private Board currentBoard;
    @Getter
    private JLabel labelLevel;
    private int levelCounter;

    public GameLogic(GameGraphics gameGraphics) {
        this.mainWindow = gameGraphics;
        this.boardSize = this.INITIAL_BOARD_SIZE;
        this.initializeBoard(this.boardSize);
        this.mainWindow.setBoard(this.currentBoard);

        this.labelBoardSize = new JLabel();
        this.updateBoardSizeLabel();

        this.levelCounter = 1;
        this.labelLevel = new JLabel();
        this.updateLevelLabel();
    }

    private void initializeBoard(int boardSize) {
        this.currentBoard = new Board(boardSize);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
    }

    private void updateBoardSizeLabel() {
        this.labelBoardSize.setText("Current board size is: " + this.boardSize);
        this.mainWindow.repaint();
    }

    private void updateLevelLabel() {
        this.labelLevel.setText("Current level: " + this.levelCounter + "   " +
                "Number of wins: " + (this.levelCounter - 1));
        this.mainWindow.repaint();
    }

    private void gameRestart() {
        this.initializeBoard(this.boardSize);

        this.levelCounter = 1;
        this.updateLevelLabel();

        this.mainWindow.setBoard(this.currentBoard);
        this.mainWindow.repaint();
    }

    private void gameCheckWin() {
        if (this.currentBoard.checkWin()) {
            this.addLevel();
            this.initializeBoard(this.boardSize);
            this.mainWindow.setBoard(this.currentBoard);
        } else {
            //this.gameRestart();
        }
        this.mainWindow.repaint();
    }

    private void addLevel() {
        this.levelCounter += 1;
        this.updateLevelLabel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gameRestart();
                break;
            case KeyEvent.VK_ENTER:
                this.gameCheckWin();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainWindow.dispose();
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case (GameDefs.RESTART_BUTTON):
                this.gameRestart();
                break;
            case (GameDefs.CHECK_BUTTON):
                this.gameCheckWin();
                break;
            default:
                break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            this.boardSize = ((JSlider) e.getSource()).getValue();
            this.updateBoardSizeLabel();
            this.gameRestart();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        ((Tile) current).setHighlight(true);
        this.currentBoard.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mainWindow.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        if (((Tile) current).isPlayable()) {
            ((Tile) current).setHighlight(true);
            if (SwingUtilities.isRightMouseButton(e)) {
                ((Tile) current).rotateClockwise(1);
            }
            if (SwingUtilities.isLeftMouseButton(e)) {
                ((Tile) current).rotateCounterClockwise(1);
            }
        }
        this.currentBoard.uncheck();
        this.currentBoard.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        ((Tile) current).setHighlight(true);
        this.currentBoard.repaint();
    }
}
