package org.com.sportsdata.service;

import org.com.sportsdata.comparator.MatchComparator;
import org.com.sportsdata.exceptions.MatchNotFoundException;
import org.com.sportsdata.exceptions.ScoreException;
import org.com.sportsdata.exceptions.TeamNameException;
import org.com.sportsdata.model.Match;
import org.com.sportsdata.util.MatchFinder;
import org.com.sportsdata.validators.MatchValidator;

import java.time.Clock;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final Map<String, Match> matches = new HashMap<>();
    private final Clock clock;

    public Scoreboard(Clock clock) {
        this.clock = clock;
    }

    public void startMatch(String homeTeam, String awayTeam) {
        try {
            MatchValidator.validateTeams(new ArrayList<>(matches.values()), homeTeam, awayTeam);
            matches.put(MatchFinder.getMatchKey(homeTeam, awayTeam), new Match(homeTeam, awayTeam, clock));
            System.out.println("Match started!");
        } catch (TeamNameException e) {
            System.err.println("Failed to start match: " + e.getMessage());
        }
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        try {
            MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);
            matches.remove(MatchFinder.getMatchKey(homeTeam, awayTeam));
            System.out.println("Match finished!");
        } catch (MatchNotFoundException e) {
            System.err.println("Failed to finish match: " + e.getMessage());
        }
    }

   public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
       try {
           MatchValidator.validateScores(homeScore, awayScore);
           MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

           String key = MatchFinder.getMatchKey(homeTeam, awayTeam);
           matches.computeIfPresent(key, (k, match) -> match.updateScore(homeScore, awayScore));
           System.out.println("Match updated!");
       } catch (ScoreException | MatchNotFoundException e) {
           System.err.println("Failed to update score: " + e.getMessage());
       }
    }

    public List<String> getSummary() {
        return List.copyOf(
                matches.values().stream()
                        .sorted(new MatchComparator())
                        .map(Match::getSummary)
                        .toList()
        );
    }
}
