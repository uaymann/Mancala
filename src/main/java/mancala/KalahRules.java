package mancala;

/**
 * Represents the kalah rule set in the Mancala game.
 */
public class KalahRules extends GameRules {

    private final MancalaDataStructure gameBoard;

    /**
     * Constructs KalahRules and initializes the game board.
     */
    public KalahRules() {
        super();
        gameBoard = getDataStructure();
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {

        final int inStore = gameBoard.getStoreCount(playerNum);
        final int numMoves = getNumStones(startPit); // Get number of moves (# of stones in pit)

        if (!checkOwnPit(playerNum, startPit)) {
            throw new InvalidMoveException();
        } else if (numMoves <= 0) {
            throw new InvalidMoveException();
        } else {
            setPlayer(playerNum);
            gameBoard.setIterator(startPit, playerNum, false);
            distributeStones(startPit);
        }

        return gameBoard.getStoreCount(playerNum) - inStore; // returning amount of stones added to store
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    protected int distributeStones(final int startPit) {

        final int numStones = gameBoard.removeStones(startPit);
        int toDistribute = numStones; // Get amount to distribute (# of stones in pit)

        gameBoard.setIterator(startPit, getPlayer(), false);

        while (toDistribute > 0) { // distribute stones until none left

            final Countable data = gameBoard.next();
            data.addStone();

            final int pitNum = gameBoard.getPitNum();

            if (toDistribute == 1 && pitNum > 0) {
                if (data.getStoneCount() == 1 && checkOwnPit(getPlayer(), pitNum)) { // check if last stone ends up in own pit to capture
                    captureStones(pitNum);
                }
            } else if (toDistribute == 1 && pitNum < 0) {
                setFreeTurn();
            }
            toDistribute--; // decrement stone count once put in pit

        }

        return numStones;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    protected int captureStones(final int stoppingPoint) {

        int numStones = 0;
        int[] arrayOppPit = new int[12]; // an array to rep. opposite side's pits
        int ctr = 11;

        for (int i = 0; i < 12; i++) { // opposite pit (1 <-> 12, 2 <-> 11)
            arrayOppPit[i] = ctr;
            ctr--;
        }
        final int oppPit = arrayOppPit[stoppingPoint-1] + 1; // set to number of opposite pit
        if (gameBoard.getNumStones(oppPit) > 0) { // if opposite pit contains stones
            numStones = gameBoard.removeStones(stoppingPoint);
            numStones += gameBoard.removeStones(oppPit); // add last stone with all stones in opp pit
            gameBoard.addToStore(getPlayer(), numStones); // add stones to current player's store
        }
        return numStones;
    }

}