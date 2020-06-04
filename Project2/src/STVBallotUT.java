import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class STVBallotUT
{
  private Candidate candidate1 = new Candidate(0, "A");
  private Candidate candidate2 = new Candidate(1, "B");
  private Candidate[] c = {candidate1, candidate2};
  private int[] myVotes = {2, 1};
  private STVBallot b = new STVBallot(myVotes);

  @Test
  public void getCandidatesTest()
  {
    STVBallot.setCandidates(c);
    assertEquals(STVBallot.getCandidates()[0], candidate1);
    assertEquals(STVBallot.getCandidates()[1], candidate2);
  }

  @Test
  public void setCandidatesTest()
  {
    STVBallot.setCandidates(c);
    assertEquals(STVBallot.getCandidates(), c);
  }

  @Test
  public void getVotesSizeTest()
  {
    assertEquals(b.getVoteSize(), 2);
  }

  @Test
  public void getBallotIDTest()
  {
    b.setBallotID(10);
    assertEquals(b.getBallotID(), 10);
  }

  @Test
  public void setBallotIDTest()
  {
    b.setBallotID(2);
    assertEquals(b.getBallotID(), 2);

    b.setBallotID(200);
    assertEquals(b.getBallotID(), 200);
  }

  @Test
  public void getVotesTest() {
    assertTrue(Arrays.equals(new int[]{2, 1}, b.getVotes()));
  }

  @Test
  public void toStringTest()
  {
    assertEquals("Ballot #0: [2, 1]", b.toString());
  }

  @Test
  public void getChoiceTest()
  {
    STVBallot.setCandidates(c);
    assertEquals(b.getChoice(), candidate2);
  }

  @Test
  public void getChoiceTest2()
  {
    int[] myVotes2 = {1, 2};
    STVBallot b2 = new STVBallot(myVotes2);
    STVBallot.setCandidates(c);
    assertEquals(b2.getChoice(), candidate1);
  }

  @Test
  public void getChoiceTest3()
  {
    int[] myVotes3 = {1, 2};
    STVBallot b3 = new STVBallot(myVotes3);
    STVBallot.setCandidates(c);
    b3.incrementCurrNum();
    assertEquals(b3.getChoice(), candidate2);
  }

  @Test
  public void incrementCurrNumTest()
  {
    b.incrementCurrNum();
    STVBallot.setCandidates(c);
    assertEquals(b.getChoice(), candidate1);
  }

  @Test
  public void getCurrNumTest()
  {
    assertEquals(b.getCurrNum(), 1);
  }

  @Test
  public void equalsTest()
  {
    assertEquals(b, b);
    int[] myVotes2 = {1, 2};
    STVBallot b2 = new STVBallot(myVotes2);
    assertNotEquals(b, b2);
  }
}
