package edu.brown.cs.student.main;

/**
 * Class for StarNotFoundException.
 */
public class StarNotFoundException extends Exception {

    /**
     * Field for the name of the star not found.
     */
    private String name;

    /**
     * Constructor for StarNotFoundException.
     * @param name the name of the star not found
     */
    public StarNotFoundException(String name) {
        this.name = name;
    }
}