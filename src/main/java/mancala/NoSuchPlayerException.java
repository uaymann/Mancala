package mancala;

public class NoSuchPlayerException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoSuchPlayerException() {

        super("Player not found.");

    }

}