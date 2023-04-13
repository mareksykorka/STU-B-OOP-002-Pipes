package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.logic.GameLogic;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;

public class GameGraphics extends JFrame {
    private Board currentBoard;
    private GridBagConstraints gbc;

    public GameGraphics() {
        this.setTitle("Pipe Game - Marek Sykorka - 115025");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 800);
        this.setResizable(false);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setBackground(GameDefs.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.BOTH;

        GameLogic logic = new GameLogic(this);
        this.addKeyListener(logic);

        GameMenu currentMenu = new GameMenu(logic);
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.weightx = 1.0;
        this.gbc.weighty = 0.0;
        this.add(currentMenu, this.gbc);

        this.setVisible(true);
    }

    public void setBoard(Board board) {
        if (this.currentBoard != null) {
            this.remove(this.currentBoard);
        }
        this.currentBoard = board;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.weightx = 1.0;
        this.gbc.weighty = 1.0;
        this.add(board, this.gbc);
        this.repaint();
    }

    @Override
    public void repaint() {
        this.revalidate();
        super.repaint();
    }
}
