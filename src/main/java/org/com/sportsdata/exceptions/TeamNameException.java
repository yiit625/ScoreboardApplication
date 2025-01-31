package org.com.sportsdata.exceptions;

public class TeamNameException extends IllegalArgumentException {
    public TeamNameException(String message) {
        super(message);
    }
}