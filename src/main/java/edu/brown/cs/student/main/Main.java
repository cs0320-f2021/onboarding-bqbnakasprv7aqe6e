package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  private String[] args;

  private Main(String[] arguments) {
    this.args = args;
  }

  @SuppressWarnings("checkstyle:TodoComment")
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
        try {
          input = input.trim();
          String[] arguments = input.split(" ");
          // TO DO: complete your REPL by adding commands for addition "add" and subtraction
          // "subtract"
          if (arguments[0].equals("stars") && arguments.length == 2) {
            stars(arguments[1]);
          } else if (arguments[0].equals("naive_neighbors")) {
            if (arguments.length == 5) {
              naiveNeighborsCoord(Integer.parseInt(arguments[1]), Double.parseDouble(arguments[2]),
                  Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]));
            } else if (arguments.length == 3) {
              naiveNeighborsName(Integer.parseInt(arguments[1]), arguments[2]);
            }
          }

          if (arguments[0].equals("add") && arguments.length == 3) {
            add(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2]));
          } else if (arguments[0].equals("subtract") && arguments.length == 3) {
            subtract(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2]));
          } else {
            System.out.println(arguments[0]);
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

  private List<Star> stars(String filepath) {
    // Should type be ArrayList or List?
    List<Star> starList = new ArrayList<Star>();
    // for each star
      // add new star object to list
    try (FileReader fr = new FileReader(filepath); BufferedReader br = new BufferedReader(fr)) {
      String input;
      while ((input = br.readLine()) != null) {
        input = input.trim();
        String[] arguments = input.split(" ");

        Star star = new Star(Integer.parseInt(arguments[0]), arguments[1],
              Double.parseDouble(arguments[2]), Double.parseDouble(arguments[3]),
              Double.parseDouble(arguments[4]));

        starList.add(star);
      }
      return starList;
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: " + e.getMessage());
      return new ArrayList<>();
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
      return new ArrayList<>();
    }
  }

  private List<String> naiveNeighborsCoord(int k, double x, double y, double z) {
    // Should this be an ArrayList (in signature and actual usage?)
    return new ArrayList<>();
  }

  private List<String> naiveNeighborsName(int k, String name) {
    // Should this be an ArrayList (in signature and actual usage?)
    Star star = findStar(name);
    return new ArrayList<>();
  }

  private Star findStar(String name) {
    return new Star(1, "", 1, 1, 1);
  }

  private double euclidianDistance(double x, double y, double z, Star star) {
    return Math.sqrt(Math.pow(x - star.getX(), 2) + Math.pow(y - star.getY(), 2)
        + Math.pow(z - star.getZ(), 2));
  }

  private void add(double n1, double n2) {
    MathBot mb = new MathBot();
    System.out.println(mb.add(n1, n2));
  }

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
