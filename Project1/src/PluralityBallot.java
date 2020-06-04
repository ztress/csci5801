/**
 * PluralityBallot class
 *
 * This class represents a ballot in an Plurality Election and implements the Ballot interface.
 *
 * @author Jessica Moore
 */

import java.util.Arrays;

public class PluralityBallot implements Ballot {
    private static Candidate[] candidates;
    private int[] votes;

    /**
     * Constructor for the Plurality Ballot class
     *
     * @param inVotes is an array that stores who the ballot
     *                is voting for. This is represented by a 1
     */
    public PluralityBallot(int[] inVotes) {
        votes = inVotes;
    }

    /**
     * Retrieves the array of Candidate objects for the PluralityBallot class.
     * @return candidates array
     */
    public static Candidate[] getCandidates() {
      return candidates;
    }

    /**
     * Sets the number of candidates for the PluralityBallot class.
     * @param inCands Array of Candidate objects
     */
    public static void setCandidates(Candidate[] inCands) {
        candidates = inCands;
    }

    /**
     * Gives the Candidate the ballot is casting a vote for.
     *
     * @return the Candidate object that the ballot is voting for
     */
    public Candidate getChoice() {
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] == 1) {
                return candidates[i];
            }
        }
        return null;
    }

    /**
     * Gives the ballot represented as a string.
     * @return string representation
     */
    @Override
    public String toString() { return "Ballot: " + Arrays.toString(votes); }

    /**
     * Checks if given object is equal.
     * @param o Object to compare to
     * @return boolean if candidates are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluralityBallot)) {
            return false;
        }
        PluralityBallot c = (PluralityBallot) o;
        return this.toString().equals(c.toString());
    }
}
