package org.com.sportsdata.util;

public class MatchFinder {
    public static String getMatchKey(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase() + "-" + awayTeam.toLowerCase();
    }
}
