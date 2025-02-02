package org.com.sportsdata.service.impl;

import org.com.sportsdata.comparator.MatchComparator;
import org.com.sportsdata.exceptions.MatchNotFoundException;
import org.com.sportsdata.exceptions.ScoreException;
import org.com.sportsdata.exceptions.TeamNameException;
import org.com.sportsdata.model.Match;
import org.com.sportsdata.service.ScoreboardService;
import org.com.sportsdata.util.MatchFinder;
import org.com.sportsdata.validators.MatchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ScoreboardServiceImpl implements ScoreboardService {
    private static final Logger logger = LoggerFactory.getLogger(ScoreboardServiceImpl.class);
    private final Map<String, Match> matches = new ConcurrentHashMap<>();
    private final Clock clock;

    /**
     * Constructs a new Scoreboard instance with the given Clock.
     *
     * @param clock The Clock instance to use for obtaining the current time.
     */
    public ScoreboardServiceImpl(Clock clock) {
        this.clock = clock;
    }

    /**
     * Starts a new match between the specified home and away teams.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws TeamNameException If the team names are invalid or a match between these teams is already in progress.
     */
    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        try {
            MatchValidator.validateTeams(new ArrayList<>(matches.values()), homeTeam, awayTeam);

            matches.put(getMatchKey(homeTeam, awayTeam), new Match(homeTeam, awayTeam, clock));
        } catch (TeamNameException e) {
            logger.error("Failed to start match: " + e.getMessage());
        }
    }

    /**
     * Finishes an existing match between the specified teams.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws MatchNotFoundException If no match is found for the given teams.
     */
    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        try {
            MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

            matches.remove(getMatchKey(homeTeam, awayTeam));
        } catch (MatchNotFoundException e) {
            logger.error("Failed to finish match: " + e.getMessage());
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
    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        try {
            MatchValidator.validateScores(homeScore, awayScore);
            MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

            matches.compute(getMatchKey(homeTeam, awayTeam), (k, match) ->
                    match != null ? match.updateScore(homeScore, awayScore) : null);
        } catch (ScoreException | MatchNotFoundException e) {
            logger.error("Failed to update match: " + e.getMessage());
        }
    }

    /**
     * Retrieves a summary of all active matches.
     *
     * @return A list of strings, each representing the summary of an active match.
     */
    @Override
    public List<String> getSummary() {
        return matches.values().stream()
                .sorted(new MatchComparator())
                .map(Match::getSummary)
                .toList();
    }

    /**
     * Retrieves String match key
     *
     * @param homeTeam The name of the home team
     * @param awayTeam The name of the away team
     * @return String match key
     */
    private static String getMatchKey(String homeTeam, String awayTeam) {
        return MatchFinder.getMatchKey(homeTeam, awayTeam);
    }
}
