/**
 * BallotGenerator class
 *
 * This class parses the ballot file, determines the candidates that are running, and produces ballots to be processed by elections.
 *
 * @author Jessica Moore
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

class BallotGenerator {
    private String fileLocation;
    private boolean shuffle;
    private String electionType;

    /**
     * Constructor for the Ballot Generator class.
     * @param inFileLocation is where the user stored the ballot files
     * @param inShuffle is true unless the user specified that the shuffle should be off
     * @param inElectionType is a string that shows if it is an STV or Plurality election
     */
    public BallotGenerator(String inFileLocation, boolean inShuffle, String inElectionType) {
        fileLocation = inFileLocation;
        shuffle = inShuffle;
        electionType = inElectionType;
    }

    /**
     * Parses the ballot files, finds the Candidates running in the election, and creates Ballot Objects for each ballot contained in the files.
     * @param candidates empty HashMap representing unelected candidates & their associated votes to be filled.
     * @return ArrayList of ballot objects representing all ballots in the files.
     */
    public ArrayList<Ballot> createBallots(HashMap<Candidate, ArrayList<Ballot>> candidates) {
        String delim = ",";
        String fileLocations[] = fileLocation.split(delim);
        ArrayList<Ballot> ballots = new ArrayList<Ballot>();
        // iterate thru each file
        for (String file : fileLocations) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file)); //new file reader
                String currLine = br.readLine();
                // only update candidate array in ballot object if not already done
                if (candidates.size() == 0) {
                    String[] candList = currLine.split(delim);
                    Candidate[] inCandidates = new Candidate[candList.length];
                    for (int i=0; i < candList.length; i++) {
                        inCandidates[i] = new Candidate(i, candList[i]);
                    }
                    if (electionType.equals("STV")) {
                        STVBallot.setCandidates(inCandidates);
                    } else {
                        PluralityBallot.setCandidates(inCandidates);
                    }
                    for (int i = 0; i < inCandidates.length; i++) {
                        candidates.put(inCandidates[i], new ArrayList<>());
                    }
                }

                // iterate thru each line in file
                while ((currLine = br.readLine()) != null) {
                    String[] strVotes = currLine.split(delim);
                    int votes[] = new int[candidates.size()];
                    for (int i = 0; i < candidates.size(); i++) {
                        if (strVotes.length <= i || strVotes[i].equals("")){
                            votes[i] = 0;
                        } else {
                            votes[i] = Integer.parseInt(strVotes[i]);
                        }
                    }
                    if (electionType.equals("STV")) {
                        ballots.add(new STVBallot(votes));
                    } else {
                        ballots.add(new PluralityBallot(votes));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (shuffle) {
            shuffle(ballots, ballots.size());
        } if (electionType.equals("STV")) {
            for (int i = 0; i < ballots.size(); i++) {
                ((STVBallot) ballots.get(i)).setBallotID(i);
            }
        }

        return ballots;
    }

    /**
     * Shuffles the given array 1000 times.
     * @param ballots ArrayList of ballot objects to be shuffled.
     * @param numBallots Number of ballots in the ArrayList.
     */
    public void shuffle(ArrayList<Ballot> ballots, int numBallots) {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < numBallots; j++) {
                int randNum = (int)(Math.random()*numBallots);
                STVBallot temp = (STVBallot) ballots.get(j);    // only STVBallots will use shuffle
                ballots.set(j, ballots.get(randNum));
                ballots.set(randNum, temp);
            }
        }
    }
}
