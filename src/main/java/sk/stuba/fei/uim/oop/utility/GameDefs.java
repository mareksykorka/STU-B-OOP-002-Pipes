package sk.stuba.fei.uim.oop.utility;

import java.awt.*;

public class GameDefs {
    public static final String RESTART_BUTTON = "RESTART";
    public static final String CHECK_BUTTON = "CHECK PATH";
    public static final int MAX_BOARD_SIZE = 12;
    public static final int INITIAL_BOARD_SIZE = 8;
    public static final int MIN_BOARD_SIZE = INITIAL_BOARD_SIZE;

    public static final Color COLOR_LIGHT_GRAY = new Color(187, 187, 187);
    public static final Color COLOR_GRAY = new Color(135, 135, 135);
    public static final Color COLOR_DARK_GRAY = new Color(108, 108, 108);
    public static final Color COLOR_BLACK = new Color(0, 0, 0);
    public static final Color COLOR_BLUE = new Color(0, 0, 200);
    public static final Color HIGHLIGHT_NON_PLAYABLE = new Color(192, 0, 0);
    public static final Color HIGHLIGHT_PLAYABLE = new Color(0, 125, 56);

    public static final float STROKE_PIPE_WALL_RATIO = (float) 0.325;
    public static final float STROKE_PIPE_IN_RATIO = (float) 0.25;
    public static final float STROKE_WATER_RATIO = (float) 0.2;
    public static final float STROKE_HIGHLIGHT_RATIO = (float) 0.2;
    public static final float PIPE_END_WIDTH_RATIO = (float) 0.125;

    public static final float PIPE_WALL_END_RATIO = (float) 0.19;
    public static final float PIPE_IN_END_RATIO = (float) 0.22;
    public static final float WATER_END_RATIO = (float) 0.26;
}
