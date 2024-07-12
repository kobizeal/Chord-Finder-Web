package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combination {
    public static void generateCombinations(List<Integer> arr, List<Integer> currentCombination, List<List<Integer>> result) {
        if (currentCombination.size() == arr.size()) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = 0; i < arr.size(); i++) {
            if (!currentCombination.contains(arr.get(i))) {
                currentCombination.add(arr.get(i));
                generateCombinations(arr, currentCombination, result);
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }
}
