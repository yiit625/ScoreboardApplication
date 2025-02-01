package org.com.sportsdata.util;

/**
 * This utility class provides methods for finding and identifying matches.
 */
public class MatchFinder {
    /**
     * Generates a unique key for a match based on the home and away team names.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @return A unique key string in the format "homeTeam-awayTeam" (lowercase).
     */
    public static String getMatchKey(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase() + "-" + awayTeam.toLowerCase();
    }
}
