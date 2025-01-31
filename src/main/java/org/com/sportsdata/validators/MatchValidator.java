package org.com.sportsdata.validators;

import org.com.sportsdata.model.Match;
import org.com.sportsdata.util.MatchFinder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class MatchValidator {
    public static void validateTeams(List<Match> matches, String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be blank");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be the same");
        }
        if (matches.stream().anyMatch(m -> m.homeTeam().equals(homeTeam) || m.awayTeam().equals(homeTeam) ||
                m.homeTeam().equals(awayTeam) || m.awayTeam().equals(awayTeam))) {
            throw new IllegalArgumentException("One of the teams is already in a match");
        }
    }

    public static void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    public static void validateMatchExists(Map<String, Match> matches, String homeTeam, String awayTeam) {
        if (!matches.containsKey(MatchFinder.getMatchKey(homeTeam, awayTeam))) {
            throw new NoSuchElementException("Match cannot be found.");
        }
    }
}
