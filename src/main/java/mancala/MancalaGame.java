package mancala;

import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable {

    private static final long serialVersionUID = 1L;
    private final MancalaDataStructure dataStructure;
    private GameRules board;
    private final ArrayList<Player> players;
    private Player currentPlayer;

    /**
     * Constructor to initialize a new Mancala game with default kalah game rules.
     */
    public MancalaGame() {
        initializeGameRules(1);
        this.dataStructure = this.board.getDataStructure();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
    }

    /**
     * Constructor to initialize a new Mancala game with specified game rules.
     *
     * @param gameRuleSet The game rule set to use (1 for Kalah, 2 for Ayo).
     */
    public MancalaGame(final int gameRuleSet) {
        initializeGameRules(gameRuleSet);
        this.dataStructure = this.board.getDataStructure();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
    }

    /**
     * Initialize game rule set.
     *
     * @param gameRuleSet The game rule set to use (1 for Kalah, 2 for Ayo).
     */
    private void initializeGameRules(final int gameRuleSet) {
        if (gameRuleSet == 1) {
            this.board = new KalahRules();
        } else {
            this.board = new AyoRules();
        }
    }

    /**
     * Set the players for the game.
     *
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        this.players.clear();
        // Set player one
        if (!onePlayer.getName().isBlank()) { //check if player has name
            this.players.add(onePlayer);
        } else {
            return;
        }
        // Set player two
        if (!twoPlayer.getName().isBlank()) { //check if player has name
            this.players.add(twoPlayer);
        } else {
            return;
        }
        this.board.registerPlayers(onePlayer, twoPlayer); // register the players
    }

    /**
     * Get the players for the game.
     *
     * @return The list of players.
     */
    protected ArrayList<Player> getPlayers() {
        // Return the list of players
        return this.players;
    }

    /**
     * Get the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Set the current player.
     *
     * @param player The player to set as the current player.
     */
    public void setCurrentPlayer(final Player player) {
        this.currentPlayer = player;

    }

    /**
     * Switch the current player.
     */
    private void changeCurrentPlayer() {
        if (this.currentPlayer.getName().equals(this.players.get(0).getName())) {
            this.currentPlayer = this.players.get(1);
        } else {
            this.currentPlayer = this.players.get(0);
        }
    }

    /**
     * Set the board for the game.
     *
     * @param theBoard The game board to set.
     */
    protected void setBoard(final GameRules theBoard) {
        this.board = theBoard;
    }

    /**
     * Get the board for the game.
     *
     * @return The game board.
     */
    public GameRules getBoard() {
        return this.board;
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    public MancalaDataStructure getDataStructure() {
        return this.dataStructure;
    }

    /**
     * Get the number of stones in a specific pit.
     *
     * @param pitNum The pit number.
     * @return The number of stones in the pit.
     */
    protected int getNumStones(final int pitNum) {
        return this.board.getNumStones(pitNum);
    }

    /**
     * Make a move for the current player.
     *
     * @param startPit The starting pit for the move.
     * @return The number of stones in the last pit of the move.
     * @throws InvalidMoveException If the move is invalid.
     */
    public int move(final int startPit) throws InvalidMoveException {

        int retVal;

        try {
            int playerNum;
            if (this.players.get(0).getName().equals(this.currentPlayer.getName())) {
                playerNum = 1;
            } else {
                playerNum = 2;
            }
            retVal = this.board.moveStones(startPit, playerNum);
            if (!this.board.isFreeTurn()) {
                this.changeCurrentPlayer();
            }
        } catch (InvalidMoveException e) {
            throw new InvalidMoveException();
        }
        return retVal;
    }

    /**
     * Get the total number of stones in a player's store.
     *
     * @param player The player.
     * @return The number of stones in the player's store.
     * @throws NoSuchPlayerException If the player is not found.
     */
    protected int getStoreCount(final Player player) throws NoSuchPlayerException {

        if (this.players.contains(player)) {
            return player.getStoreCount();
        } else {
            throw new NoSuchPlayerException();
        }

    }

    /**
     * Get the winner of the game.
     *
     * @return The winning player.
     * @throws GameNotOverException If the game is not over.
     */
    public Player getWinner() throws GameNotOverException {
        Player toReturn;
        if (this.isGameOver()) {
            final int storeOneCount = this.dataStructure.getStoreCount(1);
            final int storeTwoCount = this.dataStructure.getStoreCount(2);
            if (storeOneCount == storeTwoCount) {
                toReturn = null;
            } else if (storeOneCount > storeTwoCount) {
                toReturn = this.players.get(0);
            } else {
                toReturn = this.players.get(1);
            }
        } else {
            throw new GameNotOverException();
        }
        return toReturn;
    }

    /**
     * Check if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        boolean retVal = false;
        if (this.board.isSideEmpty(1) || this.board.isSideEmpty(7)) {
            retVal = true;
        }
        return retVal;
    }

    /**
     * Start a new game by resetting the board.
     */
    public void startNewGame() {
        this.board.resetBoard();
    }

    /**
     * Get a string representation of the game board.
     *
     * @return A string representation of the game board.
     */
    @Override
    public String toString() {
        return this.board.toString();
    }
}