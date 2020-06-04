/**
 * Ballot interface
 *
 * The Ballot interface represents a generic ballot. Both STV Ballot and Plurality Ballot implement this interface.
 *
 * @author Jessica Moore
 */


interface Ballot {
    Candidate getChoice();
    String toString();
    boolean equals(Object o);
}
