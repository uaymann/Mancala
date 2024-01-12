package mancala;

import java.io.Serializable;

/**
 * Represents a player in the Mancala game.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    private UserProfile user;
    private Store store;

    /**
     * Constructor to initialize a new player with default values.
     */
    public Player() {
        this.user = new UserProfile();
        this.store = null;
    }

    /**
     * Constructor to initialize a new player with a given user profile.
     *
     * @param uProfile The user profile to associate with the player.
     */
    protected Player(final UserProfile uProfile) {
        this.user = uProfile;
        this.store = null;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        String toReturn;
        if (this.user != null) {
            toReturn = this.user.getUserName();
        } else {
            toReturn = "";
        }
        return toReturn;
    }

    /**
     * Sets the user profile for the player.
     *
     * @param userProf The user profile to set for the player.
     */
    public void setUserProfile(final UserProfile userProf) {
        this.user = userProf;
    }

    /**
     * Gets the user profile associated with the player.
     *
     * @return The user profile associated with the player.
     */
    public UserProfile getUserProfile() {
        return this.user;
    }

    /**
     * Gets the store owned by the player.
     *
     * @return The store owned by the player.
     */
    protected Store getStore() {
        return this.store;
    }

    /**
     * Gets the count of the total stones in the player's store.
     *
     * @return The count of stones in the player's store.
     */
    protected int getStoreCount() {
        return this.store.getStoneCount();
    }

    /**
     * Sets the store for the player.
     *
     * @param playerStore The store to set for the player.
     */
    protected void setStore(final Store playerStore) {
        this.store = playerStore;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return A string representation of the player, including the player's name.
     */
    @Override
    public String toString() {
        return "Player: " + this.getName();
    }
}