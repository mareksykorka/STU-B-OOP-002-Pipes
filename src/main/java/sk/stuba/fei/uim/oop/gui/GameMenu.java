package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.GameLogic;
import sk.stuba.fei.uim.oop.utility.GameDefs;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
    private GridBagConstraints gbc;

    public GameMenu(GameLogic logic) {
        this.setBackground(GameDefs.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.BOTH;

        JLabel levelLabel = logic.getLabelLevel();
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.ipadx = 5;
        this.gbc.ipady = 8;
        this.gbc.weightx = 0.5;
        this.gbc.weighty = 0.5;
        this.add(levelLabel, this.gbc);

        JButton buttonCheckWin = new JButton(GameDefs.CHECK_BUTTON);
        buttonCheckWin.addActionListener(logic);
        buttonCheckWin.setFocusable(false);
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.weightx = 0.25;
        this.gbc.weighty = 0.5;
        this.add(buttonCheckWin, this.gbc);

        JButton buttonRestart = new JButton(GameDefs.RESTART_BUTTON);
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);
        this.gbc.gridx = 2;
        this.gbc.gridy = 0;
        this.gbc.weightx = 0.25;
        this.gbc.weighty = 0.5;
        this.add(buttonRestart, this.gbc);

        JLabel boardSizeLabel = logic.getLabelBoardSize();
        boardSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.ipady = 5;
        this.gbc.weightx = 0.5;
        this.gbc.weighty = 0.5;
        this.add(boardSizeLabel, this.gbc);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, GameDefs.MIN_BOARD_SIZE, GameDefs.MAX_BOARD_SIZE, GameLogic.INITIAL_BOARD_SIZE);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);
        slider.setFocusable(false);
        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 2;
        this.gbc.weightx = 0.5;
        this.gbc.weighty = 0.5;
        this.add(slider, this.gbc);
    }
}
