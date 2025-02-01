package org.com.sportsdata.validators;

import org.com.sportsdata.exceptions.MatchNotFoundException;
import org.com.sportsdata.exceptions.ScoreException;
import org.com.sportsdata.exceptions.TeamNameException;
import org.com.sportsdata.model.Match;
import org.com.sportsdata.util.MatchFinder;

import java.util.List;
import java.util.Map;

/**
 * This class provides methods for validating various aspects of matches.
 */
public class MatchValidator {
    /**
     * Validates the team names for a new match.
     *
     * @param matches A list of existing matches.
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws TeamNameException If any team name is invalid or a team is already in a match.
     */
    public static void validateTeams(List<Match> matches, String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new TeamNameException("Team names cannot be blank");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new TeamNameException("Team names cannot be the same");
        }
        if (matches.stream().anyMatch(m -> m.homeTeam().equals(homeTeam) || m.awayTeam().equals(homeTeam) ||
                m.homeTeam().equals(awayTeam) || m.awayTeam().equals(awayTeam))) {
            throw new TeamNameException("One of the teams is already in a match");
        }
    }

    /**
     * Validates the scores for a match.
     *
     * @param homeScore The score of the home team.
     * @param awayScore The score of the away team.
     * @throws ScoreException If either score is negative.
     */
    public static void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new ScoreException("Scores cannot be negative");
        }
    }

    /**
     * Validates if a match exists with the given teams.
     *
     * @param matches A map of existing matches (key: match key, value: Match object).
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws MatchNotFoundException If no match is found for the given teams.
     */
    public static void validateMatchExists(Map<String, Match> matches, String homeTeam, String awayTeam) {
        if (!matches.containsKey(MatchFinder.getMatchKey(homeTeam, awayTeam))) {
            throw new MatchNotFoundException("Match cannot be found.");
        }
    }
}
