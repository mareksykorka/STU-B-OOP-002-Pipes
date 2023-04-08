package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameGraphics {

    public GameGraphics() {
        JFrame frame = new JFrame("Pipes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setLayout(new BorderLayout());

        GameLogic logic = new GameLogic(frame);
        frame.addKeyListener(logic);

        JPanel gameMenu = new JPanel();
        gameMenu.setBackground(Color.DARK_GRAY);
        JButton buttonRestart = new JButton("RESTART");
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);

        gameMenu.setLayout(new GridLayout(2, 2));
        gameMenu.add(buttonRestart);
        gameMenu.add(logic.getBoardSizeLabel());
        gameMenu.add(slider);
        frame.add(gameMenu, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }
}
