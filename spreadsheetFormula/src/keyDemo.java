import java.util.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;

public class keyDemo {
    public static void main(String arg[])
    {
        /*
         *  Section 10.2.
         */
        EventQueue.invokeLater(() -> {
                KeyDemoFrame     frame   = new KeyDemoFrame();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Blah blah blah");
                frame.setVisible(true);
            });
    }
}

class KeyDemoFrame extends JFrame {
    public KeyDemoFrame() {
        /*
         *  Add the JComponent that displays our field.
         *  Section 10.3.
         */
        add(new KeyDemoComponent());

        pack();

        /*
         *  Center the frame in the screen.
         */
        setLocationRelativeTo(null);
    }
}

class KeyDemoComponent extends JComponent {
    private Font        monospacedFont;
    private JTextField  ourTextField;

    private class KeyHandler extends KeyAdapter
    {
        public void keyTyped(KeyEvent event)
        {
            char    character;

            character = event.getKeyChar();

            if (character == KeyEvent.VK_ENTER) {
                System.out.println("Typed: \"Enter\"");
            } else if (character == KeyEvent.VK_ESCAPE) {
                System.out.println("Typed: \"Esc\"");
            } else {
                System.out.println("Typed: " + character);
            }
        }
    }

    public KeyDemoComponent()
    {
        monospacedFont = new Font("Monospaced", Font.BOLD, 18);

        setLayout(new GridLayout(1, 1));

        ourTextField = new JTextField(10);

        ourTextField.addKeyListener(new KeyHandler());
        ourTextField.setFont(monospacedFont);
        add(ourTextField);
    }
}