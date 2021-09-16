package edu.brown.cs.student.main;

public class Star {

  // from edStem #40 (https://edstem.org/us/courses/13003/discussion/610470)

  private int id;
  private String name;
  private double x;
  private double y;
  private double z;

  public Star(int id, String name, double x, double y, double z) {
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }
}
