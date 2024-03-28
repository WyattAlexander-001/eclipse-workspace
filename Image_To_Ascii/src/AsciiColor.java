import java.awt.Color;

public class AsciiColor {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String getColor(Color color) {
        if (color.equals(Color.BLACK)) {
            return ANSI_BLACK;
        } else if (color.equals(Color.RED)) {
            return ANSI_RED;
        } else if (color.equals(Color.GREEN)) {
            return ANSI_GREEN;
        } else if (color.equals(Color.YELLOW)) {
            return ANSI_YELLOW;
        } else if (color.equals(Color.BLUE)) {
            return ANSI_BLUE;
        } else if (color.equals(Color.MAGENTA)) {
            return ANSI_PURPLE;
        } else if (color.equals(Color.CYAN)) {
            return ANSI_CYAN;
        } else if (color.equals(Color.WHITE)) {
            return ANSI_WHITE;
        } else {
            return ANSI_RESET;
        }
    }
}