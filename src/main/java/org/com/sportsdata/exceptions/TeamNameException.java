package org.com.sportsdata.exceptions;

/**
 * This exception is thrown when an invalid team name is encountered.
 * It inherits from the `IllegalArgumentException` class.
 */
public class TeamNameException extends IllegalArgumentException {
    public TeamNameException(String message) {
        super(message);
    }
}