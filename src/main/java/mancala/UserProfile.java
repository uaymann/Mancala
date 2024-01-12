package mancala;

import java.io.Serializable;

/**
 * Represents a player's user profile with their stats in the Mancala game.
 */
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    private String playerName;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahGamesWon;
    private int ayoGamesWon;

    /**
     * Default constructor to initialize a new UserProfile.
     */
    public UserProfile() {
        this.playerName = "";
        this.kalahGamesPlayed = 0;
        this.ayoGamesPlayed = 0;
        this.kalahGamesWon = 0;
        this.ayoGamesWon = 0;
    }

    /**
     * Constructor to initialize a new UserProfile with a name.
     *
     * @param userName The name of the user.
     */
    public UserProfile(final String userName) {
        this.playerName = userName;
        this.kalahGamesPlayed = 0;
        this.ayoGamesPlayed = 0;
        this.kalahGamesWon = 0;
        this.ayoGamesWon = 0;
    }

    /**
     * Constructor to initialize a new UserProfile with detailed game statistics.
     *
     * @param userName     The name of the user.
     * @param kalahPlayed   The number of Kalah games played.
     * @param ayoPlayed     The number of Ayo games played.
     * @param kalahWon      The number of Kalah games won.
     * @param ayoWon        The number of Ayo games won.
     */
    public UserProfile(final String userName, final int kalahPlayed, final int ayoPlayed, final int kalahWon, final int ayoWon) {
        this.playerName = userName;
        this.kalahGamesPlayed = kalahPlayed;
        this.ayoGamesPlayed = ayoPlayed;
        this.kalahGamesWon = kalahWon;
        this.ayoGamesWon = ayoWon;
    }

    /**
     * Sets the user's name.
     *
     * @param userName The name to set.
     */
    public void setUserName(final String userName) {
        this.playerName = userName;
    }

    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getUserName() {
        return this.playerName;
    }

    /**
     * Adds a Kalah game to the user's statistics.
     *
     * @param wonGame Indicates whether the user won the game.
     */
    public void addKalahGames(final Boolean wonGame) {
        this.kalahGamesPlayed += 1;
        if (wonGame) {
            this.addKalahGamesWon();
        }
    }

    /**
     * Gets the number of Kalah games played by the user.
     *
     * @return The number of Kalah games played.
     */
    public int getKalahGamesPlayed() {
        return this.kalahGamesPlayed;
    }

    /**
     * Gets the number of Kalah games won by the user.
     *
     * @return The number of Kalah games won.
     */
    public int getKalahGamesWon() {
        return this.kalahGamesWon;
    }

    /**
     * Adds a won Kalah game to the user's statistics.
     */
    private void addKalahGamesWon() {
        this.kalahGamesWon += 1;
    }

    /**
     * Gets the number of Ayo games played by the user.
     *
     * @return The number of Ayo games played.
     */
    public int getAyoGamesPlayed() {
        return this.ayoGamesPlayed;
    }

    /**
     * Adds an Ayo game to the user's statistics.
     *
     * @param wonGame Indicates whether the user won the game.
     */
    public void addAyoGames(final Boolean wonGame) {
        this.ayoGamesPlayed += 1;
        if (wonGame) {
            this.addAyoGamesWon();
        }
    }

    /**
     * Gets the number of Ayo games won by the user.
     *
     * @return The number of Ayo games won.
     */
    public int getAyoGamesWon() {
        return this.ayoGamesWon;
    }

    /**
     * Adds a won Ayo game to the user's statistics.
     */
    private void addAyoGamesWon() {
        this.ayoGamesWon += 1;
    }

    /**
     * Gets the total number of games played by the user.
     *
     * @return The total number of games played.
     */
    public int getGamesPlayed() {
        return this.kalahGamesPlayed + this.ayoGamesPlayed;
    }

    /**
     * Gets the total number of games won by the user.
     *
     * @return The total number of games won.
     */
    public int getGamesWon() {
        return this.kalahGamesWon + this.ayoGamesWon;
    }

    /**
     * Returns a string representation of the UserProfile.
     *
     * @return A string representation of the UserProfile.
     */
    @Override
    public String toString() {
        return "UserProfile { " +
                "Username: " + this.playerName + '\'' +
                ", # of Kalah Games Played = " + this.kalahGamesPlayed +
                ", # of Ayo Games Played = " + this.ayoGamesPlayed +
                ", # of Kalah Games Won = " + this.kalahGamesWon +
                ", # of Ayo Games Won = " + this.ayoGamesWon +
                ", # of Games Played = " + this.getGamesPlayed() +
                ", # of Games Won = " + this.getGamesWon() +
                " }";
    }
}