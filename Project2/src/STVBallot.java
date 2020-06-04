/**
 * STVBallot class
 *
 * This class represents a ballot in an STV Election and implements the Ballot interface.
 *
 * @author Jessica Moore
 */

import java.util.Arrays;

public class STVBallot implements Ballot {
    private static Candidate[] candidates;  // represents the order of candidates how they appear in the ballot

    private int currNum;  // represents which rank choice will be cast as the vote for this ballot
    private int ballotID;
    private int[] votes;    // The ranking of the candidates

    /**
     * Constructor for the Plurality Ballot class
     *
     * @param inVotes is an array that stores the order of candidates
     *                in which the ballot is voting for, where
     *                1 represents the first choice
     * currNum is also set to 1 and ballotID set to 0
     */
    public STVBallot(int[] inVotes) {
        votes = inVotes;
        currNum = 1;
        ballotID = 0;
    }

    /**
     * Retrieves the array of Candidate objects for the STVBallot class.
     * @return candidates array
     */
    public static Candidate[] getCandidates() {
      return candidates;
    }

    /**
     * Sets the number of candidates for the STVBallot class.
     * @param inCands Array of Candidate objects
     */
    public static void setCandidates(Candidate[] inCands) {
        candidates = inCands;
    }

    /**
     * Represents the number of candidates a ballot voted for
     * @return Returns the length of the votes array
     */
    public int getVoteSize()
    {
        return votes.length;
    }

    /**
     * Retrieves the ballot ID.
     * @return ballot ID
     */
    public int getBallotID() {
        return ballotID;
    }

    /**
     * Sets the ballot ID.
     * @param inID desired ballot ID
     */
    public void setBallotID(int inID) {
        ballotID = inID;
    }

    /**
     * Increments currNum to change which candidate the ballot votes for.
     */
    public void incrementCurrNum() {
        currNum++;
    }

    /**
     * Retrieves currNum, which represents which rank the ballot currently represents
     * @return returns the currNum variable
     */
    public int getCurrNum()
    {
        return currNum;
    }

    /**
     * Retrieves votes, which represent the votes cast in the ballot
     * @return votes[]
     */
    public int[] getVotes() { return votes; }

    /**
     * Gives the Candidate the ballot is casting a vote for.
     *
     * @return the candidate object that is "currNum"th on the ballot
     */
    public Candidate getChoice() {
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] == currNum) {
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
    public String toString() { return "Ballot #" + ballotID + ": " + Arrays.toString(votes); }

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
        if (!(o instanceof STVBallot)) {
            return false;
        }
        STVBallot c = (STVBallot) o;
        return Arrays.equals(votes, c.getVotes());
    }
}
