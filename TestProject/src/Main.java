import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Main {

    public static int countDuplicateWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }

        ArrayList<String> inputList = new ArrayList<>(Arrays.asList(input.split(" ")));
        HashSet<String> duplicates = new HashSet<>();

        int totalCount = 0;

        for (int i = 0; i < inputList.size(); ++i) {
            int count = 0;
            String currentWord = inputList.get(i);

            if (duplicates.contains(currentWord)) {
                continue;
            }

            for (int j = i + 1; j < inputList.size(); ++j) {
                if (currentWord.equals(inputList.get(j))) {
                    count++;
                }
            }

            if (count > 0) {
                duplicates.add(currentWord);
                totalCount += count;
            }
        }

        return totalCount;
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println(countDuplicateWords("You jump I jump")); // Output: 1
        System.out.println(countDuplicateWords("You can join us and I can join another team join")); // Output: 2
        System.out.println(countDuplicateWords("This is good")); // Output: 0
    }
}
