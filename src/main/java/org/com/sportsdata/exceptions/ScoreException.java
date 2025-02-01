package org.com.sportsdata.exceptions;

/**
 * This exception is thrown when an invalid score is encountered.
 * It inherits from the `IllegalArgumentException` class.
 */
public class ScoreException extends IllegalArgumentException {
    public ScoreException(String message) {
        super(message);
    }
}
