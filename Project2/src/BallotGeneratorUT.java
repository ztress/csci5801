import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

public class BallotGeneratorUT
{
  private static int[] v1 = {2, 1, 3};
  private static int[] v2 = {1, 2, 3};
  private static int[] v3 = {3, 2, 1};
  private static int[] v4 = {2, 3, 1};
  private static int[] v5 = {1, 3, 2};
  private static int[] v6 = {3, 1, 2};
  private static int[] votes1 = new int[] {1,0,2,0,3,4,5};
  private static int[] votes2 = new int[] {0,0,2,0,1,0,3};
  private static int[] votes3 = new int[] {1,0,0,0,2,0,0};
  private static int[] votes4 = new int[] {1,2,3,4,5,0,0};
  private static int[] votes5 = new int[] {1,2,0,0,0,0,3};
  private static int[] votes6 = new int[] {2,3,1,4,0,0,0};
  private static STVBallot b1;
  private static STVBallot b2;
  private static STVBallot b3;
  private static STVBallot b4;
  private static STVBallot b5;
  private static STVBallot b6;
  private static STVBallot testBallot1 = new STVBallot(votes1);
  private static STVBallot testBallot2 = new STVBallot(votes2);
  private static STVBallot testBallot3 = new STVBallot(votes3);
  private static STVBallot testBallot4 = new STVBallot(votes4);
  private static STVBallot testBallot5 = new STVBallot(votes5);
  private static STVBallot testBallot6 = new STVBallot(votes6);
  private static BallotGenerator STVbg;
  private static BallotGenerator Pluralitybg;
  private static BallotGenerator STVinvalidator;
  private static Candidate candidate1;
  private static Candidate candidate2;
  private static Candidate candidate3;
  private static ArrayList<Ballot> testBallots;
  private static ArrayList<Ballot> newBallots;
  private static HashMap<Candidate, ArrayList<Ballot>> candidates;
  private static ArrayList<STVBallot> invalTestBallots;
  private static ArrayList<STVBallot> invalTestBallots1;
  private static ArrayList<STVBallot> valTestBallots;
  private static BallotGenerator STVNoShuffle;

  @BeforeAll
  public static void setUp(){
    STVbg = new BallotGenerator("testing/sampleBallotGeneratorUT.csv", true, "STV" );
    STVNoShuffle = new BallotGenerator("testing/sampleBallotGeneratorUT.csv", false, "STV" );
    STVinvalidator = new BallotGenerator("string", false, "STV" );
    Pluralitybg = new BallotGenerator("testing/sampleBallotGeneratorPluralityUT.csv", false, "Plurality");
    candidate1 = new Candidate(0, "Jess");
    candidate2 = new Candidate(1, "Isabel");
    candidate3 = new Candidate(2, "Zac");
    testBallots = new ArrayList<Ballot>();
    b1 = new STVBallot(v1);
    testBallots.add(b1);
    b2 = new STVBallot(v2);
    b2.setBallotID(1);
    testBallots.add(b2);
    b3= new STVBallot(v3);
    b3.setBallotID(2);
    testBallots.add(b3);
    b4= new STVBallot(v4);
    b4.setBallotID(3);
    testBallots.add(b4);
    b5= new STVBallot(v5);
    b5.setBallotID(4);
    testBallots.add(b5);
    b6= new STVBallot(v6);
    b6.setBallotID(5);
    testBallots.add(b6);
    candidates = new HashMap<Candidate, ArrayList<Ballot>>();
    invalTestBallots = new ArrayList<STVBallot>();
    invalTestBallots1 = new ArrayList<STVBallot>();
    valTestBallots = new ArrayList<STVBallot>();
    valTestBallots.add(testBallot1);
    valTestBallots.add(testBallot4);
    valTestBallots.add(testBallot6);
    invalTestBallots.add(testBallot1);
    invalTestBallots.add(testBallot2);
    invalTestBallots.add(testBallot3);
    invalTestBallots.add(testBallot4);
    invalTestBallots.add(testBallot5);
    invalTestBallots.add(testBallot6);
    invalTestBallots1.add(testBallot1);
    invalTestBallots1.add(testBallot2);
    invalTestBallots1.add(testBallot3);
    invalTestBallots1.add(testBallot4);
    invalTestBallots1.add(testBallot5);
    invalTestBallots1.add(testBallot6);

  }

  @Test
  public void createPluralityBallotsTest(){
    // no shuffle here so no issue with reshuffling to same location.
    newBallots = Pluralitybg.createBallots(candidates);
    assertEquals(newBallots.get(0).getChoice().getIndex(), candidate2.getIndex());
    assertEquals(newBallots.get(1).getChoice().getIndex(), candidate1.getIndex());
    assertEquals(newBallots.get(2).getChoice().getIndex(), candidate3.getIndex());

  }

  @Test
  public void createBallotsTest2(){
    // This test checks if it still works even though it is given a HashMap that is not empty making the
    // program skip the first loop and go through the next loop, but still accounts for the first candidate that was
    // already added.
    ArrayList<Ballot> t2 = new ArrayList<Ballot>();
    int[] votes = {0,1,0};
    PluralityBallot p = new PluralityBallot(votes);
    t2.add(p);
    testBallots = Pluralitybg.createBallots(candidates);
    candidates.put(candidate2, t2);
    newBallots = Pluralitybg.createBallots(candidates);
    assertEquals(newBallots.get(0), testBallots.get(0));

  }

  @Test
  public void createBallotsTest3(){
    // This test ensures that createBallots method will also work if the Hashmap already has candidates and Ballot arraylists
    // inside of it for the STV ballot Generator.
    ArrayList<Ballot> t3 = new ArrayList<Ballot>();
    STVBallot s = new STVBallot(v1);
    t3.add(s);
    testBallots = STVbg.createBallots(candidates);
    candidates.put(candidate1, t3);
    newBallots = STVbg.createBallots(candidates);
    assertNotEquals(newBallots.get(0), testBallots.get(0));
  }

  @Test
  public void createSTVBallotsTest(){
    // This test checks the STV creation of ballots and checks the ballots are also shuffled properly.
    // with STV, shuffle will always occur, so sometimes ballots can be shuffled to the original location which is
    // sometimes possible, so the test may fail due to the ballot being reshuffled to the original location in the array.

    BallotGenerator bg1 = new BallotGenerator("testing/sampleSTVBallot.csv", false, "STV");
    BallotGenerator bg2 = new BallotGenerator("testing/sampleSTVBallot.csv", false, "STV");

    assertEquals(bg1.createBallots(candidates), bg2.createBallots(candidates));

    BallotGenerator bg3 = new BallotGenerator("testing/sampleSTVBallot.csv", true, "STV");

    assertNotEquals(bg3.createBallots(candidates), bg2.createBallots(candidates));

    BallotGenerator bg4 = new BallotGenerator("testing/sampleSTVBallot.csv", true, "STV");

    assertNotEquals(bg3.createBallots(candidates), bg4.createBallots(candidates));
  }


  @Test
  public void invalidBallotsReportTest1(){
    // This test checks that the invalidBallotsReport method works as it is intended. It must correctly display percentages
    // and ballot information to the screen and information regarding each individual ballot.

    STVinvalidator.invalidator(invalTestBallots1);

    // Must test file output in the invalid file created.
    ArrayList<String> expectedLines = new ArrayList<String>();
    expectedLines.add("Number of Invalidated Ballots: 3");
    expectedLines.add("Percentage of Ballots that were Invalidated: 50%");
    expectedLines.add("Ratio of Valid Ballots to Invalidated Ballots: 1:1");
    expectedLines.add("List of Invalidated Ballots:");
    expectedLines.add("[0, 0, 2, 0, 1, 0, 3]");
    expectedLines.add("[1, 0, 0, 0, 2, 0, 0]");
    expectedLines.add("[1, 2, 0, 0, 0, 0, 3]");

    try {
      BufferedReader br = new BufferedReader(new FileReader("Invalid-Ballots-Report"));
      String next;
      Iterator<String> iter = expectedLines.iterator();

      while ((next = br.readLine()) != null && iter.hasNext()){
        assertEquals(iter.next(), next);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void invalidatorTest() {
    // This test checks that the invalidator method works. In an STV election all ballots must have at least half the
    // candidates ranked. The invalidator will check each ballot and remove any where fewer than half the candidates
    // are ranked

    ArrayList<STVBallot> expectedValid = new ArrayList<STVBallot>();
    expectedValid.add(testBallot1);
    expectedValid.add(testBallot4);
    expectedValid.add(testBallot6);

    STVinvalidator.invalidator(invalTestBallots);

    assertEquals(expectedValid, invalTestBallots);
  }


  @Test
  public void invalidBallotsReportTest2(){
    // This test checks that the invalidBallotsReport method works if the invalidballots arraylist given to the invalidBallotReport
    // function can still generate correct output when it contains no invalidated ballots.
    STVinvalidator.invalidator(valTestBallots);

    // Must test file output in the invalid file created.
    ArrayList<String> expectedLines = new ArrayList<String>();
    expectedLines.add("Number of Invalidated Ballots: 0");
    expectedLines.add("Percentage of Ballots that were Invalidated: 0%");
    expectedLines.add("Ratio of Valid Ballots to Invalidated Ballots: 1:1");
    expectedLines.add("No invalidated ballots to display.");

    try {
      BufferedReader br = new BufferedReader(new FileReader("Invalid-Ballots-Report"));
      String next;
      Iterator<String> iter = expectedLines.iterator();

      while ((next = br.readLine()) != null && iter.hasNext()){
        assertEquals(iter.next(), next);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void shuffleTest(){
    // This test checks that the shuffle method properly shuffles the ballots so that they are not in the same order
    // as being read in.
    // with STV, shuffle will always occur, so sometimes ballots can be shuffled to the original location which is
    // sometimes possible, so the test may fail due to the ballot being reshuffled to the original location in the array.
    STVbg.shuffle(testBallots, testBallots.size());
    assertFalse(testBallots.get(0).equals(b1) && testBallots.get(1).equals(b2) &&
            testBallots.get(2).equals(b3) && testBallots.get(3).equals(b4) &&
            testBallots.get(5).equals(b6));
  }
}
