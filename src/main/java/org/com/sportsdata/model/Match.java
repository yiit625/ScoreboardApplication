package org.com.sportsdata.model;

import java.time.Clock;

public record Match (String homeTeam, String awayTeam, int homeScore, int awayScore, long startTime) {
    public Match(String homeTeam, String awayTeam, Clock clock) {
        this(homeTeam, awayTeam, 0, 0, clock.instant().toEpochMilli());
    }

    public Match updateScore (int homeScore, int awayScore) {
       return new Match(homeTeam, awayTeam, homeScore, awayScore, startTime);
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public String getSummary() {
        return homeTeam + " " + homeScore + "-" + awayScore + " " + awayTeam;
    }
}
