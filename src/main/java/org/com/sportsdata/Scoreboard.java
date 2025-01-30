package org.com.sportsdata;

import org.com.sportsdata.model.Match;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Match> matches;

    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
    }

    public List<Match> getMatchesInProgress() {
        return new ArrayList<>(matches);
    }
}
