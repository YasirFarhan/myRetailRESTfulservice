/**
 * 
 * 
 * 
 *This exception is thrown when Product id in the url and product id in the body of the PUT request does not match
 */
package myRetails;

/**
 *
 * @author Kanwal
 */
public class UpdateIntegrityException extends Exception {

    public UpdateIntegrityException() {

    }

    public UpdateIntegrityException(String message) {
        super(message);
    }

    public UpdateIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
