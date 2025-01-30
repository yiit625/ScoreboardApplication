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
        matches.add(new Match(homeTeam, awayTeam));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Match match: matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                match.setScore(homeScore, awayScore);
                return;
            }
        }
    }

    public List<String> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match :: getTotalScore))
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
}
