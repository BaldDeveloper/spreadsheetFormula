import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
public class Test extends JFrame{
    public static void main(String[] args) {
        Test a = new Test();
    }
    public Test() {
        GridBagLayout GridBagLayoutgrid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(GridBagLayoutgrid);


        setTitle("GridBag Layout Example");
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        // gridx controls what column position its in
        // gridy controls what rows
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new Button("x=0,y=0"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(new Button("x=1,y=1"), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;

        // ipady controls the height of the object
        // ipady controls the width of the object
        gbc.ipady = 5;
        gbc.ipadx = 50;
        this.add(new Button("x=0,y=0"), gbc);


//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        this.add(new Button("Button Four"), gbc);
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 2;
//        this.add(new Button("Button Five"), gbc);


        setSize(300, 300);
        setPreferredSize(getSize());
        setVisible(true);


        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}  