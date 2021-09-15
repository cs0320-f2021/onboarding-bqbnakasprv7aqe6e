package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testAdditionLargerNumbers() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  // TODO: add more unit tests of your own
  @Test
  public void testSubtractionLargerNumbers() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(18234, 17234);
    assertEquals(1000, output, 0.01);
  }

  @Test
  public void testAdditionDoubleNegative() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(-294, -2);
    assertEquals(-296, output, 0.01);
  }

  @Test
  public void testSubtractionDoubleNegative() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(-5, -10);
    assertEquals(5, output, 0.01);
  }

  @Test
  public void testAdditionFirstNegative() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(-294, 9);
    assertEquals(-285, output, 0.01);
  }

  @Test
  public void testSubtractionFirstNegative() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(-5, 10);
    assertEquals(-15, output, 0.01);
  }

  @Test
  public void testAdditionSecondNegative() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(294, -9);
    assertEquals(285, output, 0.01);
  }

  @Test
  public void testSubtractionSecondNegative() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(5, -10);
    assertEquals(15, output, 0.01);
  }
  @Test
  public void testAdditionZeros() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(0, 0);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void testSubtractionZeros() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(0, 0);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void testAdditionFirstZero() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(0, 9);
    assertEquals(9, output, 0.01);
  }

  @Test
  public void testSubtractionFirstZero() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(0, 10);
    assertEquals(-10, output, 0.01);
  }

  @Test
  public void testAdditionSecondZero() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(294, 0);
    assertEquals(294, output, 0.01);
  }

  @Test
  public void testSubtractionSecondZero() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.subtract(5, 0);
    assertEquals(5, output, 0.01);
  }
}
