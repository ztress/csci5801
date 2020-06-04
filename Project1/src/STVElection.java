/**
*    STVElection class
*
*    The head class for running an STV Election. Has getter and setter methods, a method to run the actual election,
*    a method to display the stats of the election, and a method to call the ballot generator.
*
*    @author Zac Tressel and Ian Luck
 */

import java.util.*;
import java.lang.Math;

public class STVElection implements Election
{
  private ArrayList<Candidate> losers = new ArrayList<Candidate>();
  private int droop;
  private boolean shuffle;
  private int numCandidates;
  private int numSeats;
  private String ballotFileLocation;
  private ArrayList<Ballot> votes = new ArrayList<Ballot>();
  private HashMap<Candidate, ArrayList<Ballot>> elected = new HashMap<Candidate, ArrayList<Ballot>>();
  private HashMap<Candidate, ArrayList<Ballot>> nonElected = new HashMap<Candidate, ArrayList<Ballot>>();


  /**
   * The default Constructor for the STV Election class. It takes in the
   * number of seats, the location of the ballot file, and the option to shuffle.
   *
   * @param numSeats The number of seats to fill during the election.
   * @param ballotFileLocation The ballot file location, held as a String.
   * @param shuffle A boolean option, where true indicates the ballots will shuffled before counted
   */
  public STVElection(int numSeats, String ballotFileLocation, boolean shuffle)
  {
    this.numSeats = numSeats;
    this.ballotFileLocation = ballotFileLocation;
    this.shuffle = shuffle;
  }

  /**
   * Retrieves the number calculated to be droop
   * This is the number of votes that candidates want to reach
   *
   * @return The int droop
   */
  public int getDroop()
  {
    return this.droop;
  }

  /**
   * Checks whether the shuffle option has been chosen by the user,
   * which indicates that the order of ballots will be shuffled before counting
   *
   * @return The boolean value indicating if the ballots will be/have been shuffled
   */
  public boolean isShuffled()
  {
    return this.shuffle;
  }

  /**
   * Retrieves the HashMap of elected candidates and their respective
   * Ballot ArrayLists.
   *
   * @return The HashMap of elected candidates
   */
  public HashMap<Candidate, ArrayList<Ballot>> getElected()
  {
    return this.elected;
  }

  /**
   * Retrieves the HashMap of non elected candidates and their respective
   * Ballot ArrayLists.
   *
   * @return The HashMap of non elected candidates
   */
  public HashMap<Candidate, ArrayList<Ballot>> getNonElected()
  {
    return this.nonElected;
  }

  /**
   * Retrieves the ArrayList of candidates who did not meet droop
   *
   * @return The ArrayList of (potentially) losing candidates
   */
  public ArrayList<Candidate> getLosers()
  {
    return this.losers;
  }

  /**
   * Retrieves the ArrayList of Ballots and returns it.
   *
   * @return The ArrayList holding the ballots
   */
  public ArrayList<Ballot> getBallots()
  {
    return this.votes;
  }

  /**
   * Retrieves the number of seats to fill and returns it.
   *
   * @return number of seats
   */
  public int getNumSeats()
  {
    return this.numSeats;
  }

  /**
   * Retrieves the number of candidates and returns it.
   *
   * @return The number of candidates
   */
  public int getNumCandidates()
  {
    return this.numCandidates;
  }


  /**
   * Runs an election by generating ballots and then determining the elected
   * and non elected candidates and places them in their different
   * HashMaps.
   */
  public void runElection()
  {
    generateBallots();

    numCandidates = nonElected.size();

    droop = (int)Math.floor(votes.size()/(numSeats + 1)) + 1;

    // First pass starting
    for(int i=0; i< votes.size(); i++)
    {
      boolean ignore = false;
      while(!nonElected.containsKey(votes.get(i).getChoice()))
      {
        ((STVBallot) votes.get(i)).incrementCurrNum();
        if(((STVBallot)votes.get(i)).getCurrNum() > ((STVBallot) votes.get(i)).getVoteSize());
        {
          ignore = true;  // Will ignore the ballot if it is no longer useful
          break;
        }
      }

      if(!ignore) // If ballot doesn't have any more candidates, this is skipped
      {
        votes.get(i).getChoice().addBallot();
        if(nonElected.get(votes.get(i).getChoice()) == null)
        {
          ArrayList<Ballot> emptyList = new ArrayList<Ballot>();
          nonElected.put(votes.get(i).getChoice(), emptyList);
        }
        nonElected.get(votes.get(i).getChoice()).add(votes.get(i));   // Adds ballot to Array List
        if(votes.get(i).getChoice().getVotes() == droop)  // Checks for droop
        {
          elected.put(votes.get(i).getChoice(), nonElected.get(votes.get(i).getChoice()));
          nonElected.remove(votes.get(i).getChoice());
          if(numSeats == elected.size())      // If no more seats left, election is over
          {
            break;
          }
        }
      }
    }
    // First pass done
    votes.clear();  // Empties queue of votes

    // Loop until election is finished. Same exact as above
    while(!nonElected.isEmpty() && numSeats > elected.size())
    {
      Candidate losingCandidate = null;
      int lowestVotes = Integer.MAX_VALUE;

      Iterator it = nonElected.entrySet().iterator();
      while(it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        Candidate c = (Candidate)entry.getKey();
        if(c.getVotes() < lowestVotes)
        {
          lowestVotes = c.getVotes();
          losingCandidate = c;
        }
        else if(c.getVotes() == lowestVotes)
        {
          if(c.getVotes() != 0)
          {
            if(((STVBallot)nonElected.get(c).get(0)).getBallotID() > ((STVBallot)nonElected.get(losingCandidate).get(0)).getBallotID())
            {
              lowestVotes = c.getVotes();   // Whoever got their first vote last loses the tie
              losingCandidate = c;
            }
          }

        }
      }

      votes = nonElected.get(losingCandidate);
      losers.add(losingCandidate);
      nonElected.remove(losingCandidate);

      boolean skipCandidate = false;

      if(losingCandidate.getVotes() == 0)
      {
        skipCandidate = true;
      }

      if(!skipCandidate)
      {
        for(int i=0; i< votes.size(); i++)
        {
          boolean ignore = false;
          while(!nonElected.containsKey(votes.get(i).getChoice()))
          {
            ((STVBallot) votes.get(i)).incrementCurrNum();
            if(((STVBallot)votes.get(i)).getCurrNum() > ((STVBallot) votes.get(i)).getVoteSize()) {
              ignore = true;
              break;
            }
          }

          if(!ignore)
          {
            votes.get(i).getChoice().addBallot();
            nonElected.get(votes.get(i).getChoice()).add(votes.get(i));
            if(votes.get(i).getChoice().getVotes() == droop)
            {
              elected.put(votes.get(i).getChoice() ,nonElected.get(votes.get(i).getChoice()));
              nonElected.remove(votes.get(i).getChoice());
              if(numSeats == elected.size())
              {
                break;
              }
            }
          }
        }
      }

    }
    // All ballots have been used/ No one else can reach droop


    if(nonElected.isEmpty())  // Still spots left
    {
      int ind = losers.size() - 1;  // Start with last person to "lose"
      while(numSeats > elected.size())    // While there is a spot left
      {
        ArrayList<Ballot> empty = new ArrayList<Ballot>();
        elected.put(losers.get(ind), empty);   // Get last loser
        losers.remove(ind);
        ind--;
      }
    }
    else  // Still candidates left
    {
      Iterator it = nonElected.entrySet().iterator();
      ArrayList<Candidate> toRemove = new ArrayList<>();
      while(it.hasNext())
      {
        Map.Entry entry = (Map.Entry) it.next();
        Candidate c = (Candidate) entry.getKey();
        losers.add(c);
        toRemove.add(c);
      }
      for (Candidate c : toRemove)
      {
        nonElected.remove(c);
      }
    }
  }

  /**
   * Prints out election statistics to the terminal for viewing purposes.
   */
  public void displayStats()
  {
    System.out.println("Election Type: STV");
    System.out.println("Seats: " + getNumSeats());
    System.out.println("Number of Candidates: " + getNumCandidates());
    System.out.println("The order in which the candidates are listed, is the order that they were elected.");

    System.out.print("Elected: ");

    Iterator it = elected.entrySet().iterator();
    while(it.hasNext())   // Iterates through hashmap of elected candidates
    {
      Map.Entry entry = (Map.Entry)it.next();
      Candidate c = (Candidate)entry.getKey();
      System.out.print(c.getName() + ", ");

    }
    System.out.println();

    System.out.print("Non-Elected: ");
    int len = getLosers().size();
    for(int i = 0; i < len; i++)// Iterates through ArrayList of Loser candidates
    {
      System.out.print(getLosers().get(i).getName() + ", ");
    }
    System.out.println();
    System.out.println("Audit File Name: Election-Audit-File");
    System.out.print("Audit File Location: src/Election-Audit-File");
  }

  /**
   * Creates a new ballotGenerator and creates a new ArrayList of ballots using
   * the createBallots function. Sets the votes ArrayList equal to the newly
   * generated ballot list.
   */
  public void generateBallots() {
    BallotGenerator ballotGen = new BallotGenerator(ballotFileLocation, shuffle, "STV");
    this.votes = ballotGen.createBallots(nonElected);
  }
}
