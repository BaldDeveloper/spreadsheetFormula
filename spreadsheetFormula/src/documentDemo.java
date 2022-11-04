import java.util.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class documentDemo {
    public static void main(String arg[])
    {
        /*
         *  Section 10.2.
         */
        EventQueue.invokeLater(() -> {
                DocumentDemoFrame     frame;

                frame = new DocumentDemoFrame();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Blah blah blah");
                frame.setVisible(true);
            });
    }
}

class DocumentDemoFrame extends JFrame {
    public DocumentDemoFrame() {
        /*
         *  Add the JComponent that displays our field.
         *  Section 10.3.
         */
        add(new DocumentDemoComponent());

        pack();

        /*
         *  Center the frame in the screen.
         */
        setLocationRelativeTo(null);
    }
}

class DocumentDemoComponent extends JComponent {
    private Font        monospacedFont;
    private JTextField  ourTextField;
    private JTextField	otherTextField;
    private Document	savedOtherDocument;

    private class KeyHandler extends KeyAdapter
    {
        public void keyTyped(KeyEvent event)
        {
            char    character;

            character = event.getKeyChar();

            if (character == KeyEvent.VK_ENTER) {
                System.out.println("Typed: \"Enter\"");
                otherTextField.setDocument(ourTextField.getDocument());
            } else if (character == KeyEvent.VK_ESCAPE) {
                System.out.println("Typed: \"Esc\"");
                otherTextField.setDocument(savedOtherDocument);
            } else {
                System.out.println("Typed: " + character);
            }
        }
    }

    public DocumentDemoComponent()
    {
		GridBagConstraints	constraints;
		DefaultCaret		caret;

        monospacedFont = new Font("Monospaced", Font.BOLD, 18);

        setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        add(new JLabel("Shared document"), constraints);

        otherTextField = new JTextField(10);
        savedOtherDocument = otherTextField.getDocument();
        caret = (DefaultCaret)otherTextField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        ourTextField = new JTextField(10);
        ourTextField.addKeyListener(new KeyHandler());
        ourTextField.setFont(monospacedFont);
        constraints.gridx = 0;
        constraints.gridy=1;
        constraints.gridwidth = 1;
        add(ourTextField, constraints);

        ourTextField = new JTextField(10);
        ourTextField.addKeyListener(new KeyHandler());
        ourTextField.setFont(monospacedFont);
        constraints.gridx = 1;
        constraints.gridy=1;
        constraints.gridwidth = 1;
        add(ourTextField, constraints);

        otherTextField.addKeyListener(new KeyHandler());
        otherTextField.setFont(monospacedFont);
        constraints.gridx = 2;
        constraints.gridy=1;
        constraints.gridwidth = 1;
        add(otherTextField, constraints);
    }
}