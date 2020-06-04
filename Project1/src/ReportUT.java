import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReportUT {

    private static Election pElection;
    private static Election sElection;
    private static Report reportPTest;
    private static Report reportSTest;

    @BeforeAll
    public static void setUp() {
        pElection = new PluralityElection(2, "testing/samplePluralityBallot.csv");
        sElection = new STVElection(2, "testing/sampleSTVBallot.csv", false);
    }

    @Test
    public void testPluralityCreateLog() {
        pElection.runElection();

        reportPTest = new Report(pElection);
        reportPTest.createLog();

        ArrayList<String> expectedLines = new ArrayList<String>();
        expectedLines.add("Elected: ");
        expectedLines.add("Jess - Ballot: [1, 0, 0, 0], Ballot: [1, 0, 0, 0], Ballot: [1, 0, 0, 0]");
        expectedLines.add("Isabel - Ballot: [0, 1, 0, 0], Ballot: [0, 1, 0, 0], Ballot: [0, 1, 0, 0]");
        expectedLines.add("Non-Elected: ");
        expectedLines.add("Zac - Ballot: [0, 0, 1, 0] ");
        expectedLines.add("Ian - Ballot: [0, 0, 0, 1] ");
        expectedLines.add("");
        expectedLines.add("Election Type: Plurality");
        expectedLines.add("Seats: 2");
        expectedLines.add("Number of Candidates: 4");

        try {
            BufferedReader br = new BufferedReader(new FileReader("Election-Audit-File"));
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
    public void testSTVCreateLog() {
        sElection.runElection();

        reportSTest = new Report(sElection);
        reportSTest.createLog();

        ArrayList<String> expectedLines = new ArrayList<String>();
        expectedLines.add("Elected: ");
        expectedLines.add("Jess - Ballot #0: [1, 2, 3, 4], Ballot #2: [1, 2, 3, 4], Ballot #8: [1, 2, 3, 4], Ballot #1: [4, 3, 2, 1]");
        expectedLines.add("Zac - Ballot #5: [3, 2, 1, 4], Ballot #6: [2, 3, 1, 4], Ballot #7: [3, 2, 1, 4], Ballot #4: [4, 1, 2, 3]");
        expectedLines.add("Non-Elected: ");
        expectedLines.add("Isabel, Ian");
        expectedLines.add("");
        expectedLines.add("Election Type: STV");
        expectedLines.add("Seats: 2");
        expectedLines.add("Number of Candidates: 4");

        try {
            BufferedReader br = new BufferedReader(new FileReader("Election-Audit-File"));
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

}
