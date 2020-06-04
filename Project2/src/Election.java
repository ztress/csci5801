/**
 * Election interface
 *
 * The Election interface represents a generic election.
 * Both STV Election and Plurality Election implement this interface.
 *
 * @author Ian Luck
 */
import java.util.*;
interface Election
{
  void runElection();
  void displayStats();
  void generateBallots();
  ArrayList<Ballot> getBallots();
  int getNumSeats();
  int getNumCandidates();
  HashMap<Candidate, ArrayList<Ballot>> getElected();
  HashMap<Candidate, ArrayList<Ballot>> getNonElected();

}
