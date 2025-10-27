import java.util.HashMap;

public class Proj1Tester {
    public static void main(String[] args){

    }
    public static void testCountTotalVotes(){
        HashMap<String, Integer> voteMap = new HashMap<>();
        voteMap.put("Alice", 10);
        voteMap.put("Bob", 20);
        voteMap.put("Carl", 15);

        int result= ElectionResults.countTotalVotes(voteMap);
    }
}
