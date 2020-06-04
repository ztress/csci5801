/**
*    PluralityElection class
 *
*    The head class for running a Plurality Election. Has getter and setter methods, a method to run the actual election,
*    a method to display the stats of the election, and a method to call the ballot generator.
 *
*    @author Zac Tressel and Ian Luck
 */

import java.util.*;
import java.lang.Math;

public class PluralityElection implements Election {
  private int numCandidates;
  private int numSeats;
  private String ballotFileLocation;
  private ArrayList<Ballot> votes = new ArrayList<Ballot>();
  private HashMap<Candidate, ArrayList<Ballot>> elected = new HashMap<Candidate, ArrayList<Ballot>>();
  private HashMap<Candidate, ArrayList<Ballot>> nonElected = new HashMap<Candidate, ArrayList<Ballot>>();

  /**
  * The default Constructor for the Plurality Election class. It takes in the
  * number of seats as well as the location of the ballot file.
  *
  * @param numSeats The number of seats to fill during the election.
  * @param ballotFileLocation The ballot file location, held as a String.
  */
  public PluralityElection(int numSeats, String ballotFileLocation) {
    this.numSeats = numSeats;
    this.ballotFileLocation = ballotFileLocation;
  }

  /**
  * Retrieves the ArrayList of Ballots and returns it.
  *
  * @return The ArrayList holding the ballots
  */
  public ArrayList<Ballot> getBallots() {
    return this.votes;
  }

  /**
  * Retrieves the number of seats to fill and returns it.
  *
  * @return number of seats
  */
  public int getNumSeats() {
    return this.numSeats;
  }

  /**
  * Retrieves the number of candidates and returns it.
  *
  * @return The number of candidates
  */
  public int getNumCandidates() {
    return this.numCandidates;
  }

  /**
  * Retrieves the HashMap of elected candidates and their respective
  * Ballot ArrayLists.
  *
  * @return The HashMap of elected candidates
  */
  public HashMap<Candidate, ArrayList<Ballot>> getElected() {
    return this.elected;
  }

  /**
  * Retrieves the HashMap of non elected candidates and their respective
  * Ballot ArrayLists.
  * @return The HashMap of non elected candidates
  */
  public HashMap<Candidate, ArrayList<Ballot>> getNonElected() {
    return this.nonElected;
  }

  /**
  * Runs an election by generating ballots and then determining the elected
  * and non elected candidates and places them in their different
  * HashMaps.
  */
  public void runElection() {
    generateBallots();
    numCandidates = nonElected.size();
    Iterator it1 = nonElected.entrySet().iterator();
    while (it1.hasNext())   // Iterates through hashmap of candidates
    {
      Map.Entry entry = (Map.Entry) it1.next();
      entry.setValue(new ArrayList<>());
    }

    for (int i = 0; i < votes.size(); i++)  // Iterate through all ballots once
    {
      votes.get(i).getChoice().addBallot();
      if(nonElected.get(votes.get(i).getChoice()) == null)
      {
        ArrayList<Ballot> emptyList = new ArrayList<Ballot>();
        nonElected.put(votes.get(i).getChoice(), emptyList);
      }
      nonElected.get(votes.get(i).getChoice()).add(votes.get(i));
    }

    while (numSeats > elected.size())  // Assign winners until no spots left
    {
      int ind = (int) (Math.random() * (votes.size() - 1));  // Randomizes ties
      Candidate topCandidate = votes.get(ind).getChoice();
      int mostVotes = topCandidate.getVotes();

      Iterator it = nonElected.entrySet().iterator();
      while (it.hasNext())   // Iterates through hashmap of candidates
      {
        Map.Entry entry = (Map.Entry) it.next();
        Candidate c = (Candidate) entry.getKey();
        if (c.getVotes() > mostVotes) {
          mostVotes = c.getVotes();
          topCandidate = c;
        }
      }
      if(!elected.containsKey(topCandidate))  // Added to avoid null pointer exception
      {
        elected.put(topCandidate, nonElected.get(topCandidate));
      }

      nonElected.remove(topCandidate);
    }
  }

  /**
  * Prints out election statistics to the terminal for viewing purposes.
  */
  public void displayStats() {
    System.out.println("Election Type: Plurality");
    System.out.println("Seats: " + getNumSeats());
    System.out.println("Number of Candidates: " + getNumCandidates());
    System.out.println("The order in which the candidates are listed, is the order that they were elected.");
    System.out.println("Elected:");
    int total_votes = getBallots().size();
    Iterator it = elected.entrySet().iterator();
    while (it.hasNext())   // Iterates through hashmap of elected candidates
    {
      Map.Entry entry = (Map.Entry) it.next();
      Candidate c = (Candidate) entry.getKey();
      System.out.println(c.getName() + " | % Votes: " + (c.getVotes() * 100 / total_votes) + "%");
    }
    System.out.println("Non-Elected:");
    it = nonElected.entrySet().iterator();
    int count = 0;
    while (it.hasNext())   // Iterates through hashmap of non-elected candidates
    {
      Map.Entry entry = (Map.Entry) it.next();
      Candidate c = (Candidate) entry.getKey();
      if (count == nonElected.size()-1) {
        System.out.println(c.getName());
      } else {
        System.out.print(c.getName() + ", ");
      }
      count++;
    }
    // Here we are printing the location and name of the audit file.
    System.out.println("Audit File Name: Election-Audit-File");
    System.out.print("Audit File Location: src/Election-Audit-File");
  }

  /**
  * Creates a new ballotGenerator and creates a new ArrayList of ballots using
  * the createBallots function. Sets the votes ArrayList equal to the newly
  * generated ballot list.
  */
  public void generateBallots() {
    BallotGenerator ballotGen = new BallotGenerator(ballotFileLocation, false, "Plurality");
    this.votes = ballotGen.createBallots(nonElected);
  }
}
