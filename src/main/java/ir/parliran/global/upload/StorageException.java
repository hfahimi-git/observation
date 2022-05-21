package ir.parliran.global.upload;

/**
 * ©hFahimi.com @ 2019/12/21 14:40
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
