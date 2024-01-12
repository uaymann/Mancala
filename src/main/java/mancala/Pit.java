package mancala;

import java.io.Serializable;

/**
 * Represents a pit in the Mancala game.
 */
public class Pit implements Countable, Serializable {

    private static final long serialVersionUID = 1L;
    private int stoneCount;

    /**
     * Constructor to initialize a new pit
     */
    protected Pit() {
        this.stoneCount = 0;
    }

    /**
     * Adds a stone to the pit.
     */
    @Override
    public void addStone() {
        this.stoneCount++;
    }

    /**
     * Adds a specified number of stones to the pit.
     *
     * @param numToAdd The number of stones to add to the pit.
     */
    @Override
    public void addStones(final int numToAdd) {
        this.stoneCount += numToAdd;
    }

    /**
     * Gets the number of stones in the pit.
     *
     * @return The number of stones in the pit.
     */
    @Override
    public int getStoneCount() {
        return this.stoneCount;
    }

    /**
     * Removes all stones from the pit and returns the amount.
     *
     * @return The number of stones removed from the pit.
     */
    @Override
    public int removeStones() {
        final int stonesRemoved = this.stoneCount;
        this.stoneCount = 0;
        return stonesRemoved;
    }

    /**
     * Returns a string representation of the pit.
     *
     * @return A string representation of the pit, including the number of stones in the pit.
     */
    @Override
    public String toString() {
        return "Pit[" + this.stoneCount + "]";
    }
}
