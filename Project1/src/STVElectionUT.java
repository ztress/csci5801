import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.*;

import java.io.PrintStream;

public class STVElectionUT{


    private STVElection stvelection = new STVElection(10, "sampleSTVElection.csv", true);

    @Test
    public void STVElectionConstructorTest()
    {
        assertEquals(stvelection.getNumSeats(), 10);
        // no method to get file location
        assertEquals(stvelection.isShuffled(), true);
    }

    @Test
    public void getLosersTest()
    {
        ArrayList<Candidate> losersTest = new ArrayList<Candidate>();

        assertEquals(stvelection.getLosers(), losersTest);
    }

    @Test
    public void isShuffledTest()
    {
        assertTrue(stvelection.isShuffled());
    }

    @Test
    public void getNumSeatsTest()
    {
        assertEquals(stvelection.getNumSeats(), 10);
    }

    @Test
    public void getElectedTest()
    {
        HashMap<Candidate, ArrayList<Ballot>> electedTest = new HashMap<Candidate, ArrayList<Ballot>>();

        assertEquals(stvelection.getElected(), electedTest);
    }

    @Test
    public void getNonElectedTest()
    {
        HashMap<Candidate, ArrayList<Ballot>> nonElectedTest = new HashMap<Candidate, ArrayList<Ballot>>();

        assertEquals(stvelection.getNonElected(), nonElectedTest);
    }

    @Test
    public void getDroopTest()
    {
        assertEquals(stvelection.getDroop(), 0);
    }

    @Test
    public void getBallotsTest()
    {
        ArrayList<Ballot> votesTest = new ArrayList<Ballot>();

        assertEquals(stvelection.getBallots(), votesTest);
    }

    @Test
    public void generateBallotsTest()
    {
        STVElection stvelection2 = new STVElection(2, "testing/sampleSTVBallot2.csv", false);
        stvelection2.generateBallots();

        ArrayList<Ballot> votesTest = new ArrayList<Ballot>();

        int[] v1 = {1,2,3};
        int[] v2 = {2,1,3};
        int[] v3 = {1,3,2};
        int[] v4 = {1,3,2};
        int[] v5 = {3,2,1};
        int[] v6 = {1,3,2};
        int[] v7 = {1,2,3};

        STVBallot testballot1 = new STVBallot(v1);
        STVBallot testballot2 = new STVBallot(v2);
        STVBallot testballot3 = new STVBallot(v3);
        STVBallot testballot4 = new STVBallot(v4);
        STVBallot testballot5 = new STVBallot(v5);
        STVBallot testballot6 = new STVBallot(v6);
        STVBallot testballot7 = new STVBallot(v7);

        testballot1.setBallotID(0);
        testballot2.setBallotID(1);
        testballot3.setBallotID(2);
        testballot4.setBallotID(3);
        testballot5.setBallotID(4);
        testballot6.setBallotID(5);
        testballot7.setBallotID(6);

        votesTest.add(testballot1);
        votesTest.add(testballot2);
        votesTest.add(testballot3);
        votesTest.add(testballot4);
        votesTest.add(testballot5);
        votesTest.add(testballot6);
        votesTest.add(testballot7);

 
        assertEquals(votesTest, stvelection2.getBallots());
    }

    // all seats are filled after the first pass
    @Test
    public void runElectionFirstPassTest()
    {
        STVElection stvelection3 = new STVElection(2, "testing/sampleSTVBallot3.csv", false);

        Candidate testCand0 = new Candidate(0, "A");
        Candidate testCand1 = new Candidate(1, "B");
        Candidate testCand2 = new Candidate(2, "C");

        // the expected losers
        ArrayList<Candidate> expectedLosers = new ArrayList<Candidate>();
        expectedLosers.add(testCand2);

        // the expected winners
        ArrayList<Candidate> expectedWinners = new ArrayList<Candidate>();
        expectedWinners.add(testCand0);
        expectedWinners.add(testCand1);

        stvelection3.runElection();

        // get the hashmap of actual winners
        HashMap<Candidate, ArrayList<Ballot>> testElected = stvelection3.getElected();

        // put the winners in an array
        Iterator it = testElected.entrySet().iterator();
        int i = 0;
        ArrayList<Candidate> testWinners = new ArrayList<Candidate>();
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            Candidate c = (Candidate)entry.getKey();
            testWinners.add(c);
            i++;
        }

        assertEquals(stvelection3.getDroop(), 3);

        assertEquals(expectedWinners, testWinners);
        assertEquals(expectedLosers, stvelection3.getLosers());
    }


    // all seats are filled after the second pass
    @Test
    public void runElectionSecondPassTest()
    {
        STVElection stvelection3 = new STVElection(2, "testing/sampleSTVBallot2.csv", false);

        Candidate testCand0 = new Candidate(0, "A");
        Candidate testCand1 = new Candidate(1, "B");
        Candidate testCand2 = new Candidate(2, "C");

        // the expected losers
        ArrayList<Candidate> expectedLosers = new ArrayList<Candidate>();
        expectedLosers.add(testCand2);

        // the expected winners
        ArrayList<Candidate> expectedWinners = new ArrayList<Candidate>();
        expectedWinners.add(testCand0);
        expectedWinners.add(testCand1);


        stvelection3.runElection();

        // get the hashmap of actual winners
        HashMap<Candidate, ArrayList<Ballot>> testElected = stvelection3.getElected();

        // put the winners in an array
        Iterator it = testElected.entrySet().iterator();
        int i = 0;
        ArrayList<Candidate> testWinners = new ArrayList<Candidate>();
        while(it.hasNext())  
        {
            Map.Entry entry = (Map.Entry)it.next();
            Candidate c = (Candidate)entry.getKey();
            testWinners.add(c);
            i++;
        }

        assertEquals(3, stvelection3.getDroop());

        assertEquals(expectedWinners, testWinners);
        assertEquals(expectedLosers, stvelection3.getLosers());
    }

    // no one else can reach droop, pick the last loser
    @Test
    public void runElectionPickFromLosersTest()
    {
        STVElection stvelection3 = new STVElection(3, "testing/sampleSTVBallot4.csv", false);

        Candidate testCand0 = new Candidate(0, "A");
        Candidate testCand1 = new Candidate(1, "B");
        Candidate testCand2 = new Candidate(2, "C");
        Candidate testCand3 = new Candidate(3, "D");

        // the expected losers
        ArrayList<Candidate> expectedLosers = new ArrayList<Candidate>();
        expectedLosers.add(testCand3);

        // the expected winners
        ArrayList<Candidate> expectedWinners = new ArrayList<Candidate>();
        expectedWinners.add(testCand0);
        expectedWinners.add(testCand1);
        expectedWinners.add(testCand2);

        stvelection3.runElection();

        // get the hashmap of actual winners
        HashMap<Candidate, ArrayList<Ballot>> testElected = stvelection3.getElected();

        // put the winners in an array
        Iterator it = testElected.entrySet().iterator();
        int i = 0;
        ArrayList<Candidate> testWinners = new ArrayList<Candidate>();
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            Candidate c = (Candidate)entry.getKey();
            testWinners.add(c);
            i++;
        }

        assertEquals(2, stvelection3.getDroop());

        assertEquals(expectedWinners, testWinners);
        assertEquals(expectedLosers, stvelection3.getLosers());
    }

    @Test
    public void displayStatsTest()
    {
        STVElection stvelection3 = new STVElection(2, "testing/sampleSTVBallot2.csv", false);
        stvelection3.runElection();
        String res = null;
        PrintStream originalOut = System.out;
        try 
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            System.setOut(ps);
            stvelection3.displayStats();
            ps.flush();
            res = os.toString();

            String expectedDisp = "Election Type: STV\r\n" +
                                  "Seats: 2\r\n" +
                                  "Number of Candidates: 3\r\n" +
                                  "The order in which the candidates are listed, is the order that they were elected.\r\n" +
                                  "Elected: A, B, \r\n" +
                                  "Non-Elected: C, \r\n" +
                                  "Audit File Name: Election-Audit-File\r\n" +
                                  "Audit File Location: src/Election-Audit-File";
            assertEquals(expectedDisp, res);
        }
        finally 
        {
            System.setOut(originalOut);
        }
    }

}