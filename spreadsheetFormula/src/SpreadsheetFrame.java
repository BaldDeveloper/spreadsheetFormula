import java.awt.*;
import javax.swing.*;

/**
 *  {@code SpreadsheetFrame} is the class that contains the
 *  {@code JFrame} of the spreadsheet.
 *  @version 2019032800
 *  @author Richard Barton
 */
class SpreadsheetFrame extends JFrame {
    /**
     *  Construct a frame to hold our spreadsheet
     */
    public SpreadsheetFrame() {
        /*
         *  Add the JComponent that displays our spreadsheet.
         */
        add(new SpreadsheetComponent(), BorderLayout.CENTER);

        pack();

        /*
         *  Center the frame in the screen.
         */
        setLocationRelativeTo(null);
    }
}
