package com.prekdu;

import java.util.Scanner;

/*
 * Create a basic Java Program that takes two strings as input and produces the following output.
 * Print the length of the first string
 * Print the length of the second string
 * Print if the length matches or not
 * Print if the two strings are the same
 */

public class StringComparison {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

      // Input two strings
      System.out.println("Enter the first string:");
      String firstString = scanner.nextLine();

      System.out.println("Enter the second string:");
      String secondString = scanner.nextLine();

      scanner.close();

      // Print lengths of the strings
      System.out.println("Length of first string: " + firstString.length());
      System.out.println("Length of second string: " + secondString.length());

      // Check if lengths match
      if (firstString.length() == secondString.length()) {
          System.out.println("The lengths are equal.");
      } else {
          System.out.println("The lengths are not equal.");
      }

      // Check if strings are the same
      if (firstString.equals(secondString)) {
          System.out.println("The two strings are same.");
      } else {
          System.out.println("The two strings are not same.");
      }
  }
}
