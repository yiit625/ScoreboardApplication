package org.com.sportsdata.model;

public record Match (String homeTeam, String awayTeam, int homeScore, int awayScore, long startTime) {
    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0, 0, System.nanoTime());
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
