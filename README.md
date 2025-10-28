# Ranked Choice Voting Election Simulator

This Java project simulates an **instant-runoff (ranked choice) voting election** using ballots provided in a file. It processes votes round by round, eliminating the candidate with the fewest votes until a winner is determined.

---

## Project Structure

### `Result.java`
- Represents the outcome of a candidate in an election round.
- **Fields:**
  - `name` – the candidate's name.
  - `isWinner` – boolean indicating whether the candidate has won.
- **Methods:**
  - `getName()`, `isWinner()`, `isLoser()`
  - `equals()`, `compareTo()`, `hashCode()`, `toString()`
- Implements `Comparable<Result>` to allow sorting of results.

---

### `Ballot.java`
- Represents a single voter's ranked ballot.
- **Fields:**
  - `choices` – a list of candidate names in order of preference.
- **Methods:**
  - `addCandidate(String name)` – adds a candidate if not already on the ballot.
  - `removeCandidate(String name)` – removes a candidate from the ballot.
  - `getCurrentChoice()` – returns the top-ranked candidate still in the race.
  - `isExhausted()` – checks if the ballot has no remaining candidates.
  - `equals()`, `compareTo()`, `hashCode()`, `toString()`
- Implements `Comparable<Ballot>` to allow comparison of ballots.

---

### `ElectionResults.java`
- Main class that drives the election simulation.
- **Responsibilities:**
  - Reads ballots from a CSV file.
  - Converts the file into `Ballot` objects.
  - Tallies votes round by round.
  - Eliminates the candidate with the fewest votes in each round.
  - Determines and announces the winner.
- **Key Methods:**
  - `convert(String[] fileContents)` – converts file lines into `Ballot` objects.
  - `tallies(ArrayList<Ballot> ballots)` – counts votes for current top choices.
  - `analyze(HashMap<String, Integer> totalVotes)` – finds a winner or candidate to eliminate.
  - `remove(String candidateName, ArrayList<Ballot> ballots)` – removes eliminated candidates from ballots.
  - `printCounts` and `printPercentages` – display the current round results.
  - `getFileContents` and `getFileScanner` – read ballot files safely.

---

## How to Use

1. **Prepare a ballot file**:  
   Each line represents a ballot. Candidates are separated by commas in order of preference. Example:


2. **Compile the Java files**:

```bash
javac Result.java Ballot.java ElectionResults.java
java ElectionResults
Ballots file: ballots.txt
