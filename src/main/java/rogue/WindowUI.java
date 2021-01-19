package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
//import java.awt.Color;

public class WindowUI extends JFrame {


    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 800;
    public static final int TWENTYFIVE = 25;
    // Screen buffer dimensions are different than terminal dimensions
    private static Rogue theGame;
    private static WindowUI theGameUI;
    public static final int COLS = 80;
    public static final int ROWS = 24;
   private final char startCol = 0;
   private final char roomRow = 1;
   private Container contentPane;
   private JLabel messageDisplay;
   private JLabel nameDisplay;
   private JLabel inventoryLabel;

/**
Constructor.
**/

    public WindowUI() {
        super("my awesome game");
        contentPane = getContentPane();
        setWindowDefaults();
        setUpPanels();
        pack();
        start();
    }

    private void setWindowDefaults() {
        setTitle("Rogue");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());

    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.CENTER);
    }

    private void setUpPanels() {
        JPanel labelPanel = new JPanel();
        JPanel messagePanel = new JPanel();
        JPanel inventoryPanel = new JPanel();
        setMenuBar();
        setUpMessageBox(messagePanel);
        setUpInventory(inventoryPanel);
        setTerminal();
    }

    private void setMenuBar() {
      JMenuBar menu = new JMenuBar();
      setJMenuBar(menu);
      JMenu filesMenu = new JMenu("File");
      menu.add(filesMenu);
      JMenuItem name = new JMenuItem("Change Name");
      filesMenu.add(name);
      JMenuItem json = new JMenuItem("Load JSON File");
      filesMenu.add(json);
      JMenuItem load = new JMenuItem("Load Game");
      filesMenu.add(load);
      JMenuItem save = new JMenuItem("Save Game");
      filesMenu.add(save);
      setLoadJsonListener(json);
      setLoadListener(load);
      setSaveListener(save);
      setNameListener(name);
    }

    private void setLoadJsonListener(JMenuItem json) {
      json.addActionListener(e -> {
        boolean isError = true;
        while (isError) {
          JFileChooser fileChooser = new JFileChooser(".");
          fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files (.json)", "json"));
          int result = fileChooser.showOpenDialog(this);
          if (result == fileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            isError = loadJson(file, isError);
          } else {
            isError = false;
          }
        }
      });
    }

    private boolean loadJson(File file, boolean isError) {
      try {
        RogueParser parser = new RogueParser(file, theGame.getSymbolsMap());
        theGame = new Rogue(parser);
        Player thePlayer = new Player("Player One");
        theGame.setPlayer(thePlayer);
        theGameUI.setName(thePlayer.getName());
        theGameUI.setInventory(theGame.getPlayerInventoryDisplay());
        theGameUI.screen.clear();
        theGameUI.draw("New Room File Loaded.", theGame.getNextDisplay());
        theGameUI.draw("New Room File Loaded.", theGame.getNextDisplay());
        return false;
      } catch (Exception z) {
        JOptionPane.showMessageDialog(null, "Error Parsing File.", "Error", JOptionPane.ERROR_MESSAGE);
        return true;
      }
    }

    private void setLoadListener(JMenuItem load) {
      load.addActionListener(e -> {
        boolean isError = true;
        while (isError) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Rogue File (.rgx)", "rgx"));
        int result = fileChooser.showOpenDialog(this);
        if (result == fileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          isError = load(file.toString());
        } else {
          isError = false;
        }
      }
      });
    }

    private boolean load(String filename) {
      try {
        FileInputStream input = new FileInputStream(filename);
        ObjectInputStream stream = new ObjectInputStream(input);
        theGame = (Rogue) stream.readObject();
        closeStreams(input, stream);
        theGameUI.setInventory(theGame.getPlayerInventoryDisplay());
        setName(theGame.getPlayer().getName());
        theGameUI.screen.clear();
        theGameUI.draw("Save Loaded!", theGame.getNextDisplay());
        theGameUI.draw("Save Loaded!", theGame.getNextDisplay());
        return false;
      } catch (IOException z) {
        JOptionPane.showMessageDialog(null, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
        return true;
      } catch (ClassNotFoundException c) {
        JOptionPane.showMessageDialog(null, "Class Not Found.", "Error", JOptionPane.ERROR_MESSAGE);
        return true;
      }
    }

    private void closeStreams(FileInputStream input, ObjectInputStream stream) throws IOException {
      stream.close();
      input.close();
    }

    private void setSaveListener(JMenuItem save) {
      save.addActionListener(e -> {
        boolean isError = true;
        while (isError) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Rogue File (.rgx)", "rgx"));
        int result = fileChooser.showSaveDialog(this);
        if (result == fileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          isError = save(file.toString());
        } else {
          isError = false;
        }
      }
      });
    }

    private boolean save(String filename) {
      try {
        FileOutputStream output = new FileOutputStream(filename + ".rgx");
        ObjectOutputStream stream = new ObjectOutputStream(output);
        stream.writeObject(theGame);
        stream.close();
        output.close();
        return false;
      } catch (IOException z) {
        JOptionPane.showMessageDialog(null, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
        return true;
      }
    }

    private void setNameListener(JMenuItem name) {
      name.addActionListener(e -> {
        JOptionPane nameChange = new JOptionPane();
        JPanel thePanel = new JPanel();
        thePanel.setSize(WIDTH, HEIGHT);
        JLabel query = new JLabel("Enter New Name: ");
        thePanel.add(query);
        String newName = nameChange.showInputDialog(query, thePanel);
        if (newName == null) {
          newName = theGame.getPlayer().getName();
        }
        setName(newName);
        theGame.getPlayer().setName(newName);
      });
    }

    private void setUpMessageBox(JPanel thePanel) {
        thePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        nameDisplay = new JLabel("Player 1 |");
        thePanel.add(nameDisplay);
        messageDisplay = new JLabel("Welcome to Rogue.");
        thePanel.add(messageDisplay);
        contentPane.add(thePanel, BorderLayout.NORTH);
    }

    private void setUpInventory(JPanel thePanel) {
        thePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        inventoryLabel = new JLabel("<html>------- Inventory -------<br/>------- Equipped -------<br/></html>");
        thePanel.add(inventoryLabel);
        contentPane.add(thePanel, BorderLayout.EAST);
    }

    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
Prints a string to the screen starting at the indicated column and row.
@param toDisplay the string to be printed
@param column the column in which to start the display
@param row the row in which to start the display
**/
        public void putString(String toDisplay, int column, int row) {

            Terminal t = screen.getTerminal();
            try {
                t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        /**
        Changes the player name at the top of the screen for the user.
        @param name the name to be displayed
        **/
            public void setName(String name) {
                nameDisplay.setText(name + " |");
            }
            /**
            Changes the message at the top of the screen for the user.
            @param msg the message to be displayed
            **/
            public void setMessage(String msg) {
                messageDisplay.setText(msg);
            }

            /**
            Changes inventory and equipped screen on the right hand side.
            @param inventory the inventory and equipped items to be displayed
            **/
            public void setInventory(String inventory) {
              inventoryLabel.setText(inventory);
            }

/**
Redraws the whole screen including the room and the message.
@param message the message to be displayed at the top of the room
@param room the room map to be drawn
**/
            public void draw(String message, String room) {

                try {
                    setMessage(message);
                    putString(room, startCol, roomRow);
                    screen.refresh();
                } catch (IOException e) {

                }

        }

/**
Obtains input from the user and returns it as a char.  Converts arrow
keys to the equivalent movement keys in rogue.
@return the ascii value of the key pressed by the user
**/
        public char getInput() {
            KeyStroke keyStroke = null;
            char returnChar;
            while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();
            } catch (IOException e) {
            }
        }
        return keyStrokeReader(keyStroke);
    }

    private char keyStrokeReader(KeyStroke keyStroke) {
      if (keyStroke.getKeyType() == KeyType.ArrowDown) {
         return Rogue.DOWN;  //constant defined in rogue
     } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
         return Rogue.UP;
     } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
         return Rogue.LEFT;
     } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
         return Rogue.RIGHT;
     } else {
         return keyStroke.getCharacter();
     }
    }

/**
The controller method for making the game logic work.
@param args command line parameters
**/
    public static void main(String[] args) {
    String message = "Welcome to Rogue.";
    theGameUI = new WindowUI();
    char userInput = 'h';
    setUpGame(message);
    while (userInput != 'q') {
    userInput = theGameUI.getInput();
    try {
        printValidMove(message, userInput);
    } catch (InvalidMoveException badMove) {
        message = "I didn't understand what you meant, please enter a command";
        theGameUI.setMessage(message);
        theGameUI.draw(message, theGame.getNextDisplay());
        }
      }
      System.exit(0);
    }

    private static String initiateEat() {
      JOptionPane eatDialog = new JOptionPane();
      JComboBox inventoryBox = new JComboBox();
      if (theGame.getPlayerInventory().size() == 0) {
        return noItemErrorMessage();
      }
      inventoryBox = setItemBox(inventoryBox);
      int ok = comboBox("Eat", inventoryBox, eatDialog);
      String name = (String) inventoryBox.getSelectedItem();
      if (ok == eatDialog.OK_OPTION) {
        String result = theGame.eatItem(name);
        if (result == null) {
          return eatErrorMessage(name);
        } else {
        return result;
        }
      } else {
        return "Left the Menu.";
      }
    }

    private static JComboBox setItemBox(JComboBox inventoryBox) {
      for (String nameItem : theGame.getPlayerInventory()) {
        inventoryBox.addItem(nameItem);
      }
      return inventoryBox;
    }

    private static int comboBox(String name, JComboBox inventoryBox, JOptionPane eatDialog) {
      return eatDialog.showOptionDialog(null, inventoryBox, name, JOptionPane.OK_CANCEL_OPTION,
      JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    private static String noItemErrorMessage() {
      JOptionPane.showMessageDialog(null, "You have no items in your inventory", "Error", JOptionPane.ERROR_MESSAGE);
      return "No Items!";
    }

    private static String eatErrorMessage(String name) {
      String output = "You can not eat the " + name + ".";
      JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
      return output;
    }

    private static String initiateWear() {
      JOptionPane wearDialog = new JOptionPane();
      JComboBox inventoryBox = new JComboBox();
      if (theGame.getPlayerInventory().size() == 0) {
        return noItemErrorMessage();
      }
      inventoryBox = setItemBox(inventoryBox);
      int ok = comboBox("Wear", inventoryBox, wearDialog);
      String name = (String) inventoryBox.getSelectedItem();
      if (ok == wearDialog.OK_OPTION) {
        String result = theGame.wearItem(name);
        if (result == null) {
          return wearErrorMessage(name);
        } else {
        return result;
      }
    } else {
        return "Left the Menu.";
      }
    }

    private static String wearErrorMessage(String name) {
      String output = "You can not wear the " + name + ".";
      JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
      return output;
    }

    private static String initiateToss() {
      JOptionPane tossDialog = new JOptionPane();
      JComboBox inventoryBox = new JComboBox();
      if (theGame.getPlayerInventory().size() == 0) {
        return noItemErrorMessage();
      }
      inventoryBox = setItemBox(inventoryBox);
      int ok = comboBox("Toss", inventoryBox, tossDialog);
      String name = (String) inventoryBox.getSelectedItem();
      if (ok == tossDialog.OK_OPTION) {
        String result = theGame.tossItem(name);
        if (result == null) {
          return tossErrorMessage(name);
        } else {
        return result;
        }
      } else {
        return "Left the Menu.";
      }
    }

    private static String tossErrorMessage(String name) {
      String output = "You can not toss the " + name + ".";
      JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
      return output;
    }

    private static void printValidMove(String message, char input) throws InvalidMoveException {
      if (input == 'e') {
        message = initiateEat();
      } else if (input == 'w') {
        message = initiateWear();
      } else if (input == 't') {
        message = initiateToss();
      } else {
        message = theGame.makeMove(input);
      }
      String gameInventory;
      gameInventory = theGame.getPlayerInventoryDisplay();
      theGameUI.setInventory(gameInventory);
      theGameUI.screen.clear();
      theGameUI.draw(message, theGame.getNextDisplay());
      theGameUI.draw(message, theGame.getNextDisplay());
    }

    private static void setUpGame(String message) {
      RogueParser parser = new RogueParser("fileLocations.json");
      theGame = new Rogue(parser);
      Player thePlayer = new Player("Player One");
      theGame.setPlayer(thePlayer);
      theGameUI.setName(thePlayer.getName());
      theGameUI.draw(message, theGame.getNextDisplay());
      theGameUI.setVisible(true);
    }

}
