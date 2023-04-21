package sk.stuba.fei.uim.oop.logic;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.tiles.Tile;
import sk.stuba.fei.uim.oop.gui.GameGraphics;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter {
    private GameGraphics mainWindow;
    @Getter
    private JLabel boardSizeLabel;
    @Getter
    private JLabel levelLabel;
    private int boardSize;
    private Board board;
    private int levelCounter;

    public GameLogic(GameGraphics gameGraphics) {
        this.mainWindow = gameGraphics;
        this.boardSize = GameDefs.INITIAL_BOARD_SIZE;
        this.initializeBoard(this.boardSize);
        this.mainWindow.replaceBoard(this.board);

        this.boardSizeLabel = new JLabel();
        this.updateBoardSizeLabel();

        this.levelCounter = 1;
        this.levelLabel = new JLabel();
        this.updateLevelLabel();

        this.mainWindow.repaint();
    }

    private void initializeBoard(int boardSize) {
        this.board = new Board(boardSize);
        this.board.addMouseMotionListener(this);
        this.board.addMouseListener(this);
    }

    private void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("Current board size is: " + this.boardSize);
    }

    private void updateLevelLabel() {
        this.levelLabel.setText("Current level: " + this.levelCounter + "    " + "Number of wins: " + (this.levelCounter - 1));
    }

    private void gameRestart() {
        this.initializeBoard(this.boardSize);
        this.mainWindow.replaceBoard(this.board);
        this.levelCounter = 1;
        this.updateLevelLabel();
        this.mainWindow.repaint();
    }

    private void gameCheckWin() {
        if (this.board.checkWin()) {
            this.initializeBoard(this.boardSize);
            this.mainWindow.replaceBoard(this.board);
            this.levelCounter++;
            this.updateLevelLabel();
        }
        this.mainWindow.repaint();
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
        Component current = this.board.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        ((Tile) current).setHighlight(true);
        this.board.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.board.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        ((Tile) current).setHighlight(true);
        if (((Tile) current).isPlayable()) {
            if (SwingUtilities.isRightMouseButton(e)) {
                ((Tile) current).rotateClockwise(1);
            }
            if (SwingUtilities.isLeftMouseButton(e)) {
                ((Tile) current).rotateCounterClockwise(1);
            }
        }
        this.board.uncheckTiles();
        this.board.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Component current = this.board.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        ((Tile) current).setHighlight(true);
        this.board.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mainWindow.repaint();
    }
}
