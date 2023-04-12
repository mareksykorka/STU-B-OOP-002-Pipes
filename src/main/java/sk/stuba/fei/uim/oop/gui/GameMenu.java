package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
    private GridBagLayout layout;
    private GridBagConstraints gbc;

    public GameMenu(GameLogic logic) {
        this.setBackground(new Color(187, 187, 187));
        this.layout = new GridBagLayout();
        this.setLayout(layout);
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
        this.layout.setConstraints(levelLabel, gbc);
        this.add(levelLabel);

        JButton buttonCheckWin = new JButton("CHECK PATH");
        buttonCheckWin.addActionListener(logic);
        buttonCheckWin.setFocusable(false);
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.weightx = 0.25;
        this.gbc.weighty = 0.5;
        this.layout.setConstraints(buttonCheckWin, gbc);
        this.add(buttonCheckWin);

        JButton buttonRestart = new JButton("RESTART");
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);
        this.gbc.gridx = 2;
        this.gbc.gridy = 0;
        this.gbc.weightx = 0.25;
        this.gbc.weighty = 0.5;
        this.layout.setConstraints(buttonRestart, gbc);
        this.add(buttonRestart);

        JLabel boardSizeLabel = logic.getLabelBoardSize();
        boardSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.ipady = 5;
        this.gbc.weightx = 0.5;
        this.gbc.weighty = 0.5;
        this.layout.setConstraints(boardSizeLabel, gbc);
        this.add(boardSizeLabel);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, GameLogic.INITIAL_BOARD_SIZE, 16, GameLogic.INITIAL_BOARD_SIZE);
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
        this.layout.setConstraints(slider, gbc);
        this.add(slider);

        //this.setPreferredSize(new Dimension(700,100));
    }
}
