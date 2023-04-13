package sk.stuba.fei.uim.oop.utility;

import sk.stuba.fei.uim.oop.logic.GameLogic;

import java.awt.*;

public class GameDefs {
    public static final String RESTART_BUTTON = "RESTART";
    public static final String CHECK_BUTTON = "CHECK PATH";

    public static int MAX_BOARD_SIZE = 12;
    public static int MIN_BOARD_SIZE = GameLogic.INITIAL_BOARD_SIZE;

    public static final Color LIGHT_GRAY = new Color(187,187,187);
    public static final Color GRAY = new Color(135,135,135);
    public static final Color DARK_GRAY = new Color(108,108,108);
    public static final Color BLACK = new Color(0,0,0);
    public static final Color BLUE = new Color(0,0,200);
}
