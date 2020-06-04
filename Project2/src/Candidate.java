/**
*    Candidate class
 *
*    The original class defining a Candidate object.
 *
*    @author Isabel Tomb
 */

public class Candidate {
    private int index;
    private String name;
    private int numVotes;

    /**
    * Constructor creates a Candidate object with an index, name
    * and starting number of votes.
    * @param index index of the candidate
    * @param name name of the candidate
     */
    public Candidate(int index, String name)
    {
        this.index = index;
        this.name = name;
        this.numVotes = 0;
    }

    /**
    * Retrieves the number of votes a candidate has.
    * @return number of votes
    */
    public int getVotes()
    {
        return this.numVotes;
    }

    /**
    * Retrieves the name of a candidate.
    * @return candidate name
    */
    public String getName() { return this.name; }

    /**
     * Retrieves the index of a candidate.
     * @return candidate index
     */
    public int getIndex() { return this.index; }

    /**
    * Increments the number of votes a candidate has by one.
    *
    */
    public void addBallot()
    {
        this.numVotes++;
    }

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
        if (!(o instanceof Candidate)) {
            return false;
        }
        Candidate c = (Candidate) o;
        return (c.getName().equals(this.name)) && (c.getIndex() == this.index);
    }
    /**
     * Gives a hashcode for the object.
     * @return index as hashcode
     */
    @Override
    public int hashCode() {
        return index;
    }
}
