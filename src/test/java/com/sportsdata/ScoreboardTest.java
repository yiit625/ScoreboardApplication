package com.sportsdata;

import org.com.sportsdata.Scoreboard;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {
    @Test
    void shouldStartNewMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        assertEquals(1, scoreboard.getSummary().size());
    }

    @Test
    void shouldUpdateMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 2 , 1 );

        var summary = scoreboard.getSummary();
        assertEquals("Team A 2-1 Team B", summary.get(0));
    }

    @Test
    void shouldFinishMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");
        assertTrue(scoreboard.getSummary().isEmpty());
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
    void shouldNotBeSameNameHomeAndAwayTeam() {
        Scoreboard scoreboard = new Scoreboard();
        assertThrows(IllegalArgumentException.class, () ->
                        scoreboard.startMatch("Team A", "Team A"),
                "Team names cannot be same name");
    }

    @Test
    void shouldNotBeBlankHomeOrAwayTeam() {
        Scoreboard scoreboard = new Scoreboard();
        assertThrows(IllegalArgumentException.class, () ->
                        scoreboard.startMatch("", "Team B"),
                "Team names cannot be blank");
        assertThrows(IllegalArgumentException.class, () ->
                        scoreboard.startMatch("Team A", ""),
                "Team names cannot be blank");
    }

    @Test
    void shouldNotAllowNegativeScores() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", -1, 2),
                "Scores cannot be negative");
    }

    @Test
    void shouldNotUpdateScoreOrFinishMatchForNonExistentMatch() {
        Scoreboard scoreboard = new Scoreboard();

        assertThrows(NoSuchElementException.class, () ->
                scoreboard.updateScore("Team X", "Team Y", 1, 2),
                "Match cannot be found");
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatch() {
        Scoreboard scoreboard = new Scoreboard();
        List<String> summary = scoreboard.getSummary();

        assertTrue(summary.isEmpty(), "Summary should be empty when there are no matches");
    }

    @Test
    void shouldNotAllowedToAddSameTeamIfItIsInMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");

        assertThrows(IllegalArgumentException.class, () ->
                        scoreboard.startMatch("Team A", "Team C"),
                "Team A is currently in match");
    }
}
