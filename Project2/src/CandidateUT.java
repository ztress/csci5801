import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CandidateUT
{
  private Candidate candidate = new Candidate(0, "Ian");
  private Candidate candidate2 = new Candidate(1, "Zac");
  private Candidate candidate3 = new Candidate(0, "Ian");

  @Test
  public void getVotesTest()
  {
    assertEquals(candidate.getVotes(), 0);
    candidate.addBallot();
    assertEquals(candidate.getVotes(), 1);
  }

  @Test
  public void getNameTest()
  {
    assertEquals(candidate.getName(), "Ian");
  }

  @Test
  public void getIndexTest() { assertEquals(candidate.getIndex(), 0); }

  @Test
  public void addBallotTest()
  {
    assertEquals(candidate.getVotes(), 0);
    candidate.addBallot();
    candidate.addBallot();
    assertEquals(candidate.getVotes(), 2);
  }

  @Test
  public void equalsTest() {
    assertTrue(candidate.equals(candidate3));
    assertFalse(candidate.equals(candidate2));
  }
}
