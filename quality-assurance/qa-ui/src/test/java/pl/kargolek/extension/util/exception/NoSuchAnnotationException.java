package pl.kargolek.extension.util.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSuchAnnotationException extends RuntimeException{
    public NoSuchAnnotationException(String message) {
        super(message);
    }
}
