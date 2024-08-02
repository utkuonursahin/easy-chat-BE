package me.utku.easychatbe.exception;

/**
 * This exception is thrown when a feature is not supported.
 */
public class FeatureNotSupportedException extends RuntimeException {
    public FeatureNotSupportedException(String message) {
        super(message);
    }

    public FeatureNotSupportedException() {
        super("Feature not supported");
    }
}
