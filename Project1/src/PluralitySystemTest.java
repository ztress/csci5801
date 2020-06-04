import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class PluralitySystemTest {

    private static PluralityElection pElection1;
    private static PluralityElection pElection2;
    // private static PluralityElection pElection3;

    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners1;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners21;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners22;

    private static HashMap<Candidate, ArrayList<Ballot>> expectedLosers1;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedLosers21;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedLosers22;

    @BeforeAll
    public static void setUp() {
        pElection1 = new PluralityElection(2, "testing/samplePluralitySystemBallot1.csv");
        pElection1 = new PluralityElection(2, "testing/samplePluralitySystemBallot2.csv");
        // pElection1 = new PluralityElection(2, "testing/samplePluralitySystemBallot2.csv");
        
        pElection1.runElection();
        // pElection2.runElection();

        expectedWinners1 = new HashMap<>();
        Candidate a1 = new Candidate(0, "A");
        expectedWinners1.put(a1, new ArrayList<>());
        expectedWinners1.get(a1).add(new PluralityBallot(new int[]{1,0,0}));
        expectedWinners1.get(a1).add(new PluralityBallot(new int[]{1,0,0}));
        expectedWinners1.get(a1).add(new PluralityBallot(new int[]{1,0,0}));
        expectedWinners1.get(a1).add(new PluralityBallot(new int[]{1,0,0}));
        expectedWinners1.get(a1).add(new PluralityBallot(new int[]{1,0,0}));
        expectedWinners1.get(a1).add(new PluralityBallot(new int[]{1,0,0}));
        Candidate b1 = new Candidate(1, "B");
        expectedWinners1.put(b1, new ArrayList<>());
        expectedWinners1.get(b1).add(new PluralityBallot(new int[]{0,1,0}));
        expectedWinners1.get(b1).add(new PluralityBallot(new int[]{0,1,0}));
        expectedWinners1.get(b1).add(new PluralityBallot(new int[]{0,1,0}));
        expectedWinners1.get(b1).add(new PluralityBallot(new int[]{0,1,0}));
        Candidate c1 = new Candidate(2, "C");
        expectedLosers1 = new HashMap<>();
        expectedLosers1.put(c1, new ArrayList<>());
        expectedLosers1.get(c1).add(new PluralityBallot(new int[]{0,0,1}));

        // Possibility one for the tie election: A and B win C and D lose
        expectedWinners21 = new HashMap<>();
        Candidate a2 = new Candidate(0, "A");
        expectedWinners21.put(a2, new ArrayList<>());
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners21.get(a2).add(new PluralityBallot(new int[]{1,0,0,0}));
        
        Candidate b2 = new Candidate(1, "B");
        expectedWinners21.put(b2, new ArrayList<>());
        expectedWinners21.get(b2).add(new PluralityBallot(new int[]{0,1,0,0}));
        
        expectedLosers21 = new HashMap<>();
        Candidate c2 = new Candidate(2, "C");
        expectedLosers21.put(c2, new ArrayList<>());
        expectedLosers21.get(c2).add(new PluralityBallot(new int[]{0,0,1,0}));
        
        Candidate d2 = new Candidate(3, "D");
        expectedLosers21.put(d2, new ArrayList<>());


        // Possibility two for the tie election: A and C win B and D lose
        expectedWinners22 = new HashMap<>();
        Candidate a = new Candidate(0, "A");
        expectedWinners22.put(a, new ArrayList<>());
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));
        expectedWinners22.get(a).add(new PluralityBallot(new int[]{1,0,0,0}));

        Candidate c = new Candidate(2, "C");
        expectedWinners22.put(c, new ArrayList<>());
        expectedWinners22.get(c).add(new PluralityBallot(new int[]{0,0,1,0}));

        expectedLosers22 = new HashMap<>();
        Candidate b = new Candidate(2, "B");
        expectedLosers22.put(b, new ArrayList<>());
        expectedLosers22.get(b).add(new PluralityBallot(new int[]{0,0,1,0}));
        
        Candidate d = new Candidate(3, "D");
        expectedLosers22.put(d, new ArrayList<>());


        pElection1 = new PluralityElection(2, "testing/samplePluralitySystemBallot1.csv");
        pElection2 = new PluralityElection(2, "testing/samplePluralitySystemBallot2.csv");
        pElection1.runElection();
        pElection2.runElection();
    }

    // gets null pointer exception sometimes
    @Test
    public void testPluralityNoTieWinners() {
        assertEquals(expectedWinners1, pElection1.getElected());
    }

    // gets null pointer exception sometimes
    @Test
    public void testPluralityNoTieLosers() {
        assertEquals(expectedLosers1, pElection1.getNonElected());
    }

    // gets null pointer exception sometimes
    @Test
    public void testPluralityTieWinners() {
        assertTrue(pElection2.getElected().equals(expectedWinners21) ||
                pElection2.getElected().equals(expectedWinners22));
    }

    // gets null pointer exception sometimes
    @Test
    public void testPluralityTieLosers() {
        assertTrue(pElection2.getNonElected().equals(expectedLosers21) ||
                   pElection2.getNonElected().equals(expectedLosers22));
    }
}
