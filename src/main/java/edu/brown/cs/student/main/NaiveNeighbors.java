package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for NaiveNeighbors.
 */
public class NaiveNeighbors {

    /**
     * Field for starList, the list of Star objects in which data read in from the REPL will be stored.
     */
    private List<Star> starList;

    /**
     * Constructor for NaiveNeighbors.
     * @param starList a list of the stars in a given file
     */
    public NaiveNeighbors(List<Star> starList){
        this.starList = starList;
    }

    /**
     * Method to print stars.
     * @param stars a list of Star objects to be printed
     */
    public void printStars(List<Star> stars) {
        for (Star star: stars) {
            System.out.println(star.getId());
        }
    }

    /**
     * Method to retrieve a list of nearest stars given REPL input and processed arguments.
     * @param input a string, the user input to the REPL
     * @param arguments an Array of strings, the user input split on spaces
     * @return the list of the k stars nearest the input location or star
     */
    public List<Star> getStarList(String input, String[] arguments) {
        List<Star> nearestStars;
        if (arguments.length == 5 && !input.contains("\"")) {
            nearestStars = naiveNeighborsCoord(Integer.parseInt(arguments[1]), Double.parseDouble(arguments[2]),
                    Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]), false);
        } else {
            String name = input.split("\"")[1];
            nearestStars = naiveNeighborsName(Integer.parseInt(arguments[1]), name);
        }
        return nearestStars;
    }

    /**
     * Method to return a list of the k stars which are closest to a given location
     * @param k the number of stars to be searched for
     * @param x a double, the x-coordinate of the position of interest
     * @param y a double, the y-coordinate of the position of interest
     * @param z a double, the z-coordinate of the position of interest
     * @return a list of stars, the k stars nearest the input location
     */
    private List<Star> naiveNeighborsCoord(int k, double x, double y, double z, Boolean nameInput) {
        List<Star> nearestStarList = new ArrayList<>();
        Boolean starGiven = false;
        double maxDist = 0.0;
        Star maxStar = null;
        int c = 0;

        for (Star star: starList) {
            double dist = euclideanDistance(x, y, z, star);
            if (nameInput && dist == 0) {
                starGiven = true;
            } else {
                if (c <= k) {
                    nearestStarList.add(star);
                    if (dist > maxDist) {
                        maxDist = dist;
                        maxStar = star;
                    }
                } else if (dist < maxDist) {
                    nearestStarList.add(star);
                    nearestStarList.remove(maxStar);
                    maxStar = findMax(x, y, z, nearestStarList);
                    maxDist = euclideanDistance(x, y, z, maxStar);
                } else if (dist == maxDist) {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        nearestStarList.add(star);
                        nearestStarList.remove(maxStar);
                        maxStar = star;
                    }
                }
            }
            c++;
        }

        if (nearestStarList.isEmpty()) {
            return new ArrayList<Star>();
        } else {
            if (starGiven && k == starList.size()) {
                k = k - 1;
            }
            return nearestStarList.subList(0, Math.min(k, starList.size()));
        }
    }

    /**
     * Method to return a list of the k stars which are closest to a given star.
     * @param k the number of stars to be searched for
     * @param name the name of the star of interest
     * @return a list of stars, the k stars nearest the input star
     */
    private List<Star> naiveNeighborsName(int k, String name) {
        try {
            Star star = findStar(name);
            return naiveNeighborsCoord(k, star.getX(), star.getY(), star.getZ(), true);
        } catch (StarNotFoundException e) {
            System.out.println("Star " + name + " not found.");
            return new ArrayList<>();
        }
    }

    /**
     * Method to find an input star in a list of stars.
     * @param name the name of the star of interest
     * @return a star, the input star if it is in the list
     * @throws StarNotFoundException thrown if star is not within starList (i.e., no star with the name given is within
     * the data that has been loaded or no file has been loaded)
     */
    private Star findStar(String name) throws StarNotFoundException {

        for (Star star: starList) {
            if (star.getName().equals(name)) {
                return star;
            }
        }
        throw new StarNotFoundException(name);
    }

    /**
     * Method to find the star farthest from a given location within a list of stars
     * @param x a double, the x-coordinate of the position of interest
     * @param y a double, the y-coordinate of the position of interest
     * @param z a double, the z-coordinate of the position of interest
     * @param starSublist a list of stars
     * @return a Star, the maximal-distance star from the input location
     */
    private Star findMax(double x, double y, double z, List<Star> starSublist) {
        double maxDist = 0.0;
        Star maxStar = null;

        for (Star star: starSublist) {
            double dist = euclideanDistance(x, y, z, star);
            if (dist > maxDist) {
                maxDist = dist;
                maxStar = star;
            }
        }

        return maxStar;
    }

    /**
     * Method to calculate the Euclidean distance between a given position and star.
     * @param x a double, the x-coordinate of the position of interest
     * @param y a double, the y-coordinate of the position of interest
     * @param z a double, the z-coordinate of the position of interest
     * @param star a Star, the star of interest
     * @return a double, the distance between the given location and star
     */
    private double euclideanDistance(double x, double y, double z, Star star) {
        return Math.sqrt(Math.pow(x - star.getX(), 2) + Math.pow(y - star.getY(), 2)
                + Math.pow(z - star.getZ(), 2));
    }
}
