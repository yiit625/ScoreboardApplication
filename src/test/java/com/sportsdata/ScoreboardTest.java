package com.sportsdata;

import org.com.sportsdata.Scoreboard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void shouldUpdateMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 2 /*First Team Score */, 1 /*Second Team Score */);
        assertEquals("Team A 2-1 Team B", scoreboard.getMatchScore("Team A", "Team B"));
    }

    @Test
    void shouldReturnSummaryOrderByTotalScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.startMatch("Team C", "Team D");
        scoreboard.updateScore("Team A", "Team B", 2 , 2 );
        scoreboard.updateScore("Team C", "Team D", 4 , 1 );

        var summary = scoreboard.getSummary();
        assertEquals("Team C 4-1 Team D", summary.get(0)); // Highest Total Score
        assertEquals("Team A 2-2 Team B", summary.get(1)); // Lowest Total Score

    }

    @Test
    void shouldReturnSummaryOrderByTotalScoreWithSameTotalScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.startMatch("Team C", "Team D");
        scoreboard.updateScore("Team A", "Team B", 2 , 2 );
        scoreboard.updateScore("Team C", "Team D", 3 , 1 );

        var summary = scoreboard.getSummary();
        assertEquals("Team C 3-1 Team D", summary.get(0));
        assertEquals("Team A 2-2 Team B", summary.get(1));
    }

    @Test
    void shouldNotStartMatchWithSameTeamsTwice() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("Team A", "Team B"),
                "Match between these teams is already in progress");
    }

    @Test
    void shouldNotAllowNegativeScores() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", -1, 2),
                "Scores cannot be negative");
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", 1, -2),
                "Scores cannot be negative");
    }

    @Test
    void shouldNotUpdateScoreForNonExistentMatch() {
        Scoreboard scoreboard = new Scoreboard();

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team X", "Team Y", 1, 2),
                "Non existent matches cannot be updated");
    }

    @Test
    void shouldNotFinishingNonExistentMatch() {
        Scoreboard scoreboard = new Scoreboard();

        assertThrows(IllegalArgumentException.class, () ->
                        scoreboard.finishMatch("Team X", "Team Y"),
                "Non existent matches cannot be finished");
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatch() {
        Scoreboard scoreboard = new Scoreboard();
        List<String> summary = scoreboard.getSummary();

        assertTrue(summary.isEmpty(), "Summary should be empty when there are no matches");
    }
}
