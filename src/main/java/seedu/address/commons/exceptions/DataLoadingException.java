package seedu.address.commons.exceptions;

/**
 * Represents an error during loading of data from a file, optionally including the
 * location of a backup copy of the corrupted resource.
 */
public class DataLoadingException extends Exception {
    /**
     * Constructs a {@code DataLoadingException} without a backup path.
     *
     * @param cause the underlying exception thrown when reading failed
     */
    public DataLoadingException(Exception cause) {
        super(cause);
    }

}
