package org.com.sportsdata.util;

import org.com.sportsdata.model.Match;

import java.util.List;
import java.util.NoSuchElementException;

public class MatchFinder {
    public static Match findMatch(List<Match> matches, String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.homeTeam().equalsIgnoreCase(homeTeam) && m.awayTeam().equalsIgnoreCase(awayTeam))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Match cannot be found"));
    }
}
