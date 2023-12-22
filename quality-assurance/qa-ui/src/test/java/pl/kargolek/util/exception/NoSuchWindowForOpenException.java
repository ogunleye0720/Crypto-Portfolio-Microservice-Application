package pl.kargolek.util.exception;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NoSuchWindowForOpenException extends RuntimeException {
    public NoSuchWindowForOpenException(String currentWindow) {
        super(String.format("Looks like currentWindow: %s is only one tab/window currently opened", currentWindow));
    }
}
