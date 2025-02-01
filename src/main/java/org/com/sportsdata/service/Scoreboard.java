package org.com.sportsdata.service;

import org.com.sportsdata.comparator.MatchComparator;
import org.com.sportsdata.exceptions.MatchNotFoundException;
import org.com.sportsdata.exceptions.ScoreException;
import org.com.sportsdata.exceptions.TeamNameException;
import org.com.sportsdata.model.Match;
import org.com.sportsdata.util.MatchFinder;
import org.com.sportsdata.validators.MatchValidator;

import java.time.Clock;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class manages the scoreboard for sports matches.
 * It provides methods to start, finish, and update scores for matches.
 * It also provides a summary of the current matches.
 */
public class Scoreboard {
    private final Map<String, Match> matches = new ConcurrentHashMap<>();
    private final Clock clock;

    /**
     * Constructs a new Scoreboard instance with the given Clock.
     *
     * @param clock The Clock instance to use for obtaining the current time.
     */
    public Scoreboard(Clock clock) {
        this.clock = clock;
    }

    /**
     * Starts a new match between the specified home and away teams.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws TeamNameException If the team names are invalid or a match between these teams is already in progress.
     */
    public void startMatch(String homeTeam, String awayTeam) {
        try {
            MatchValidator.validateTeams(new ArrayList<>(matches.values()), homeTeam, awayTeam);

            matches.put(MatchFinder.getMatchKey(homeTeam, awayTeam), new Match(homeTeam, awayTeam, clock));
            System.out.println("Match started!");
        } catch (TeamNameException e) {
            System.err.println("Failed to start match: " + e.getMessage());
        }
    }

    /**
     * Finishes an existing match between the specified teams.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws MatchNotFoundException If no match is found for the given teams.
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        try {
            MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

            matches.remove(MatchFinder.getMatchKey(homeTeam, awayTeam));
            System.out.println("Match finished!");
        } catch (MatchNotFoundException e) {
            System.err.println("Failed to finish match: " + e.getMessage());
        }
    }

    /**
     * Updates the score for an existing match.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @param homeScore The current score of the home team.
     * @param awayScore The current score of the away team.
     * @throws ScoreException If the scores are invalid.
     * @throws MatchNotFoundException If no match is found for the given teams.
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        try {
            MatchValidator.validateScores(homeScore, awayScore);
            MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

            matches.compute(MatchFinder.getMatchKey(homeTeam, awayTeam), (k, match) ->
                    match != null ? match.updateScore(homeScore, awayScore) : null);
            System.out.println("Match updated!");
        } catch (ScoreException | MatchNotFoundException e) {
            System.err.println("Failed to update score: " + e.getMessage());
        }
    }

    /**
     * Retrieves a summary of all active matches.
     *
     * @return A list of strings, each representing the summary of an active match.
     */
    public List<String> getSummary() {
        return matches.values().stream()
                .sorted(new MatchComparator())
                .map(Match::getSummary)
                .toList();
    }
}
