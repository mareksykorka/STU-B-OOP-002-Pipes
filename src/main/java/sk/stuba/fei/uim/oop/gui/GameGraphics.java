package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameGraphics extends JFrame {
    private Board currentBoard;

    public GameGraphics(){
        this.setTitle("Pipes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,900);
        this.setResizable(false);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setLayout(new BorderLayout());

        GameLogic logic = new GameLogic(this);
        this.addKeyListener(logic);

        GameMenu currentMenu = new GameMenu(logic);
        this.add(currentMenu, BorderLayout.PAGE_START);

        this.setVisible(true);
    }

    @Override
    public void repaint(){
        this.revalidate();
        super.repaint();
    }

    public void setBoard(Board board){
        if(this.currentBoard != null){
            this.remove(this.currentBoard);
        }
        this.currentBoard = board;
        this.add(board);
        this.repaint();
    }

}
