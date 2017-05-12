package com.ibm.bluemix;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

// this code looks like it is testing the scanner, which is a java util...
public class CandidateAppTest extends TestCase {
  private final ByteArrayOutputStream systemOutStream = new ByteArrayOutputStream();
  public CandidateAppTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(CandidateAppTest.class);
  }

  public void testCandidateApp() throws IOException {
    System.setOut(new PrintStream(systemOutStream));
    CandidateApp.main(null);

    List<String> expected = Files.readAllLines(
        FileSystems.getDefault().getPath("test-data", "output.txt"));

    String systemOutContents = systemOutStream.toString();

    int i = 0;
    try(Scanner scanner = new Scanner(systemOutContents)) {
      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();
        assertEquals("Mismatched output on line " + (i + 1), expected.get(i), line);
        i++;
      }
    }

    System.setOut(null);
  }
}
