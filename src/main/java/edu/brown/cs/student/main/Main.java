package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param arguments An array of command line arguments
   */
  public static void main(String[] arguments) {
    new Main(arguments).run();
  }

  /**
   * Field for args, an Array of strings.
   */
  private String[] args;
  /**
   * Field for starList, the list of Star objects in which data read in will be stored.
   */
  private List<Star> starList;

  /**
   * Constructor for Main.
   * @param arguments
   */
  Main(String[] arguments) {
    this.args = arguments;
    this.starList = new ArrayList<Star>();
  }

  /**
   * Method to run REPL.
   */
  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // TO DO: Add your REPL here!
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;

      while ((input = br.readLine()) != null) {
//        System.out.println(starList.size());

        try {
          input = input.trim();
          String[] arguments = input.split(" ");
          // TO DO: complete your REPL by adding commands for addition "add" and subtraction
          // "subtract"

//          System.out.println(arguments[0]);

          switch (arguments[0]) {
            case "stars":
              if (arguments.length == 2) {
                stars(arguments[1]);
              } else {
                System.out.println("ERROR: The stars method requires a valid filepath as input.");
              }
              break;
            case "naive_neighbors":
              if (Integer.parseInt(arguments[1]) <= 0) {
                throw new Exception("ERROR: k must be greater than zero.");
              }

              if (starList.isEmpty()) {
                throw new Exception("ERROR: No stars data provided.");
              }

              List<Star> nearestStars;

              nearestStars = getStarList(input, arguments);

              if (nearestStars.isEmpty()) {
                System.out.println("There are no nearest stars.");
              } else if (Integer.parseInt(arguments[1]) > starList.size()) {
//              System.out.println("Oh dear! There are only " + starList.size() + " stars.");
//              System.out.println("They are: ");
                printStars(nearestStars);
              } else {
//      System.out.println("The " + Integer.parseInt(arguments[1]) + " nearest stars are: ");
                printStars(nearestStars);
              }
              break;
            case "add":
              if (arguments.length == 3) {
                add(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2]));
              } else {
                System.out.println("ERROR:");
              }
              break;
            case "subtract":
              if (arguments.length == 3) {
                subtract(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2]));
              } else {
                System.out.println("ERROR:");
              }
              break;
            default:
              Double.parseDouble(arguments[0]);
              System.out.println(arguments[0]);
              break;
          }
        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR: Invalid input for REPL");
      }
  }

  private List<Star> getStarList(String input, String[] arguments) {
    List<Star> nearestStars;
    if (arguments.length == 5 && !input.contains("\"")) {
      nearestStars = naiveNeighborsCoord(Integer.parseInt(arguments[1]), Double.parseDouble(arguments[2]),
              Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]), false);
    } else {
      String name = input.split("\"")[1];
//              System.out.println(name);
      nearestStars = naiveNeighborsName(Integer.parseInt(arguments[1]), name);
    }
    return nearestStars;
  }

  /**
   * Method to print stars.
   * @param stars a list of Star objects to be printed
   */
  private void printStars(List<Star> stars) {
    for (Star star: stars) {
      System.out.println(star.getId());
    }
  }

  /**
   * Method to load in star data.
   * @param filepath a String, the path to a file containing star data
   * @return a list of the Stars in the input file
   */
  private List<Star> stars(String filepath) {
//    System.out.println("stars entered");

    try {
      BufferedReader br = new BufferedReader(new FileReader(filepath, StandardCharsets.UTF_8));
      Stream<String> allRows = br.lines();

      List<String> processedRows = new ArrayList<>();

      for (String row: (Iterable<String>) allRows::iterator) {
        processedRows.add(row);

        // check for header format
      }

      for (String row: processedRows.subList(1, processedRows.size())) {
        String[] arguments = row.split(",");

//        System.out.println(arguments[0]);

        Star star = new Star(Integer.parseInt(arguments[0]), arguments[1],
                Double.parseDouble(arguments[2]), Double.parseDouble(arguments[3]),
                Double.parseDouble(arguments[4]));

//        System.out.println(star.getName());
//        System.out.println(star.getX());
//        System.out.println(star.getY());
//        System.out.println(star.getZ());

        starList.add(star);
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
    double maxDist = 0.0;
    Star maxStar = null;
    int c = 0;

    for (Star star: starList) {
      double dist = euclideanDistance(x, y, z, star);
      if (!(nameInput && dist == 0)) {
        if (c <= k) {
          nearestStarList.add(star);
//        System.out.println(c);
//        System.out.println(maxDist);
//        System.out.println(maxStar.getId());
          if (dist > maxDist) {
            maxDist = dist;
            maxStar = star;
//          System.out.println(c);
//          System.out.println(maxDist);
//          System.out.println(maxStar.getId());
          }
        } else if (dist < maxDist) {
          nearestStarList.add(star);
          nearestStarList.remove(maxStar);
          maxStar = findMax(x, y, z, nearestStarList);
          maxDist = euclideanDistance(x, y, z, maxStar);
//        System.out.println(c);
//        System.out.println(maxDist);
//        System.out.println(maxStar.getId());
        } else if (dist == maxDist) {
          Random random = new Random();
          if (random.nextBoolean()) {
            nearestStarList.add(star);
            nearestStarList.remove(maxStar);
            maxStar = star;
          }
//        System.out.println(c);
//        System.out.println(maxDist);
//        System.out.println(maxStar.getId());
        }
      }
      c++;
    }

    if (nearestStarList.isEmpty()) {
      return new ArrayList<Star>();
    } else {
      return nearestStarList.subList(0, Math.min(k, starList.size()));
    }
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

      // What should happen here?
      return new ArrayList<>();
    }
//      System.out.println("naiveNeighborsName entered, star found");
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

  /**
   * Method to add two input numbers
   * @param n1 a double to be added
   * @param n2 a double to be added
   */
  private void add(double n1, double n2) {
    MathBot mb = new MathBot();
    System.out.println(mb.add(n1, n2));
  }

  /**
   * Method to subtract two numbers
   * @param n1 a double, the minuend to be subtracted from
   * @param n2 a double, the subtrahend to subtract from the minuend
   */
  private void subtract(double n1, double n2) {
    MathBot mb = new MathBot();
    System.out.println(mb.subtract(n1, n2));
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
