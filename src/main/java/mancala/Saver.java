package mancala;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.*;

/**
 * A utility class for saving and loading objects to/from files.
 */
public class Saver implements Serializable {

    private static final String ASSETS_FOLDER = "assets/";
    private static final long serialVersionUID = 1L;

    static {
        // Initialize: check and create assets folder if not exists
        initializeAssetsFolder();
    }

    /**
     * Initializes the assets folder by checking and creating it if it does not exist.
     */
    private static void initializeAssetsFolder() {
        try {
            final Path assetsPath = Paths.get(ASSETS_FOLDER);
            if (Files.notExists(assetsPath)) {
                Files.createDirectories(assetsPath);
            }
        } catch (IOException e) {
            e.setStackTrace(null);
        }
    }

    /**
     * Saves a serializable object to a file.
     *
     * @param toSave   The object to save.
     * @param filename The name of the file to save the object to.
     * @throws IOException If an I/O error occurs during the save process.
     */
    public static void saveObject(final Serializable toSave, final String filename) throws IOException {

        if (filename.isBlank() || filename.isEmpty()) {
            throw new IOException("Filename is null.");
        }

        try {
            final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("assets/" + filename));
            output.writeObject(toSave);
            output.close();
        } catch (FileNotFoundException e) {
            throw new IOException("Error saving to file.");
        }

    }

    /**
     * Loads a serializable object from a file.
     *
     * @param filename The name of the file to load the object from.
     * @return The loaded serializable object.
     * @throws IOException If an I/O error occurs during the load process.
     */
    public static Serializable loadObject(final String filename) throws IOException {

        if (filename.isBlank() || filename.isEmpty()) {
            throw new IOException("Filename is null.");
        }

        Serializable retVal;

        try {
            final ObjectInputStream input = new ObjectInputStream(new FileInputStream("assets/" + filename));
            retVal = (Serializable) input.readObject();
            input.close();
        } catch (ClassNotFoundException | FileNotFoundException e) {
            throw new IOException("Error loading file.");
        }
        return retVal;
    }

}