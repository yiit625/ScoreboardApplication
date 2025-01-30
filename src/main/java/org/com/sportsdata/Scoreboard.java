package org.com.sportsdata;

import org.com.sportsdata.comparator.MatchComparator;
import org.com.sportsdata.model.Match;
import org.com.sportsdata.util.MatchFinder;
import org.com.sportsdata.validators.MatchValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        MatchValidator.validateTeams(homeTeam, awayTeam, matches);
        MatchValidator.validateMatchExists(matches, homeTeam, awayTeam);

        matches.add(new Match(homeTeam, awayTeam));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = MatchFinder.findMatch(matches, homeTeam, awayTeam);
        matches.remove(match);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        MatchValidator.validateScores(homeScore, awayScore);
        Match match = MatchFinder.findMatch(matches, homeTeam, awayTeam);
        matches.remove(match);
        matches.add(match.updateScore(homeScore, awayScore));
    }

    public List<String> getSummary() {
        return matches.stream()
                .sorted(new MatchComparator())
                .map(Match::getSummary)
                .toList();
    }
}
