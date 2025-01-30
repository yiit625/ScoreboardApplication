package org.com.sportsdata.comparator;

import org.com.sportsdata.model.Match;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {
    @Override
    public int compare(Match m1, Match m2) {
        int scoreDiff = Integer.compare(m2.getTotalScore(), m1.getTotalScore());
        return (scoreDiff != 0) ? scoreDiff : Long.compare(m2.getStartTime(), m1.getStartTime());
    }
}
