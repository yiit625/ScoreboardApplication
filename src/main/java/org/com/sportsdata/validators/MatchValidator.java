package org.com.sportsdata.validators;

import org.com.sportsdata.model.Match;

import java.util.List;

public class MatchValidator {
    public static void validateTeams(String homeTeam, String awayTeam, List<Match> matches) {
        if (homeTeam == null || awayTeam == null || homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be blank");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be the same");
        }
        if (matches.stream().anyMatch(m -> m.homeTeam().equals(homeTeam))) {
            throw new IllegalArgumentException(homeTeam + " is currently in match");
        }
        if (matches.stream().anyMatch(m -> m.awayTeam().equals(awayTeam))) {
            throw new IllegalArgumentException(awayTeam + " is currently in match");
        }
    }

    public static void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    public static void validateMatchExists(List<Match> matches, String homeTeam, String awayTeam ) {
        if (matches.stream().anyMatch(m -> m.homeTeam().equals(homeTeam) && m.awayTeam().equals(awayTeam))) {
            throw new IllegalArgumentException("Match between these teams is already in progress");
        }
    }
}
