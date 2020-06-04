import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PluralityBallotUT
{
  private Candidate candidate1 = new Candidate(0, "A");
  private Candidate candidate2 = new Candidate(1, "B");
  private Candidate[] c = {candidate1, candidate2};
  private int[] myVotes = {0, 1};
  private PluralityBallot b = new PluralityBallot(myVotes);

  @Test
  public void getCandidatesTest()
  {
    PluralityBallot.setCandidates(c);
    assertEquals(PluralityBallot.getCandidates()[0], candidate1);
    assertEquals(PluralityBallot.getCandidates()[1], candidate2);
  }

  @Test
  public void setCandidatesTest()
  {
    PluralityBallot.setCandidates(c);
    assertEquals(PluralityBallot.getCandidates(), c);
  }

  @Test
  public void toStringTest()
  {
    assertEquals("Ballot: [0, 1]", b.toString());
  }

  @Test
  public void getChoiceTest()
  {
    PluralityBallot.setCandidates(c);
    assertEquals(b.getChoice(), candidate2);
  }

  @Test
  public void getChoiceTest2()
  {
    int[] myVotes2 = {1, 0};
    PluralityBallot b2 = new PluralityBallot(myVotes2);
    PluralityBallot.setCandidates(c);
    assertEquals(b2.getChoice(), candidate1);
  }

  @Test
  public void getChoiceTest3()
  {
    int[] myVotes3 = {2, 1};
    PluralityBallot b3 = new PluralityBallot(myVotes3);
    PluralityBallot.setCandidates(c);
    assertEquals(b3.getChoice(), candidate2);
  }

  @Test
  public void equalsTest()
  {
    assertEquals(b, b);
    int[] myVotes2 = {1, 2};
    PluralityBallot b2 = new PluralityBallot(myVotes2);
    assertNotEquals(b, b2);
  }
}
