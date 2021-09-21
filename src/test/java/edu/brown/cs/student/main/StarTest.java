package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StarTest {

    @Test
    public void testGetId() {
        Star star = new Star(0, "Timitus Nelsauri", 23.5, -2.5, 74.1);
        int id = star.getId();
        assertEquals(0, id, 0.01);
    }

    @Test
    public void testGetName() {
        Star star = new Star(0, "Timitus Nelsauri", 23.5, -2.5, 74.1);
        String name = star.getName();
        assertEquals("Timitus Nelsauri", name);
    }

    @Test
    public void testGetX() {
        Star star = new Star(0, "Timitus Nelsauri", 23.5, -2.5, 74.1);
        double x = star.getX();
        assertEquals(23.5, x, 0.01);
    }

    @Test
    public void testGetY() {
        Star star = new Star(0, "Timitus Nelsauri", 23.5, -2.5, 74.1);
        double y = star.getY();
        assertEquals(-2.5, y, 0.01);
    }

    @Test
    public void testGetZ() {
        Star star = new Star(0, "Timitus Nelsauri", 23.5, -2.5, 74.1);
        double z = star.getZ();
        assertEquals(74.1, z, 0.01);
    }
}
