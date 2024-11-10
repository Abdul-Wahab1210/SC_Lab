package Task2;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class PermutationGeneratorTest {

    @Test
    public void testGeneratePermutationsWithUniqueCharacters() {
        String input = "abc";
        List<String> expected = List.of("abc", "acb", "bac", "bca", "cab", "cba");
        List<String> result = PermutationGenerator.generatePermutations(input);
        assertEquals(new HashSet<>(expected), new HashSet<>(result));
    }

    @Test
    public void testGeneratePermutationsWithDuplicateCharacters() {
        String input = "aab";
        List<String> expected = List.of("aab", "aba", "baa");
        List<String> result = PermutationGenerator.generateUniquePermutations(input);
        assertEquals(new HashSet<>(expected), new HashSet<>(result));
    }

    @Test
    public void testGenerateUniquePermutationsForSingleCharacter() {
        String input = "a";
        List<String> expected = List.of("a");
        List<String> result = PermutationGenerator.generateUniquePermutations(input);
        assertEquals(expected, result);
    }

    @Test
    public void testGeneratePermutationsForEmptyString() {
        String input = "";
        List<String> expected = new ArrayList<>();
        List<String> result = PermutationGenerator.generatePermutations(input);
        assertEquals(expected, result);
    }

    @Test
    public void testGenerateUniquePermutationsForEmptyString() {
        String input = "";
        List<String> expected = new ArrayList<>();
        List<String> result = PermutationGenerator.generateUniquePermutations(input);
        assertEquals(expected, result);
    }

    @Test
    public void testGeneratePermutationsIterativelyWithUniqueCharacters() {
        String input = "abc";
        List<String> expected = List.of("abc", "acb", "bac", "bca", "cab", "cba");
        List<String> result = PermutationGenerator.generatePermutationsIteratively(input);
        assertEquals(new HashSet<>(expected), new HashSet<>(result));
    }

    @Test
    public void testCompareRecursiveAndIterativePermutations() {
        String input = "abc";
        List<String> recursiveResult = PermutationGenerator.generatePermutations(input);
        List<String> iterativeResult = PermutationGenerator.generatePermutationsIteratively(input);
        assertEquals(new HashSet<>(recursiveResult), new HashSet<>(iterativeResult));
    }

    @Test
    public void testGenerateUniquePermutationsHandlesDuplicatesCorrectly() {
        String input = "aabb";
        List<String> result = PermutationGenerator.generateUniquePermutations(input);

        // Ensure no duplicate permutations
        assertEquals(result.size(), new HashSet<>(result).size());
    }

    @Test
    public void testAnalyzeTimeComplexityOutput() {
        PermutationGenerator.analyzeTimeComplexity(4);
        // Since this method only prints, we don't check outputs here.
        // Can be validated manually or refactored to return a value for testing.
    }
}
