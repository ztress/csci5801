import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class STVSystemTest {

    private static STVElection electionSOn1;
    private static STVElection electionSOn2;
    private static STVElection electionSOn3;


    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners1;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners2;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners3;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners4;

    private static ArrayList<Candidate> expectedLosers1;
    private static ArrayList<Candidate> expectedLosers2;
    private static ArrayList<Candidate> expectedLosers3;
    private static ArrayList<Candidate> expectedLosers4;


    private static STVElection electionSOff1;
    private static STVElection electionSOff2;
    private static STVElection electionSOff3;
    private static STVElection electionSOff4;


    @BeforeAll
    public static void setUp() {
        electionSOff1 = new STVElection(2, "testing/sampleSTVSystemBallot1.csv", false);
        electionSOff2 = new STVElection(2, "testing/sampleSTVSystemBallot2.csv", false);
        electionSOff3 = new STVElection(3, "testing/sampleSTVSystemBallot3.csv", false);
        electionSOff4 = new STVElection(2, "testing/sampleSTVSystemBallot4.csv", false);
        electionSOff1.runElection();
        electionSOff2.runElection();
        electionSOff3.runElection();
        electionSOff4.runElection();


        expectedWinners1 = new HashMap<>();
        Candidate c1 = new Candidate(2, "c");
        expectedWinners1.put(c1, new ArrayList<>());
        expectedWinners1.get(c1).add(new STVBallot(new int[]{3,2,1}));
        expectedWinners1.get(c1).add(new STVBallot(new int[]{2,0,1}));
        expectedWinners1.get(c1).add(new STVBallot(new int[]{2,3,1}));
        expectedWinners1.get(c1).add(new STVBallot(new int[]{2,0,1}));
        expectedWinners1.get(c1).add(new STVBallot(new int[]{3,2,1}));
        Candidate a1 = new Candidate(0, "a");
        expectedWinners1.put(a1, new ArrayList<>());
        expectedWinners1.get(a1).add(new STVBallot(new int[]{1,2,3}));
        expectedWinners1.get(a1).add(new STVBallot(new int[]{1,0,2}));
        expectedWinners1.get(a1).add(new STVBallot(new int[]{1,3,2}));
        expectedWinners1.get(a1).add(new STVBallot(new int[]{1,2,0}));
        expectedWinners1.get(a1).add(new STVBallot(new int[]{1,0,2}));
        expectedLosers1 = new ArrayList<>();
        expectedLosers1.add(new Candidate(1, "b"));

        expectedWinners2 = new HashMap<>();
        Candidate d2 = new Candidate(3, "d");
        expectedWinners2.put(d2, new ArrayList<>());
        expectedWinners2.get(d2).add(new STVBallot(new int[]{0,2,0,1}));
        expectedWinners2.get(d2).add(new STVBallot(new int[]{0,2,0,1}));
        expectedWinners2.get(d2).add(new STVBallot(new int[]{4,3,2,1}));
        expectedWinners2.get(d2).add(new STVBallot(new int[]{3,2,4,1}));
        expectedWinners2.get(d2).add(new STVBallot(new int[]{3,2,0,1}));
        Candidate b2 = new Candidate(1, "b");
        expectedWinners2.put(b2, new ArrayList<>());
        expectedWinners2.get(b2).add(new STVBallot(new int[]{2,1,3,0}));
        expectedWinners2.get(b2).add(new STVBallot(new int[]{2,1,3,0}));
        expectedWinners2.get(b2).add(new STVBallot(new int[]{0,1,2,0}));
        expectedWinners2.get(b2).add(new STVBallot(new int[]{1,2,0,3}));
        expectedWinners2.get(b2).add(new STVBallot(new int[]{1,2,0,0}));
        expectedLosers2 = new ArrayList<>();
        expectedLosers2.add(new Candidate(0, "a"));
        expectedLosers2.add(new Candidate(2, "c"));

        expectedWinners3 = new HashMap<>();
        Candidate c3 = new Candidate(2,"c");
        expectedWinners3.put(c3, new ArrayList<>());
        expectedWinners3.get(c3).add(new STVBallot(new int[]{3,0,1,0,0,2,4}));
        expectedWinners3.get(c3).add(new STVBallot(new int[]{2,4,1,3,0,0,0}));
        Candidate d3 = new Candidate(3,"d");
        expectedWinners3.put(d3, new ArrayList<>());
        expectedWinners3.get(d3).add(new STVBallot(new int[]{3,2,0,1,0,4,0}));
        expectedWinners3.get(d3).add(new STVBallot(new int[]{3,2,0,1,4,0,5}));
        Candidate b3 = new Candidate(5,"f");
        expectedWinners3.put(b3, new ArrayList<>());
        expectedWinners3.get(b3).add(new STVBallot(new int[]{4,3,2,0,0,1,0}));
        expectedWinners3.get(b3).add(new STVBallot(new int[]{0,0,2,0,1,3,4}));
        expectedLosers3 = new ArrayList<>();
        expectedLosers3.add(new Candidate(0,"a"));
        expectedLosers3.add(new Candidate(1,"b"));
        expectedLosers3.add(new Candidate(6,"g"));
        expectedLosers3.add(new Candidate(4,"e"));


        expectedWinners4 = new HashMap<>();
        Candidate i1 = new Candidate(0, "a");
        expectedWinners4.put(i1, new ArrayList<>());
        expectedWinners4.get(i1).add(new STVBallot(new int[]{1,2,0}));
        Candidate i2 = new Candidate(1, "b");
        expectedWinners4.put(i2, new ArrayList<>());
        expectedWinners4.get(i2).add(new STVBallot(new int[]{2,1,0}));
        expectedLosers4 = new ArrayList<>();
        expectedLosers4.add(new Candidate(2, "c"));

        electionSOn1 = new STVElection(2, "testing/sampleSTVSystemBallot1.csv", true);
        electionSOn2 = new STVElection(2, "testing/sampleSTVSystemBallot2.csv", true);
        electionSOn3 = new STVElection(3, "testing/sampleSTVSystemBallot3.csv", true);
        electionSOn1.runElection();
        electionSOn2.runElection();
        electionSOn3.runElection();
    }

    @Test
    public void testShuffleOff1() {
        assertEquals(expectedWinners1, electionSOff1.getElected());
        assertEquals(expectedLosers1, electionSOff1.getLosers());
        assertEquals(5, electionSOff1.getDroop());
    }

    @Test
    public void testShuffleOff2() {
        assertEquals(expectedWinners2, electionSOff2.getElected());
        assertEquals(expectedLosers2, electionSOff2.getLosers());
        assertEquals(5, electionSOff2.getDroop());
    }

    @Test
    public void testShuffleOff3() {
        assertEquals(expectedWinners3, electionSOff3.getElected());
        assertEquals(expectedLosers3, electionSOff3.getLosers());
        assertEquals(2, electionSOff3.getDroop());
    }

    @Test
    public void testShuffleOff4() {
        assertEquals(expectedWinners4, electionSOff4.getElected());
        assertEquals(expectedLosers4, electionSOff4.getLosers());
    }

    @Test
    public void testShuffleOn1() {
        assertNotEquals(expectedWinners1, electionSOn1.getElected());
        assertEquals(5, electionSOn1.getDroop());
    }

    @Test
    public void testShuffleOn2() {
        assertNotEquals(expectedWinners2, electionSOn2.getElected());
        assertEquals(5, electionSOn2.getDroop());
    }

    @Test
    public void testShuffleOn3() {
        assertNotEquals(expectedWinners3, electionSOn3.getElected());
        assertEquals(2, electionSOn3.getDroop());
    }
}
