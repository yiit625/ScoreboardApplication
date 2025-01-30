package org.com.sportsdata;

import org.com.sportsdata.model.Match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {
    private final List<Match> matches;

    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        System.out.println(matchExists(homeTeam, awayTeam));
        if (matchExists(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Match between these teams is already in progress");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        if (!matchExists(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Non existent matches cannot be finished");
        }
        matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        System.out.println(matchExists(homeTeam, awayTeam));
        if (!matchExists(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Non existent matches cannot be updated");
        }
        for (Match match: matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                match.setScore(homeScore, awayScore);
                return;
            }
        }
    }

    public List<String> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match :: getTotalScore)
                        .thenComparing(Match::getStartTime).reversed())
                .map(Match::toString)
                .toList();
    }

    public String getMatchScore(String homeTeam, String awayTeam) {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                return match.toString();
            }
        }
        return null;
    }

    public List<Match> getMatchesInProgress() {
        return new ArrayList<>(matches);
    }

    private boolean matchExists(String homeTeam, String awayTeam) {
        return matches.stream().anyMatch(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam));
    }
}
