package org.com.sportsdata.validators;

import org.com.sportsdata.exceptions.MatchNotFoundException;
import org.com.sportsdata.exceptions.ScoreException;
import org.com.sportsdata.exceptions.TeamNameException;
import org.com.sportsdata.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class MatchValidatorTest {
    Clock fixedClock = Clock.fixed(Instant.parse("2024-01-30T12:00:00Z"), ZoneId.of("UTC"));
    Map<String, Match> mock = new ConcurrentHashMap<>();
    @BeforeEach
    void setUp() {
        mock.put("TeamA-TeamB", new Match("Team A", "Team B", fixedClock));
    }


    @ParameterizedTest
    @CsvSource({
            "Team A, Team A, Team names cannot be the same",
            " , Team B, Team names cannot be blank",
            "Team A, , Team names cannot be blank",
            "Team A, Team B, One of the teams is already in a match"
    })
    void shouldValidateTeamNames(String homeTeam, String awayTeam, String expectedMessage) {
        TeamNameException exception = assertThrows(TeamNameException.class, () -> {
            MatchValidator.validateTeams(mock.values().stream().toList(), homeTeam, awayTeam);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

   @ParameterizedTest
    @CsvSource({
            "-1, 2, Scores cannot be negative",
            "2, -1, Scores cannot be negative"
    })
    void validateScores(int homeScore, int awayScore, String expectedMessage) {
       ScoreException exception = assertThrows(ScoreException.class, () -> {
           MatchValidator.validateScores(homeScore, awayScore);
       });
       assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validateMatchExists() {
        MatchNotFoundException exception = assertThrows(MatchNotFoundException.class, () -> {
            MatchValidator.validateMatchExists(mock, "Team X", "Team Y");
        });
        assertEquals("Match cannot be found.", exception.getMessage());
    }
}