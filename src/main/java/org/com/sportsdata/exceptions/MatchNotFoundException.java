package org.com.sportsdata.exceptions;

import java.util.NoSuchElementException;

public class MatchNotFoundException extends NoSuchElementException {
    public MatchNotFoundException(String message) {
        super(message);
    }
}
