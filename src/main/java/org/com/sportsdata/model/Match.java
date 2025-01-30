package org.com.sportsdata.model;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final long startTime;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = System.nanoTime();
    }

    public void setScore (int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + "-" + awayScore + " " + awayTeam;
    }
}
