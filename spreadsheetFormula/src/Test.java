//file: GridBag1.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test extends JPanel {
    GridBagConstraints constraints = new GridBagConstraints();

    public Test() {
        setLayout(new GridBagLayout());
        int x, y;  // for clarity
        addGB(new JButton("North"),  x = 1, y = 0);
        addGB(new JButton("West"),   x = 0, y = 0);
        addGB(new JButton("Center"), x = 1, y = 1);
        addGB(new JButton("East"),   x = 2, y = 1);
        addGB(new JButton("South"),  x = 1, y = 2);
    }

    void addGB(Component component, int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GridBag1");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize(225, 150);
        frame.setLocation(200, 200);
        frame.setContentPane(new Test());
        frame.setVisible(true);
    }
}