package me.utku.easychatbe.exception;

public class FeatureNotSupportedException extends RuntimeException{
    public FeatureNotSupportedException(String message) {
        super(message);
    }
    public FeatureNotSupportedException() {
        super("Feature not supported");
    }
}
