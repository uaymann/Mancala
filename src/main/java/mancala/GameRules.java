package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {

    private static final long serialVersionUID = 1L;
    private final MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private int freeTurn = 0;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's 6 pits) is empty.
     *
     * @param pitNum The number of a pit on a side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(int pitNum) {

        int numStones = 0;
        int ctr = 0;
        boolean retVal = false;

        if (pitNum > 0 && pitNum < 7) {
            pitNum = 1; // set to first pit on that side
        } else if (pitNum > 6 && pitNum < 13) {
            pitNum = 7;
        }
        while (ctr < 6) {
            numStones += getNumStones(pitNum + ctr); // add stones in each pit
            ctr++;
        }
        if (numStones == 0) { // if all pits on that side are empty
            storeRemainingStones(pitNum); // Store opponent's stones
            retVal = true;
        }

        return retVal;
    }

    /**
     * Stores all remaining stones once a side is empty/game over.
     *
     * @param pitNum The number of a pit on one side.
     */
    private void storeRemainingStones(int pitNum) {

        int playerNum = 0;
        int numStones = 0;
        int ctr = 0;

        if (pitNum > 0 && pitNum < 7) {
            pitNum = 7;
            playerNum = 2; //Opponent Store
        } else if (pitNum > 6 && pitNum < 13) {
            pitNum = 1;
            playerNum = 1; //Opponent Store
        }
        while (ctr < 6) { // clear all pits on that side
            numStones += gameBoard.removeStones(pitNum + ctr);
            ctr++;
        }
        if (numStones > 0) { // add stones to that player's store
            gameBoard.addToStore(playerNum, numStones);
        }
    }

    /**
     * Checks if a pit belongs to current player.
     *
     * @param playerNum The player number (1 or 2).
     * @param pitNum The number of the pit.
     *
     * @return True if pit belongs to current player, false otherwise.
     */
    protected boolean checkOwnPit(final int playerNum, final int pitNum) {

        boolean retVal = false;
        if (playerNum == 1 && pitNum > 0 && pitNum < 7)  {
            retVal = true;
        } else if (playerNum == 2 && pitNum > 6 && pitNum < 13) {
            retVal = true;
        }
        return retVal;

    }

    /**
     * Checks if a store belongs to current player.
     *
     * @param playerNum The player number (1 or 2).
     * @param pitNum The number of the pit.
     *
     * @return True if store belongs to current player, false otherwise.
     */
    protected boolean checkOwnStore(final int playerNum, final int pitNum) {

        boolean retVal = false;
        if (playerNum == 1 && pitNum == 7 || playerNum == 2 && pitNum == 13)  {
            retVal = true;
        }
        return retVal;

    }

    /**
     * Sets a bonus turn.
     */
    protected void setFreeTurn() {
        this.freeTurn = 1;
    }

    /**
     * Checks if player gets bonus turn.
     *
     * @return True if player gets a bonus turn, false otherwise.
     */
    protected boolean isFreeTurn() {
        boolean retVal = false;
        if (this.freeTurn > 0) {
            this.freeTurn = 0; // set to 0 after free turn taken
            retVal = true;
        }
        return retVal;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Get the current player.
     *
     * @return The player number (1 or 2).
     */
    public int getPlayer() {
        return currentPlayer;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {

        final Store newStore1 = new Store();
        final Store newStore2 = new Store();

        resetBoard();

        one.setStore(newStore1);
        newStore1.setOwner(one);
        gameBoard.setStore(newStore1, 1);

        two.setStore(newStore2);
        newStore2.setOwner(two);
        gameBoard.setStore(newStore2, 2);

        /* make a new store in this method, set the owner
        then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.emptyPits();
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    /*  Helper method for toString */
    private void printBlankSpace(final StringBuilder string){
        string.append("\n|\t| ");
        for (int i = 0; i < 40; i++) {
            string.append(" ");
        }
        string.append("\t|\t|");
    }

    /**
     * Returns a string representation of the game board.
     *
     * @return A string representation of the game board.
     */
    @Override
    public String toString() {
        final StringBuilder boardString = new StringBuilder();
        boardString.append("\n");
        for (int i = 0; i < 65; i++) {
            boardString.append("-");
        }
        printBlankSpace(boardString);
        printBlankSpace(boardString);
        boardString.append("\n|  S2\t| ");
        for (int i = 11; i >= 6; i--) {
            boardString.append("P").append(i + 1).append("\t");
        }
        boardString.append("|  S1\t| ");
        printBlankSpace(boardString);
        boardString.append("\n|  \t| ");
        for (int i = 11; i >= 6; i--) {
            boardString.append("(").append(getNumStones(i + 1)).append(")\t");
        }
        boardString.append("|  \t| ");
        printBlankSpace(boardString);
        printBlankSpace(boardString);
        boardString.append("\n|  [").append(gameBoard.getStoreCount(2)).append("]\t|");
        for (int i = 0; i < 47; i++) {
            boardString.append("-");
        }
        boardString.append("|  [").append(gameBoard.getStoreCount(1)).append("]\t|");
        printBlankSpace(boardString);
        printBlankSpace(boardString);
        boardString.append("\n|\t| ");
        for (int i = 0; i <= 5; i++) {
            boardString.append("(").append(getNumStones(i + 1)).append(")\t");
        }
        boardString.append("|\t| ");
        printBlankSpace(boardString);
        boardString.append("\n|  \t| ");
        for (int i = 0; i <= 5; i++) {
            boardString.append(" P").append(i + 1).append("\t");
        }
        boardString.append("|  \t| ");
        printBlankSpace(boardString);
        printBlankSpace(boardString);
        boardString.append("\n");
        for (int i = 0; i < 65; i++) {
            boardString.append("-");
        }
        boardString.append("\n");
        return boardString.toString();
    }
}
