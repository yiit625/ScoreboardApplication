package com.sportsdata;

import org.com.sportsdata.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardTest {
    Clock fixedClock = Clock.fixed(Instant.parse("2024-01-30T12:00:00Z"), ZoneId.of("UTC"));
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard(fixedClock);
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void shouldStartNewMatch() {
        scoreboard.startMatch("Team A", "Team B");

        assertEquals(1, scoreboard.getSummary().size());
    }

    @Test
    void shouldUpdateMatch() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 2, 1);

        assertEquals("Team A 2-1 Team B", scoreboard.getSummary().get(0));
    }

    @Test
    void shouldFinishMatch() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");

        assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    void shouldReturnSummaryOrderByTotalScore() {
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
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.startMatch(homeTeam, awayTeam);

        assertTrue(errContent.toString().contains(expectedMessage));
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2, Scores cannot be negative",
            "2, -1, Scores cannot be negative"
    })
    void shouldNotAllowNegativeScores(int homeScore, int awayScore, String expectedMessage) {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", homeScore, awayScore);

        assertTrue(errContent.toString().contains(expectedMessage));
    }

    @Test
    void shouldNotUpdateScoreOrFinishNonExistentMatch() {
        scoreboard.updateScore("Team X", "Team Y", 1, 2);

        assertTrue(errContent.toString().contains("Match cannot be found."));
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatch() {
        assertTrue(scoreboard.getSummary().isEmpty());
    }
}
