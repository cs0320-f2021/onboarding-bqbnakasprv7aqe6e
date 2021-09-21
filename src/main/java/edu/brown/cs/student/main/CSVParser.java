package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {

    public CSVParser(){};

    /**
     * Method to load in star data.
     * @param filepath a String, the path to a file containing star data
     * @return a list of the Stars in the input file
     */
    public List<Star> parseStars(String filepath) {
        List<Star> starList = new ArrayList<Star>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath, StandardCharsets.UTF_8));
            Stream<String> allRows = br.lines();

            List<String> processedRows = new ArrayList<>();

            for (String row: (Iterable<String>) allRows::iterator) {
                processedRows.add(row);
            }

            for (String row: processedRows.subList(1, processedRows.size())) {
                String[] arguments = row.split(",");

                try {
                    Star star = new Star(Integer.parseInt(arguments[0]), arguments[1],
                            Double.parseDouble(arguments[2]), Double.parseDouble(arguments[3]),
                            Double.parseDouble(arguments[4]));
                    starList.add(star);
                } catch (Exception e) {
                    System.out.println("ERROR: Invalid star detected.");
                }
            }

            System.out.println("Read " + starList.size() + " stars from " + filepath);
            return starList;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
