package sk.stuba.fei.uim.oop.logic;

import lombok.Getter;
import sk.stuba.fei.uim.oop.field.PlayingField;
import sk.stuba.fei.uim.oop.field.State;
import sk.stuba.fei.uim.oop.field.tile.Tile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter {

    public static final int INITIAL_BOARD_SIZE = 8;
    private JFrame mainGame;
    private PlayingField currentBoard;
    @Getter
    private JLabel boardSizeLabel;
    private int currentBoardSize;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.currentBoardSize = INITIAL_BOARD_SIZE;
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.boardSizeLabel = new JLabel();
        this.updateBoardSizeLabel();
    }

    private void initializeNewBoard(int dimension) {
        this.currentBoard = new PlayingField(dimension);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
    }

    private void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("CURRENT BOARD SIZE: " + this.currentBoardSize);
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void gameRestart() {
        this.mainGame.remove(this.currentBoard);
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gameRestart();
        this.mainGame.revalidate();
        this.mainGame.repaint();
        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        if (((Tile) current).isPlayable()) {
            ((Tile) current).setHighlight(true);
        }
        this.currentBoard.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        if (((Tile) current).getState().equals(State.EMPTY) && ((Tile) current).isPlayable()) {
            //this.currentBoard.findTile((Tile) current, State.BLACK);
            //this.swapPlayer();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.currentBoardSize = ((JSlider) e.getSource()).getValue();
        this.updateBoardSizeLabel();
        this.gameRestart();
        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gameRestart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
        }
    }
}
