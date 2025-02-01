package org.com.sportsdata.model;

import java.time.Clock;

/**
 * Represents a sports match between two teams.
 * This class is immutable.
 */
public record Match (String homeTeam, String awayTeam, int homeScore, int awayScore, long startTime) {
    /**
     * Constructs a new Match with the specified teams and starting time.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @param clock    The Clock instance to use for obtaining the current time.
     */
    public Match(String homeTeam, String awayTeam, Clock clock) {
        this(homeTeam, awayTeam, 0, 0, clock.instant().toEpochMilli());
    }

    /**
     * Creates a new Match with updated scores.
     *
     * @param homeScore The updated score of the home team.
     * @param awayScore The updated score of the away team.
     * @return A new Match object with the updated scores.
     */
    public Match updateScore (int homeScore, int awayScore) {
       return new Match(homeTeam, awayTeam, homeScore, awayScore, startTime);
    }

    /**
     * Calculates the total score of the match.
     *
     * @return The sum of homeScore and awayScore.
     */
    public int getTotalScore() {
        return homeScore + awayScore;
    }

    /**
     * Returns a summary string representing the match.
     *
     * @return A string in the format "homeTeam homeScore-awayScore awayTeam".
     */
    public String getSummary() {
        return homeTeam + " " + homeScore + "-" + awayScore + " " + awayTeam;
    }
}
