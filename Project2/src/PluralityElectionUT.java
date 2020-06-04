import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PluralityElectionUT {

    private static PluralityElection pElection;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedWinners;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedLosers;
    private static HashMap<Candidate, ArrayList<Ballot>> expectedInitial;

    private static Candidate jess;
    private static int jessbal[];
    private static Candidate isabel;
    private static int isabelbal[];
    private static Candidate zac;
    private static int zacbal[];
    private static Candidate ian;
    private static int ianbal[];

    @BeforeAll
    public static void setUp() {
        jess = new Candidate(0, "Jess");
        isabel = new Candidate(1, "Isabel");
        zac = new Candidate(2, "Zac");
        ian = new Candidate(3, "Ian");
        expectedInitial = new HashMap<>();
        expectedInitial.put(jess, new ArrayList<>());
        expectedInitial.put(isabel, new ArrayList<>());
        expectedInitial.put(zac, new ArrayList<>());
        expectedInitial.put(ian, new ArrayList<>());

        expectedWinners = new HashMap<>();
        expectedWinners.put(jess, new ArrayList<>());
        expectedWinners.put(isabel, new ArrayList<>());

        expectedLosers = new HashMap<>();
        expectedLosers.put(zac, new ArrayList<>());
        expectedLosers.put(ian, new ArrayList<>());

        jessbal = new int[]{1, 0, 0, 0};
        for (int i = 0; i < 3; i++) {
            expectedWinners.get(jess).add(new PluralityBallot(jessbal));
            expectedInitial.get(jess).add(new PluralityBallot(jessbal));
            jess.addBallot();
        }
        isabelbal = new int[]{0, 1, 0, 0};
        for (int i = 0; i < 3; i++) {
            expectedWinners.get(isabel).add(new PluralityBallot(isabelbal));
            expectedInitial.get(isabel).add(new PluralityBallot(isabelbal));
            isabel.addBallot();
        }
        zacbal = new int[]{0, 0, 1, 0};
        expectedLosers.get(zac).add(new PluralityBallot(zacbal));
        expectedInitial.get(zac).add(new PluralityBallot(zacbal));
        zac.addBallot();
        ianbal = new int[]{0, 0, 0, 1};
        expectedLosers.get(ian).add(new PluralityBallot(ianbal));
        expectedInitial.get(ian).add(new PluralityBallot(ianbal));
        ian.addBallot();

        pElection = new PluralityElection(2, "testing/samplePluralityBallot.csv");
        pElection.runElection();
    }

    @Test
    public void testDisplayStats() {
        String res = null;
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream capture = new PrintStream(os);
            // everything printed to System.out will get captured
            System.setOut(capture);
            pElection.displayStats();
            capture.flush();
            res = os.toString();

            String expectedString = "Election Type: Plurality\r\n" +
                    "Seats: 2\r\n" +
                    "Number of Candidates: 4\r\n" +
                    "The order in which the candidates are listed, is the order that they were elected.\r\n" +
                    "Elected:\r\n" +
                    "Jess | % Votes: 37%\r\n" +
                    "Isabel | % Votes: 37%\r\n" +
                    "Non-Elected:\r\n" +
                    "Zac, Ian\r\n" +
                    "Audit File Name: Election-Audit-File\r\n" +
                    "Audit File Location: src/Election-Audit-File";
            assertEquals(expectedString, res);
        } finally {
            // reset output to stdout
            System.setOut(originalOut);
        }
    }

    @Test
    public void testGetNumSeats() {
        assertEquals(2, pElection.getNumSeats());
    }

    @Test
    public void testGetNumCandidates() {
        assertEquals(4, pElection.getNumCandidates());
    }

    @Test
    public void testGetBallots() {
        ArrayList<Ballot> expectedVotes = new ArrayList<Ballot>();
        expectedVotes.add(new PluralityBallot(jessbal));
        expectedVotes.add(new PluralityBallot(isabelbal));
        expectedVotes.add(new PluralityBallot(zacbal));
        expectedVotes.add(new PluralityBallot(ianbal));
        expectedVotes.add(new PluralityBallot(jessbal));
        expectedVotes.add(new PluralityBallot(isabelbal));
        expectedVotes.add(new PluralityBallot(jessbal));
        expectedVotes.add(new PluralityBallot(isabelbal));

        for (int i = 0; i < expectedVotes.size(); i++) {
            assertEquals(expectedVotes.get(i), pElection.getBallots().get(i));
        }
    }

    @Test
    public void testGenerateBallots() {
        ArrayList<Ballot> expectedVotes = new ArrayList<Ballot>();
        expectedVotes.add(new PluralityBallot(jessbal));
        expectedVotes.add(new PluralityBallot(isabelbal));
        expectedVotes.add(new PluralityBallot(zacbal));
        expectedVotes.add(new PluralityBallot(ianbal));
        expectedVotes.add(new PluralityBallot(jessbal));
        expectedVotes.add(new PluralityBallot(isabelbal));
        expectedVotes.add(new PluralityBallot(jessbal));
        expectedVotes.add(new PluralityBallot(isabelbal));

        PluralityElection pElectionNoSeat = new PluralityElection(0, "testing/samplePluralityBallot.csv");
        pElectionNoSeat.generateBallots();
        assertEquals(expectedVotes, pElectionNoSeat.getBallots());
    }

    // tests if nonelected is properly initialized and filled in runElection
    // tests runElection() lines 86-93
    @Test
    public void testFillInitialNonElectedArray() {
        PluralityElection pElectionNoSeat = new PluralityElection(0, "testing/samplePluralityBallot.csv");
        pElectionNoSeat.runElection();

        assertEquals(expectedInitial, pElectionNoSeat.getNonElected());
    }

    // tests if most popular candidate is properly identified and added to elected
    // tests runElection() lines iterator loop lines 101-111
    @Test
    public void testGetElectedOneCandidate() {
        PluralityElection pElectionOneSeat = new PluralityElection(1, "testing/samplePluralityBallot.csv");
        pElectionOneSeat.runElection();
        HashMap<Candidate, ArrayList<Ballot>> possibleWinner = new HashMap();
        ArrayList<Ballot> expectedVotes = new ArrayList<>();
        expectedVotes.add(new PluralityBallot(isabelbal));
        expectedVotes.add(new PluralityBallot(isabelbal));
        expectedVotes.add(new PluralityBallot(isabelbal));
        possibleWinner.put(isabel, expectedVotes);
        HashMap<Candidate, ArrayList<Ballot>> possibleWinner1 = new HashMap();
        ArrayList<Ballot> expectedVotes1 = new ArrayList<>();
        expectedVotes1.add(new PluralityBallot(jessbal));
        expectedVotes1.add(new PluralityBallot(jessbal));
        expectedVotes1.add(new PluralityBallot(jessbal));
        possibleWinner1.put(jess, expectedVotes1);

        HashMap<Candidate, ArrayList<Ballot>> actual = pElectionOneSeat.getElected();
        assertTrue((possibleWinner.equals(actual) || possibleWinner1.equals(actual)));
    }

    // tests if getElected retrieves the HashMap
    // also tests if multiple popular candidates are identified and added to elected
    // tests runElection() while loop lines 95-113
    @Test
    public void testGetElectedMultipleCandidates() {
        assertEquals(expectedWinners, pElection.getElected());
    }

    // tests if getNonElected retrieves the ArrayList
    // also tests if expected non-winners are left in the nonelected array properly
    // tests runElection() line 112
    @Test
    public void testGetNonElected() {
        assertEquals(expectedLosers, pElection.getNonElected());
    }
}
