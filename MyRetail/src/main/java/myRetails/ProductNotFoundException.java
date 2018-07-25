package myRetails;

/**
 *
 * @author Yasir Farhan
 *
 * This exception is thrown when product is not found in local storage
 *
 */
public class ProductNotFoundException extends Exception {

    public ProductNotFoundException() {

    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
