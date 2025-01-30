package org.com.sportsdata;

import org.com.sportsdata.comparator.MatchComparator;
import org.com.sportsdata.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be blank");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be same name");
        }
        if (matchExists(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Match between these teams is already in progress");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = findMatch(homeTeam, awayTeam);
        match.updateScore(homeScore, awayScore);
    }

    public List<String> getSummary() {
        return matches.stream()
                .sorted(new MatchComparator())
                .map(Match::toString)
                .toList();
    }

    private boolean matchExists(String homeTeam, String awayTeam) {
        return matches.stream().anyMatch(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam));
    }

    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Match cannot be found"));
    }
}
