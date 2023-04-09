package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
    public GameMenu(GameLogic logic) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0,5,0,5);
        this.add(logic.getLabelLevel(),gbc);

        JButton buttonCheckWin = new JButton("CHECK PATH");
        buttonCheckWin.addActionListener(logic);
        buttonCheckWin.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,0);
        this.add(buttonCheckWin,gbc);

        JButton buttonRestart = new JButton("RESTART");
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,0);
        this.add(buttonRestart,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,5,0,5);
        this.add(logic.getLabelBoardSize(),gbc);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, GameLogic.INITIAL_BOARD_SIZE, 12, GameLogic.INITIAL_BOARD_SIZE);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);
        slider.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0,0,0,0);
        this.add(slider,gbc);
    }
}
