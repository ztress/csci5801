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
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.*;

public class BallotGenerator {
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
     * @param candidates empty HashMap representing unelected candidates and their associated votes to be filled.
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

        if (electionType.equals("STV")) {
            ArrayList<STVBallot> STVballots = new ArrayList<STVBallot>();
            STVBallot tempBallot;
            for(int i = 0; i < ballots.size(); i++) {
                tempBallot = (STVBallot) ballots.get(i);  // cast the ballots in the ballot array to STVballot type
                STVballots.add(tempBallot);
            }
            invalidator(STVballots);  // call the invalidator method on the ballot array to sort through the ballots and remove any that are less than half full
            ballots.retainAll(STVballots);
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
     * Sorts through the created ballots and remove any ballots that have less than half
     * of the candidates ranked
     * @param allBallots ArrayList of all STVballots created by ballot generator
     */
    public void invalidator(ArrayList<STVBallot> allBallots) {              // not sure yet if the function should be void or return the invalid list
        ArrayList<STVBallot> validBallots = new ArrayList<STVBallot>();     // arrayList to hold the valid ballots
        ArrayList<STVBallot> invalidBallots = new ArrayList<STVBallot>();   // arrayList to hold the invalid ballots
        STVBallot tempBallot;
        int i = 0;
        int ballotArraySize = allBallots.size();

        while(i < ballotArraySize) {
            tempBallot = allBallots.get(i);                                 // get a ballot from the ballot ArrayList
            int zeroCount = 0;
            for(int j = 0; j < tempBallot.getVoteSize(); j++) {             // loop through the votes array for each ballot
                if(tempBallot.getVotes()[j] == 0) {                         // if there is a zero at the given index in the votes array then increment the counter
                    zeroCount++;
                }
            }
            if(zeroCount > ((tempBallot.getVoteSize())/2)) {                // if the counter reaches a value higher than half the number of candidates-
                invalidBallots.add(tempBallot);                             // add the ballot to the invalid ballots array and break the loop
            }
            else {
                validBallots.add(tempBallot);                               // otherwise add it to the valid ballots array because more than half the candidates are rated
            }
            i++;
        }
        allBallots.retainAll(validBallots);                                 // retain only the ballots that were in the valid ballots arraylist

        invalidBallotReport(invalidBallots, validBallots.size());           // call invalid ballot report method
    }
    /**
     * Creates a new file and writes to that file a report of the invalidated ballots and prints out statistics 
     * regarding the percentage of invalidated ballots, ratio, and the number of invalidated  ballots. Prints out information on 
     * each individual invalidated ballot as well. 
     * @param invalidBallots ArrayList of all invalidated ballots. 
     * @param validBallotCount int of the number of valid ballots. 
     */
    public void invalidBallotReport(ArrayList<STVBallot> invalidBallots, int validBallotCount) {
        try {
            int invalidBallotCount = invalidBallots.size();
            double percentage_value = ((double) invalidBallotCount / ((double) validBallotCount + invalidBallotCount)) * 100;
            int p = (int) percentage_value;
            double ratio_value = 1;
            if (invalidBallotCount > 0) {
                ratio_value = ((double) validBallotCount) / invalidBallotCount;
            }
            FileWriter logWriter = new FileWriter("Invalid-Ballots-Report", false);
            logWriter.write("Number of Invalidated Ballots: " + invalidBallotCount + "\n");
            logWriter.write("Percentage of Ballots that were Invalidated: " + p + "%\n" );
            logWriter.write("Ratio of Valid Ballots to Invalidated Ballots: " + (int) ratio_value + ":1\n");
            if (invalidBallotCount == 0){
                logWriter.write("No invalidated ballots to display.\n");
            }
            else {
                logWriter.write("List of Invalidated Ballots:\n");
                for (int i = 0; i < invalidBallots.size(); i++) {
                    logWriter.write(Arrays.toString(invalidBallots.get(i).getVotes()) + "\n");
                }
            }
            logWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
