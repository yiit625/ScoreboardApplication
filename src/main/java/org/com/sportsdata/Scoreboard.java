package org.com.sportsdata;

import org.com.sportsdata.comparator.MatchComparator;
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
        MatchValidator.validateTeams(new ArrayList<>(matches.values()), homeTeam, awayTeam);
        matches.put(MatchFinder.getMatchKey(homeTeam, awayTeam), new Match(homeTeam, awayTeam, clock));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);
        matches.remove(MatchFinder.getMatchKey(homeTeam, awayTeam));
    }

   public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
       MatchValidator.validateScores(homeScore, awayScore);
       MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

       String key = MatchFinder.getMatchKey(homeTeam, awayTeam);
       matches.computeIfPresent(key, (k, match) -> match.updateScore(homeScore, awayScore));
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
