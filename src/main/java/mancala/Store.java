package mancala;

import java.io.Serializable;

/**
 * Represents a store in the Mancala game.
 */
public class Store implements Countable, Serializable {

    private static final long serialVersionUID = 1L;
    private Player storeOwner;
    private int totalStones;

    /**
     * Default constructor to initialize a new store.
     */
    protected Store() {
        this.storeOwner = null;
        this.totalStones = 0;
    }

    /**
     * Sets the owner of the store.
     *
     * @param player The player to set as the owner of the store.
     */
    protected void setOwner(final Player player) {
        this.storeOwner = player;
    }

    /**
     * Gets the owner of the store.
     *
     * @return The owner of the store.
     */
    public Player getOwner() {
        return this.storeOwner;
    }

    /**
     * Gets the total number of stones in the store.
     *
     * @return The total number of stones in the store.
     */
    @Override
    public int getStoneCount() {
        return this.totalStones;
    }

    /**
     * Adds a specified number of stones to the store.
     *
     * @param numToAdd The number of stones to add to the store.
     */
    @Override
    public void addStones(final int numToAdd) {
        this.totalStones += numToAdd;
    }

    /**
     * Adds a single stone to the store.
     */
    @Override
    public void addStone() {
        this.totalStones++;
    }

    /**
     * Empties the store and returns the number of stones that were in it.
     *
     * @return The number of stones removed from the store.
     */
    @Override
    public int removeStones() {
        final int removedStones = this.totalStones;
        this.totalStones = 0;
        return removedStones;
    }

    /**
     * Returns a string representation of the store.
     *
     * @return A string representation of the store.
     */
    @Override
    public String toString() {
        return this.storeOwner + "'s Store[" + this.totalStones + "]";
    }
}