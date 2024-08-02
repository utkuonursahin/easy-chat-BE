package me.utku.easychatbe.exception;

/**
 * This exception is thrown when the client tries to update its password but provided the current password is incorrect.
 */
public class PasswordIsIncorrectForChangeException extends RuntimeException {
    public PasswordIsIncorrectForChangeException() {
        super("Password is incorrect for update");
    }
}
