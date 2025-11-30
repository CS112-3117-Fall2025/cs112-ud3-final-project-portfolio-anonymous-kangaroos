package cs112.ud3;

/**
 * Custom exception thrown when 2 arrays
 * do not have matching lengths
 *
 * @author Isabella Watson
 * @version 1.0.0
 */
public class MismatchedDataLengthException extends RuntimeException {
    public MismatchedDataLengthException(String message) {
        super(message);
    }
}
