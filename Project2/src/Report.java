/**
* Report Class
*
* The Report class creates the auditable report file for the election
* being run and writes information regarding the election to the
* file.
*
* @author  Isabel Tomb
*/

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.FileWriter;
import java.io.File;
import java.util.*;

import java.nio.file.*;

public class Report{
  
    // Class Variables
    private String electionType;
    private int seats;
    private int numCandidates;
    private HashMap<Candidate, ArrayList<Ballot>> electedHash;
    private HashMap<Candidate, ArrayList<Ballot>> nonElectedHash;
    private String logFileName;
    private ArrayList<Candidate> losers;

    /**
    * Constructor creates a Candidate object with an index, name
    * and starting number of votes.
    *
    * @param currentElection election object to parse election information from
    *
    */
    public Report(Election currentElection)
    {
        if (currentElection instanceof PluralityElection)
        {
            this.electionType = "Plurality";
        }
        else if (currentElection instanceof STVElection)
        {
            this.electionType = "STV";
            STVElection STVelec = (STVElection) currentElection;
            this.losers = STVelec.getLosers();
        }
        this.seats = currentElection.getNumSeats();
        this.numCandidates = currentElection.getNumCandidates();
        this.logFileName = "Election-Audit-File";
        this.electedHash = currentElection.getElected();
        this.nonElectedHash = currentElection.getNonElected();
    }


    /**
    * Creates an auditable log file, loops through the elected HashMap and writes the name
    * of the elected candidate and all associated ballots in the order they were elected.
    * At the end, writes the election type, the number of seats, and number of candidates
    * to the log file as well.
    */
    public void createLog()
    {
        try {
            // writer object to write to the log file
            FileWriter logWriter = new FileWriter(logFileName.toString(), false);
            logWriter.write("Elected: \n");
            Iterator it = electedHash.entrySet().iterator();
            // iterate through the HashMap of elected candidates 
            while(it.hasNext())
            {
                Map.Entry entry = (Map.Entry)it.next();
                Candidate candidate = (Candidate)entry.getKey();
                logWriter.write(candidate.getName() + " - ");
                ArrayList<Ballot> ballotsList = electedHash.get(candidate);
                boolean noVotes = false;
                if(ballotsList == null)
                {
                    noVotes = true;
                }
                if(!noVotes)
                {
                    for(int i = 0; i < ballotsList.size(); i++)
                    {
                        // write each ballot associated with the candidate to the log file
                        if (i == ballotsList.size()-1) {
                            logWriter.write(ballotsList.get(i).toString());
                        } else {
                            logWriter.write(ballotsList.get(i).toString() + ", ");
                        }
                    }
                }

                logWriter.write("\n");
            }
            logWriter.write("Non-Elected: \n");
            // iterate through nonelected hashmap if running a plurality election
            if (electionType.equals("Plurality")) {
                it = nonElectedHash.entrySet().iterator();
                while(it.hasNext())
                {
                    Map.Entry entry = (Map.Entry)it.next();
                    Candidate candidate = (Candidate)entry.getKey();
                    logWriter.write(candidate.getName() + " - ");
                    ArrayList<Ballot> ballotsList = nonElectedHash.get(candidate);
                    for(int i = 0; i < ballotsList.size(); i++)
                    {
                        // write each ballot associated with the candidate to the log file
                        logWriter.write(ballotsList.get(i).toString() + " ");
                    }
                    logWriter.write("\n");
                }
            }
            else if (electionType.equals("STV"))
            {
                for(int i = 0; i < losers.size(); i++) {
                    if (i == losers.size()-1) {
                        logWriter.write(losers.get(i).getName() + "\n");
                    } else {
                        logWriter.write(losers.get(i).getName() + ", ");
                    }
                }
            }
            // remaining stats to be written to the log file
            logWriter.write("\nElection Type: " + electionType);
            logWriter.write("\nSeats: " + seats);
            logWriter.write("\nNumber of Candidates: " + numCandidates + "\n");
            logWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Handles any file related errors if they occur. Writes an error message to the log file
    * to inform the user that an error occured during the election
    */
    public void fileErrors()
    {
        try {
            // new filewriter object to write to log file if there are any errors
            FileWriter writeError = new FileWriter(logFileName, true);  // second argument indicates append mode turned on
            writeError.write("An error occurred in the election process");
            writeError.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
