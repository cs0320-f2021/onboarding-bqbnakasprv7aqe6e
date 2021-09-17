package edu.brown.cs.student.main;

public class StarNotFoundException extends Exception {

    private String name;

    public StarNotFoundException(String name) {
        this.name = name;
    }
}
