package com.sportsdata.service;

import org.com.sportsdata.service.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        scoreboard.startMatch("Team E", "Team F");
        scoreboard.updateScore("Team A", "Team B", 2, 2);
        scoreboard.updateScore("Team E", "Team F", 1, 3);
        scoreboard.updateScore("Team C", "Team D", 4, 1);

        var summary = scoreboard.getSummary();
        assertEquals("Team C 4-1 Team D", summary.get(0));
        assertEquals("Team E 1-3 Team F", summary.get(1));
        assertEquals("Team A 2-2 Team B", summary.get(2));
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatch() {
        assertTrue(scoreboard.getSummary().isEmpty());
    }
}
