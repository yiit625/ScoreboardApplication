package com.sportsdata;

import org.com.sportsdata.Scoreboard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {
    Clock fixedClock = Clock.fixed(Instant.parse("2024-01-30T12:00:00Z"), ZoneId.of("UTC"));

    @Test
    void shouldStartNewMatch() {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        scoreboard.startMatch("Team A", "Team B");
        assertEquals(1, scoreboard.getSummary().size());
    }

    @Test
    void shouldUpdateMatch() {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 2, 1);

        assertEquals("Team A 2-1 Team B", scoreboard.getSummary().get(0));
    }

    @Test
    void shouldFinishMatch() {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");
        assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    void shouldReturnSummaryOrderByTotalScore() {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.startMatch("Team C", "Team D");
        scoreboard.updateScore("Team A", "Team B", 2, 2);
        scoreboard.updateScore("Team C", "Team D", 4, 1);

        var summary = scoreboard.getSummary();
        assertEquals("Team C 4-1 Team D", summary.get(0)); // Highest Total Score
        assertEquals("Team A 2-2 Team B", summary.get(1)); // Lowest Total Score
    }

    @ParameterizedTest
    @CsvSource({
            "Team A, Team A, Team names cannot be the same",
            " , Team B, Team names cannot be blank",
            "Team A, , Team names cannot be blank",
            "Team A, Team B, One of the teams is already in a match"
    })
    void shouldValidateTeamNames(String homeTeam, String awayTeam, String expectedMessage) {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        scoreboard.startMatch("Team A", "Team B");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(homeTeam, awayTeam));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2, Scores cannot be negative",
            "2, -1, Scores cannot be negative"
    })
    void shouldNotAllowNegativeScores(int homeScore, int awayScore, String expectedMessage) {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        scoreboard.startMatch("Team A", "Team B");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", homeScore, awayScore));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldNotUpdateScoreOrFinishNonExistentMatch() {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        assertThrows(NoSuchElementException.class, () ->
                scoreboard.updateScore("Team X", "Team Y", 1, 2));
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatch() {
        Scoreboard scoreboard = new Scoreboard(fixedClock);
        assertTrue(scoreboard.getSummary().isEmpty());
    }
}
