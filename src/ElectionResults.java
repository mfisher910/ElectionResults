import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/*
 Author:
 Date:
 Description:
 */
public class ElectionResults {

    // the main method works as follows:
    // - provided code (leave this code as is):
    //   -- prompts user for file name containing ballot data
    //   -- reads data into array (one array item per line in file)
    // - code you need to write:
    //   -- execute the Ranked Choice Voting process as outlined
    //     in the project description document by calling the other
    //     methods that you will implement in this project
    public static void main(String[] args) {
        // Establish console Scanner for console input
        Scanner console = new Scanner(System.in);

        // Determine the file name containing the ballot data
        System.out.print("Ballots file: ");
        String fileName = console.nextLine();

        // Read the file contents into an array.  Each array
        // entry corresponds to a line in the file.
        String[] fileContents = getFileContents(fileName);

        // ***********************************************
        // Your code below here: execute the RCV process,
        // ensuring to make use of the remaining methods
        // ***********************************************
        ArrayList<Ballot> ballots = convert(fileContents);

        boolean winnerFound = false;
        while (!winnerFound && !ballots.isEmpty()) {
            HashMap<String, Integer> currentTallies = tallies(ballots);

            System.out.println("\nCurrent Round of Voting:");
            printCounts(currentTallies);
            printPercentages(currentTallies);

            Result result = analyze(currentTallies);

            if (result.isWinner()) {
                System.out.println("\nWinner: " + result.getName());
                winnerFound = true;
            } else {
                System.out.println("\nEliminating: " + result.getName());
                remove(result.getName(), ballots);
            }
        }

        if (!winnerFound) {
            System.out.println("All candidates eliminated or no ballots left.");
        }

    }

    // Create your methods below here
    public static ArrayList<Ballot> convert(String[] fileContents){
        ArrayList<Ballot> ballots = new ArrayList<>();
        for (String ballotString : fileContents) {
            String[] candidates= ballotString.split(",");
            Ballot ballot = new Ballot();
            for (String candidate : candidates){
                ballot.addCandidate(candidate);
            }
            ballots.add(ballot);
        }
        return ballots;
    }

    public static HashMap<String, Integer> tallies(ArrayList<Ballot> ballots){
        HashMap<String, Integer> voteCount = new HashMap<>();

        for (Ballot ballot : ballots) {
            String candidate = ballot.getCurrentChoice();
            voteCount.put(candidate, voteCount.getOrDefault(candidate, 0) + 1);
        }

        return voteCount;
    }

    public static int countTotalVotes (HashMap<String, Integer> totalVotes){
        int thetotalVotes = 0;
        for (int votes : totalVotes.values()) {
            thetotalVotes += votes;
        }
        return thetotalVotes;
    }

    public static Result analyze (HashMap<String, Integer> totalVotes) {
        Result results;
        int lowest=countTotalVotes(totalVotes);
        String candidate = "";
        for (String person:totalVotes.keySet()){
            if (totalVotes.get(person)>countTotalVotes(totalVotes)/2) {
                results= new Result(person, true);
                return results;
            }
            else if (totalVotes.get(person)<lowest){
                lowest=totalVotes.get(person);
                candidate=person;
            }
        }
        results=new Result (candidate, false);
        return results;
    }

    public static void printCounts(HashMap<String, Integer> totalVotes){
        System.out.println("Vote Tallies");
        for (String candidate : totalVotes.keySet()) {
            System.out.println(candidate + ": " + totalVotes.get(candidate));
            }
        }

    public static void remove (String candidateName, ArrayList<Ballot> ballots) {
        Iterator<Ballot> iterator = ballots.iterator();

        while (iterator.hasNext()) {
            Ballot ballot = iterator.next();
            ballot.removeCandidate(candidateName);

            if (ballot.isExhausted()) {
                iterator.remove();
            }
        }
    }

    public static void printPercentages (HashMap<String, Integer> totalVotes){
        double theTotalVotes = countTotalVotes(totalVotes);
        System.out.println("Vote Percentages");
        for (String candidate : totalVotes.keySet()) {
            double percentage = (totalVotes.get(candidate) / (theTotalVotes) * 100);
            System.out.print(percentage + " " + candidate);
        }
    }


    // DO NOT edit the methods below. These are provided to help you get started.
    public static String[] getFileContents(String fileName) {

        // first pass: determine number of lines in the file
        Scanner file = getFileScanner(fileName);
        int numLines = 0;
        while (file.hasNextLine()) {
            file.nextLine();
            numLines++;
        }

        // create array to hold the number of lines counted
        String[] contents = new String[numLines];

        // second pass: read each line into array
        file = getFileScanner(fileName);
        for (int i = 0; i < numLines; i++) {
            contents[i] = file.nextLine();
        }

        return contents;
    }


    public static Scanner getFileScanner(String fileName) {
        try {
            FileInputStream textFileStream = new FileInputStream(fileName);
            Scanner inputFile = new Scanner(textFileStream);
            return inputFile;
        }
        catch (IOException ex) {
            System.out.println("Warning: could not open " + fileName);
            return null;
        }
    }
}
