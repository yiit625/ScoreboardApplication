package org.com.sportsdata.comparator;

import org.com.sportsdata.model.Match;

import java.util.Comparator;

/**
 * This class implements a Comparator for Match objects.
 * It sorts matches in descending order of total score,
 * with ties broken by the start time (earlier matches come first).
 */
public class MatchComparator implements Comparator<Match> {
    /**
     * Compares two Match objects based on their total score and start time.
     *
     * @param m1 The first Match object.
     * @param m2 The second Match object.
     * @return A negative integer if m2 has a higher total score,
     *         a positive integer if m1 has a higher total score,
     *         or zero if the total scores are equal and m2 started earlier.
     */
    @Override
    public int compare(Match m1, Match m2) {
        int scoreDiff = Integer.compare(m2.getTotalScore(), m1.getTotalScore());
        return (scoreDiff != 0) ? scoreDiff : Long.compare(m2.startTime(), m1.startTime());
    }
}
