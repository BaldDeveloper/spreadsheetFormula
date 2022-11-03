import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 *  {@code spreadsheet} is class that implements a spreadsheet.
 *  @version 2022092300
 *  @author Richard Barton
 */

public class spreadsheet {
    /**
     *  Start the spreadsheet.
     *  @param arg Command line arguments.
     */
    public static void main(String arg[])
    {
        EventQueue.invokeLater(() -> {
                SpreadsheetFrame        frame;

                frame = new SpreadsheetFrame();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("MTC CPT-237 Fall 2022 spreadsheet");
                frame.setVisible(true);
            });
    }
}
