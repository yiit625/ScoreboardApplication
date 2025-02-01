package org.com.sportsdata.exceptions;

import java.util.NoSuchElementException;

/**
 * This exception is thrown when a requested match cannot be found in the Scoreboard.
 * It inherits from the `NoSuchElementException` class.
 */
public class MatchNotFoundException extends NoSuchElementException {
    public MatchNotFoundException(String message) {
        super(message);
    }
}
