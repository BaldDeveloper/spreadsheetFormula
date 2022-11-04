import java.util.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;
import expressions.*;

/**
 *  {@code SpreadsheetComponent} is the class that contains the
 *  {@code JComponent} of the spreadsheet.
 *  @version 2022100100
 *  @author Richard Barton
 */
class SpreadsheetComponent extends JComponent {
    private int         gridWidth       = 9;
    private int         gridHeight      = 9;
    private int         fieldWidth      = 10;
    private Font        monospacedFont;
    private Cell		lastEditable;
    private String      preedittedContents;
    private Cell		grid[][];
    private JLabel		rowLabel[];
    private JLabel		columnLabel[];

    private class Cell extends JTextField
    {
		private String		name;
		private Variable	value;

		public Cell(String name, int fieldWidth)
		{
			super(fieldWidth);

			this.name = name;
		}

		public boolean	updateExpression()
		{
			String		contents;
			char		firstCharacter;
			boolean		returnValue;
			Expression	expression;

			returnValue = true;

			try {
				contents = super.getText();
			} catch (Exception exception) {
				if (value != null) {
					value.assign(null);
					value = null;
				}
				return(returnValue);
			}

			if (contents.length() <= 0) {
				if (value != null) {
					value.assign(null);
					value = null;
				}
				return(returnValue);
			}

			expression = null;
			firstCharacter = contents.charAt(0);
			if (Character.isDigit(firstCharacter) == true) {
				try {
					expression = new expressions.Integer(contents);
				} catch (Exception exception) {
//					exception.printStackTrace();
					returnValue = false;
				}
			} else if (firstCharacter == '=') {
				try {
					expression =
						new Expression(contents.substring(1).
												toUpperCase());
				} catch (Exception exception) {
//					exception.printStackTrace();
					returnValue = false;
				}
			}

			if (returnValue == true) {
				value = Variable.get(name);

				value.assign(expression);
			}

			if (expression == null) {
				value = null;
			} else {
				String	ourValue;

				ourValue = "*ERR";
				try {
					ourValue = "" + value.getValue();
				} catch (Exception exception) {
					setBackground(Color.RED);
				}
				setText(ourValue);
			}

			return(returnValue);
		}

		public String getText()
		{
			String	returnValue;

			returnValue = super.getText();

			if (value != null) {
				if (lastEditable == this) {
					Expression	expression;

					expression = value.getExpression();
					returnValue = "";
					if ((expression instanceof
								expressions.Integer) != true) {
						returnValue = "=";
					}
					returnValue += expression;
				} else {
					String	ourValue;

					setBackground(null);
					ourValue = "*ERR";
					try {
						ourValue = "" + value.getValue();
					} catch (Exception exception) {
						setBackground(Color.RED);
					}
					returnValue = ourValue;
				}
			}

			return(returnValue);
		}
	}

    /*
     *  This class handles keys typed in our text fields.
     */
    private class GridKeyHandler extends KeyAdapter
    {
        public void keyTyped(KeyEvent event)
        {
            int         character;
//            String      key;

            character = event.getKeyChar();
//            key = "" + character;
            if (character == KeyEvent.VK_ENTER) {
//                key = "ENTER";
                /*
                 *  The user pressed the ENTER key.
                 */
                if ((lastEditable != null) &&
					(lastEditable.updateExpression() == true)) {
                    /*
                     *  They were editing a text field.  Accept
                     *  their changes.
                     */
                    lastEditable.setEditable(false);
                    lastEditable = null;
                    repaint();
                }
            } else if (character == KeyEvent.VK_ESCAPE) {
                /*
                 *  The user pressed the ESCAPE key.
                 */
                if (lastEditable != null)  {
					Cell	thisCell;

                    /*
                     *  They were editing a text field.  Throw
                     *  away their changes.
                     */
                    thisCell = lastEditable;
                    lastEditable = null;
                    thisCell.setEditable(false);
					thisCell.setText(preedittedContents);
                }
            }
//           System.out.println("'" + key + "'");
        }
    }

    /*
     *  This is the class that manages text field clicks.
     */
    private class MouseHandler extends MouseAdapter
    {
        private int     row;
        private int     column;

        public MouseHandler(int row, int column)
        {
            /*
             *  Remember what text field we're associated with.
             */
            this.row = row;
            this.column = column;
        }

        /*
         *  What to do when the mouse buttons are clicked.
         */
        public void mouseClicked(MouseEvent event)
        {
			Cell	thisCell;

            if (lastEditable == grid[row][column]) {
                /*
                 *  Ignore clicks in the text field we're already
                 *  editing.
                 */

                return;
            }

			if (lastEditable != null) {
                /*
                 *  The user was editing another text field.
                 */
				if (lastEditable.updateExpression() == true) {
					/*
					 *  Close that one accepting their changes.
					 */
	                lastEditable.setEditable(false);
	                repaint();
				} else {
					/*
					 *  Can't parse the expression they left.
					 */
					return;
				}
            }

            /*
             *  Remember that the user is editing this text field.
             *  Also, remember the contents before they make
             *  changes.
             */
            thisCell = grid[row][column];
            preedittedContents = thisCell.getText();
            lastEditable = thisCell;
            lastEditable.setText(thisCell.getText());
            lastEditable.setEditable(true);
        }
    }

    /**
     *  Construct the Component that contains and manages
     *  the spreadsheet.
     */
    public SpreadsheetComponent()
    {
        int             row;
		int         	column;
        GridKeyHandler  gridKeyHandler;

        /*
         *  Create a font we'll use for our field.
         */
        monospacedFont = new Font("Monospaced", Font.BOLD, 18);

        /*
         *  Figure out how big the grid is, layout the grid for
         *  text fields and labels and allocate the array that
         *  holds the pieces.
         */
		// ToDO: remove comment below
        // setLayout(new GridLayout(gridWidth + 1, gridHeight + 1));

		setLayout(new GridBagLayout());
		GridBagConstraints gridBagContraints = new GridBagConstraints();

        grid = new Cell[gridWidth][gridHeight];
        rowLabel = new JLabel[gridHeight];
        columnLabel = new JLabel[gridWidth];

        /*
         *  Instantiate the handler we'll use for keystrokes in
         *  a text field.
         */
        gridKeyHandler = new GridKeyHandler();



		// ToDo: Handles alphabet across the top
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 0;
		add(new JLabel(" "), gridBagContraints);

		// ToDo: Handles alphabet across the top gridwith - 9
        for (column = 1; (column < gridWidth); ++column) {
			columnLabel[column] = new JLabel("" + (char)('A' + column-1),
											 SwingConstants.CENTER);
			columnLabel[column].setFont(monospacedFont);
			gridBagContraints.gridx = column;
			gridBagContraints.gridy = 0;
			add(columnLabel[column], gridBagContraints);
		}

        /*
         *  Instantiate a text field for each cell.
         */
        for (row = 1; (row < gridHeight); ++row) {
			String	name;
			// ToDo: Start of number column
			name = "" + (row);
			rowLabel[row] = new JLabel(name, SwingConstants.CENTER);
			rowLabel[row].setFont(monospacedFont);
			gridBagContraints.gridx = 0;
			gridBagContraints.gridy = row;
			add(rowLabel[row], gridBagContraints);

			// ToDo: Start of empty grid
            for (column = 1; (column < gridWidth); ++column) {
				Cell	thisCell;

                /*
                 *  Instantiate and configure this text field.
                 */
				thisCell = new Cell((char)('A' + column) + name, fieldWidth);
				grid[row][column] = thisCell;
                thisCell.addMouseListener(new MouseHandler(row,
                										   column));
                thisCell.addKeyListener(gridKeyHandler);
                thisCell.setFont(monospacedFont);
                thisCell.setEditable(false);
				gridBagContraints.gridx = column;
				gridBagContraints.gridy = row;
				add(thisCell, gridBagContraints);
            }
        }
    }

    /**
     *  Provide Swing a way to redraw our component.
     */
    public void paintComponent(Graphics graphics)
    {
        int     row;
//        Font    originalFont;
//
//        /*
//         *  Remember the font we were given and install the font
//         *  we want for the game.
//         */
//        originalFont = graphics.getFont();
//        graphics.setFont(monospacedFont);
//
        /*
         *  Figure out what should be displayed for
         *  each text field.
         */
        for (row = 1; (row < gridHeight); ++row) {
            int         column;

            for (column = 1; (column < gridWidth); ++column) {
				Cell	thisCell;

				thisCell = grid[row][column];
				thisCell.setText(thisCell.getText());
            }
        }
//
//        /*
//         *  Restore the original font.
//         */
//        graphics.setFont(originalFont);
    }
}
