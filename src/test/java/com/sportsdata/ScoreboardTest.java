package com.sportsdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.com.sportsdata.Scoreboard;
import org.junit.jupiter.api.Test;

public class ScoreboardTest {
    @Test
    void shouldStartNewMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        assertEquals(1, scoreboard.getMatchesInProgress().size());
    }

    @Test
    void shouldFinishMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");
        assertEquals(0, scoreboard.getMatchesInProgress().size());
    }
}
