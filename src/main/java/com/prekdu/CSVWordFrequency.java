package com.prekdu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * Create a basic Java Program that reads a CSV file and prints the top 3 repeated words in the file.
 * The CSV file is in the reosurces folder and the file name is input.csv.
 */

public class CSVWordFrequency {
  public static void main(String[] args) {
    String csvFile = "src/main/resources/input.csv";
    Map<String, Integer> wordCountMap = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            // Split line by non-word characters and loop through words
            String[] words = line.split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Use a priority queue to store words by their frequency
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(wordCountMap.entrySet());

        // Print top 3 repeated words
        System.out.println("Top 3 most repeated words:");
        int count = 0;
        while (!pq.isEmpty() && count < 3) {
            Map.Entry<String, Integer> entry = pq.poll();
            System.out.println(entry.getKey() + ": " + entry.getValue());
            count++;
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}
