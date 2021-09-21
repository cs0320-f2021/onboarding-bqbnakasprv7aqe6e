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

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;

      while ((input = br.readLine()) != null) {

        try {
          input = input.trim();
          String[] arguments = input.split(" ");

          switch (arguments[0]) {
            case "stars":
              if (arguments.length == 2) {
                CSVParser p = new CSVParser();
                starList = p.parseStars(arguments[1]);
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

              NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);
              List<Star> nearestStars = naiveNeighbors.getStarList(input, arguments);

              if (nearestStars.isEmpty()) {
                System.out.println("There are no nearest stars.");
              } else {
                naiveNeighbors.printStars(nearestStars);
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
