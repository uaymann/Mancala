package ui;

import mancala.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private MancalaGame newGame;
    private int ruleSet;
    private JPanel pitsPanel;
    private JPanel buttonPanel;
    private List<PositionAwareButton> pitButtons;
    private PositionAwareButton store1Button;
    private PositionAwareButton store2Button;
    private JMenuBar menuBar;
    private JMenuItem kalahGame;
    private JMenuItem ayoGame;
    private JMenuItem loadGame;
    private JMenuItem saveGame;
    private JMenuItem exitGame;
    private JMenuItem backToMain;
    private JButton kalahButton;
    private JButton ayoButton;
    private boolean gameLoaded = false;
    private JPanel playersPanel;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private Player player1;
    private Player player2;
    private UserProfile user1;
    private UserProfile user2;

    public GUI(String title, int width, int height) {

        super(title);
        setSize(width, height);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                handleWindowClosing();
            }
        });

        gameLoaded = false;

        //Creating the MenuBar and adding components
        menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Menu");
        menuBar.add(menu1);

        JMenu newGame = new JMenu("New Game");
        loadGame = new JMenuItem("Load Saved Game");
        saveGame = new JMenuItem("Save and Quit");
        exitGame = new JMenuItem("Quit without saving");
        backToMain = new JMenuItem("Back to Main Menu");
        menu1.add(newGame);
        menu1.add(loadGame);
        menu1.addSeparator();
        menu1.add(saveGame);
        menu1.add(exitGame);
        menu1.addSeparator();
        menu1.add(backToMain);

        kalahGame = new JMenuItem("Play Kalah");
        ayoGame = new JMenuItem("Play Ayo");
        newGame.add(kalahGame);
        newGame.add(ayoGame);

        kalahGame.addActionListener(e -> handleNewGameMenu(e));
        ayoGame.addActionListener(e -> handleNewGameMenu(e));
        loadGame.addActionListener(e -> handleMenuClick(e));
        saveGame.addActionListener(e -> handleMenuClick(e));
        exitGame.addActionListener(e -> handleMenuClick(e));
        backToMain.addActionListener(e -> handleMenuClick(e));

        getContentPane().add(BorderLayout.NORTH, menuBar).setForeground(Color.PINK);

        playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(5, 2, 20, 5));

        playersPanel.add(new JLabel("<html>Welcome to Mancala!<br/><br/>Players, enter your names:</html>"));
        playersPanel.add(new JLabel("Choose a game mode to start playing"));
        player1NameField = new JTextField("PlayerOne");
        playersPanel.add(player1NameField);
        player2NameField = new JTextField("PlayerTwo");
        playersPanel.add(player2NameField);

        kalahButton = new JButton("Play Kalah");
        playersPanel.add(kalahButton).setForeground(Color.magenta);
        kalahButton.addActionListener(e -> handleNewGameMenu(e));
        ayoButton = new JButton("Play Ayo");
        playersPanel.add(ayoButton).setForeground(Color.magenta);
        ayoButton.addActionListener(e -> handleNewGameMenu(e));

        playersPanel.add(new JButton("Load Saved Game"));
        playersPanel.add(new JButton("View User Profile"));

        getContentPane().add(playersPanel, BorderLayout.CENTER);

    }

    private void handleWindowClosing() {
        if (gameLoaded) {
            int input = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit without saving?", "Game in progress!", JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                System.exit(0);
            } else {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        } else {
            int input = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit the application?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                System.exit(0);
            } else {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        }
    }

    void playMancala(){

        int result = JOptionPane.showConfirmDialog(this, "Ready to play?", "Confirm", JOptionPane.OK_CANCEL_OPTION);

        if (result == 0 && (player1NameField.getText().trim().isBlank() || !player1NameField.getText().trim().matches("[a-zA-Z]+")
            || player2NameField.getText().trim().isBlank() || !player2NameField.getText().trim().matches("[a-zA-Z]+")
            || player1NameField.getText().trim().contentEquals(player2NameField.getText().trim()))) {

            gameLoaded = false;
            JOptionPane.showMessageDialog(this, "Player names may only contain letters with no spaces.", "Invalid username(s)", JOptionPane.OK_OPTION);
        } else if (result == 0) {

            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();

            newGame = new MancalaGame(ruleSet); // Initialize the MancalaGame
            player1 = createPlayer(player1Name, 1);
            player2 = createPlayer(player2Name, 2);
            user1 = player1.getUserProfile();
            user2 = player2.getUserProfile();

            newGame.setPlayers(player1, player2);
            newGame.startNewGame();
            newGame.setCurrentPlayer(player1);

            pitsPanel = makePitsPanel();

            Container contentPane = getContentPane();
            contentPane.removeAll(); // Clear existing components
            contentPane.setLayout(new BorderLayout());

            contentPane.add(pitsPanel, BorderLayout.CENTER);

            createStoreButton(1);
            createStoreButton(2);

            pitButtons = new ArrayList<>();
            createPitButtons();
            contentPane.setVisible(false);
            contentPane.setVisible(true);

            refreshUI();

        } else {
            backToMain();
        }
    }

    private void restartMancala() {

        getContentPane().removeAll(); // Clear existing components
        getContentPane().setVisible(true);
        playMancala();
    }

    Player createPlayer(String playerName, int playerNum) {

        Player player = new Player();
        UserProfile playerProfile = new UserProfile();

        player.setUserProfile(playerProfile);
        playerProfile.setUserName(playerName);

        return player;

    }

    private JPanel makePitsPanel() {
        JPanel panelPit = new JPanel();
        panelPit.setLayout(new GridLayout(2, 6)); // Two rows for two players, six columns for pits

        return panelPit;
    }

    private void displayLabel() {
        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("Current Player: " + newGame.getCurrentPlayer().getName());
        messagePanel.add(messageLabel).setForeground(Color.red);
        getContentPane().add(messagePanel, BorderLayout.SOUTH);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    private void createStoreButton(int playerNum) {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        if (playerNum == 1) {
            store1Button = new PositionAwareButton(getStoreButtonText(playerNum));
            buttonPanel.add(store1Button);
            getContentPane().add(buttonPanel, BorderLayout.EAST);
        } else {
            store2Button = new PositionAwareButton(getStoreButtonText(playerNum));
            buttonPanel.add(store2Button);
            getContentPane().add(buttonPanel, BorderLayout.WEST);
        }
    }

    private void createPitButtons() {

        for (int i = 12; i >= 7; i--) {
            PositionAwareButton pitButton = new PositionAwareButton(getPitButtonText(i));
            pitButton.setAcross(i);
            pitButton.addActionListener(e -> handleButtonClick(e));
            pitButtons.add(pitButton);
            pitsPanel.add(pitButton);
        }
        for (int i = 1; i <= 6; i++) {
            PositionAwareButton pitButton = new PositionAwareButton(getPitButtonText(i));
            pitButton.setAcross(i);
            pitButton.addActionListener(e -> handleButtonClick(e));
            pitButtons.add(pitButton);
            pitsPanel.add(pitButton);
        }
    }

    private String getPitButtonText(int pitNumber) {
        return "(" + newGame.getBoard().getNumStones(pitNumber) + ")";
    }

    private String getStoreButtonText(int playerNum) {
        return "Player " + playerNum + "'s Store [" + newGame.getDataStructure().getStoreCount(playerNum) + "]";
    }

    private void backToMain() {
        gameLoaded = false;
        getContentPane().removeAll();
        getContentPane().add(playersPanel);
        getContentPane().add(playersPanel, BorderLayout.CENTER);
        getContentPane().setVisible(false);
        getContentPane().setVisible(true);
    }

    private void refreshUI() {

        // Update the UI based on the game state
        for (int i = 0; i < pitButtons.size(); i++) {
            PositionAwareButton pitButton = pitButtons.get(i);
            int pitNumber = pitButton.getAcross();
            pitButton.setText(getPitButtonText(pitNumber));
        }
        store1Button.setText(getStoreButtonText(1));
        store2Button.setText(getStoreButtonText(2));
        displayLabel();

    }

    private void handleMenuClick(ActionEvent e) {

        if (e.getSource() == loadGame) {
            if (gameLoaded) {
                int input = JOptionPane.showConfirmDialog(this,
                "Load a previously saved game?", "Game in progress!",JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    loadSavedGame();
                }
                //JOptionPane.showMessageDialog(this, "Game in progress, cannot load.");
            } else {
                loadSavedGame();
            }
        }

        if (e.getSource() == saveGame) {
            if (!gameLoaded) {
                JOptionPane.showMessageDialog(this, "No game in progress to save.");
                System.exit(0);
            } else {
                saveGame();
                int input = JOptionPane.showConfirmDialog(this,
                "Would you still like to quit?", "Confirm",JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    System.exit(0);
                }
            }
        }

        if (e.getSource() == exitGame) {
            if (gameLoaded) {
                int input = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit without saving?", "Game in progress!",JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    System.exit(0);
                }
            } else {
                int input = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit the game?", "Confirm",JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    System.exit(0);
                }
            }
        }

        if (e.getSource() == backToMain && gameLoaded) {
            int input = JOptionPane.showConfirmDialog(this,
                "You are about to leave this game unsaved.", "Back to Main Screen?",JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                backToMain();
            }
        }

    }

    private void handleNewGameMenu(ActionEvent e) {

        if (e.getSource() == kalahGame || e.getSource() == kalahButton) {
            ruleSet = 1;
        } else if (e.getSource() == ayoGame || e.getSource() == ayoButton) {
            ruleSet = 2;
        }

        if (gameLoaded) {
            int input = JOptionPane.showConfirmDialog(this,
            "Would you like to start a new game?", "Game in progress!",JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                gameLoaded = true;
                restartMancala();
            }
        } else {
            gameLoaded = true;
            playMancala();
        }

    }

    private void handleButtonClick(ActionEvent e) {

        PositionAwareButton button = (PositionAwareButton) e.getSource();
        int pitNumber = button.getAcross();

        try {
            newGame.move(pitNumber);
            refreshUI(); // Update the UI after the move
        } catch (InvalidMoveException ex) {
            // show an error message
            JOptionPane.showMessageDialog(this, "Invalid move. Please try again.");
        }
        // Check for game over and display the winner if the game is over
        if (newGame.isGameOver()) {
            refreshUI();
            displayGameOver();
        }

    }

    private void saveGame() {
        try {
            Saver.saveObject(newGame, "savedGame.ser");
            Saver.saveObject(user1, user1.getUserName() + "_Profile.ser");
            Saver.saveObject(user2, user2.getUserName() + "_Profile.ser");
            JOptionPane.showMessageDialog(this, "Your game has been saved.", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // method that loads a game state
    private void loadSavedGame() {
        try {
            newGame = (MancalaGame) Saver.loadObject("savedGame.ser");
            user1 = (UserProfile) Saver.loadObject(user1.getUserName() + "_Profile.ser");
            user2 = (UserProfile) Saver.loadObject(user2.getUserName() + "_Profile.ser");
            refreshUI(); // Refresh the UI with the loaded game state
            JOptionPane.showMessageDialog(this, "Game loaded successfully.", "Loaded", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(this, "Error loading game: Invalid game data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayGameOver() {
        Player winner;
        try {
            winner = newGame.getWinner();
            if (winner != null) {
                JOptionPane.showMessageDialog(this, winner.getName() + " wins!");
            } else {
                JOptionPane.showMessageDialog(this, "It's a tie!");
            }
            int input = JOptionPane.showConfirmDialog(this,
            "Would you like to play again?", "Game over",JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                restartMancala();
            } else {
                backToMain();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "The game is not yet over.");
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI("MANCALA", 900, 300);
        gui.setVisible(true);
    }
}