package ir.parliran.global.upload;

/**
 * ©hFahimi.com @ 2019/12/21 14:44
 */
public class FileNotFoundException extends StorageException {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
