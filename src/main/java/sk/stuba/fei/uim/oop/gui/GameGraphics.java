package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.logic.GameLogic;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;

public class GameGraphics extends JFrame {
    private Board currentBoard;
    private GridBagConstraints frameConstraints;

    public GameGraphics() {
        this.setTitle("Pipe Game - Marek Sýkorka - 115025");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 800);
        this.setResizable(false);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setBackground(GameDefs.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        this.frameConstraints = new GridBagConstraints();
        this.frameConstraints.fill = GridBagConstraints.BOTH;

        GameLogic logic = new GameLogic(this);
        this.addKeyListener(logic);

        JPanel menu = this.createGameMenu(logic);
        this.frameConstraints.gridx = 0;
        this.frameConstraints.gridy = 0;
        this.frameConstraints.weightx = 1.0;
        this.frameConstraints.weighty = 0.0;
        this.add(menu, this.frameConstraints);

        this.setVisible(true);
    }

    private JPanel createGameMenu(GameLogic logic) {
        JPanel gameMenu = new JPanel();
        gameMenu.setBackground(GameDefs.LIGHT_GRAY);
        gameMenu.setLayout(new GridBagLayout());
        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.fill = GridBagConstraints.BOTH;

        JLabel levelLabel = logic.getLabelLevel();
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuConstraints.gridx = 0;
        menuConstraints.gridy = 0;
        menuConstraints.ipadx = 5;
        menuConstraints.ipady = 8;
        menuConstraints.weightx = 0.5;
        menuConstraints.weighty = 0.5;
        gameMenu.add(levelLabel, menuConstraints);

        JButton buttonCheckWin = new JButton(GameDefs.CHECK_BUTTON);
        buttonCheckWin.addActionListener(logic);
        buttonCheckWin.setFocusable(false);
        menuConstraints.gridx = 1;
        menuConstraints.gridy = 0;
        menuConstraints.weightx = 0.25;
        menuConstraints.weighty = 0.5;
        gameMenu.add(buttonCheckWin, menuConstraints);

        JButton buttonRestart = new JButton(GameDefs.RESTART_BUTTON);
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);
        menuConstraints.gridx = 2;
        menuConstraints.gridy = 0;
        menuConstraints.weightx = 0.25;
        menuConstraints.weighty = 0.5;
        gameMenu.add(buttonRestart, menuConstraints);

        JLabel boardSizeLabel = logic.getLabelBoardSize();
        boardSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuConstraints.gridx = 0;
        menuConstraints.gridy = 1;
        menuConstraints.ipady = 5;
        menuConstraints.weightx = 0.5;
        menuConstraints.weighty = 0.5;
        gameMenu.add(boardSizeLabel, menuConstraints);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, GameDefs.MIN_BOARD_SIZE, GameDefs.MAX_BOARD_SIZE, GameDefs.INITIAL_BOARD_SIZE);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);
        slider.setFocusable(false);
        menuConstraints.gridx = 1;
        menuConstraints.gridy = 1;
        menuConstraints.gridwidth = 2;
        menuConstraints.weightx = 0.5;
        menuConstraints.weighty = 0.5;
        gameMenu.add(slider, menuConstraints);

        return gameMenu;
    }

    public void replaceBoard(Board board) {
        if (this.currentBoard != null) {
            this.remove(this.currentBoard);
        }
        this.currentBoard = board;

        this.frameConstraints.gridx = 0;
        this.frameConstraints.gridy = 1;
        this.frameConstraints.weightx = 1.0;
        this.frameConstraints.weighty = 1.0;
        this.add(board, this.frameConstraints);
    }

    @Override
    public void repaint() {
        this.revalidate();
        super.repaint();
    }
}
