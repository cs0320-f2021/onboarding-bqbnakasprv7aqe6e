package edu.brown.cs.student.main;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

public class NaiveNeighborsTest {

    @Test
    public void testFindNearestNeighbors() throws StarNotFoundException {
        // distances: 0-1: 79.03170249969311; 0-2: 75.37360280628755; 0-3: 77.77731031605553
        Star posStar = new Star(0, "Timitus Nelsauri", 23.5, 2.5, 74.1);
        Star negStar = new Star(1, "n", -1.4, -5.3, -0.5);
        Star mixStar = new Star(2, "m", 9.4, -24.1, 5.0);
        Star zeroStar = new Star(3, "z", 0, 0, 0);
        List<Star> starList = new ArrayList<Star>();
        starList.add(posStar);
        starList.add(negStar);
        starList.add(mixStar);
        starList.add(zeroStar);
        NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);
        List<Star> k3_starList_0_named = new ArrayList<Star>();
        k3_starList_0_named.add(negStar);
        k3_starList_0_named.add(mixStar);
        k3_starList_0_named.add(zeroStar);
        List<Star> k3_starList_0_unnamed = new ArrayList<Star>();
        k3_starList_0_unnamed.add(posStar);
        k3_starList_0_unnamed.add(negStar);
        k3_starList_0_unnamed.add(zeroStar);
        List<Star> k1_starList_0_named = new ArrayList<Star>();
        k1_starList_0_named.add(mixStar);
        List<Star> emptyList = new ArrayList<Star>();
        NaiveNeighbors emptyNeighbors = new NaiveNeighbors(emptyList);

        assertEquals(k3_starList_0_named,
                naiveNeighbors.findNearestNeighbors("naive_neighbors 3 \"Timitus Nelsauri\""));
        assertEquals(k1_starList_0_named,
                naiveNeighbors.findNearestNeighbors("naive_neighbors 1 \"Timitus Nelsauri\""));
        assertThrows(Exception.class, () -> emptyNeighbors.findNearestNeighbors("naive_neighbors 3 \"Timitus Nelsauri\""));
    }

    @Test
    public void testNearestNeighborsCoord() {
        // distances: 0-1: 79.03170249969311; 0-2: 75.37360280628755; 0-3: 77.77731031605553
        Star posStar = new Star(0, "Timitus Nelsauri", 23.5, 2.5, 74.1);
        Star negStar = new Star(1, "n", -1.4, -5.3, -0.5);
        Star mixStar = new Star(2, "m", 9.4, -24.1, 5.0);
        Star zeroStar = new Star(3, "z", 0, 0, 0);
        List<Star> starList = new ArrayList<Star>();
        starList.add(posStar);
        starList.add(negStar);
        starList.add(mixStar);
        starList.add(zeroStar);
        NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);
        List<Star> k3_starList_0_named = new ArrayList<Star>();
        k3_starList_0_named.add(negStar);
        k3_starList_0_named.add(mixStar);
        k3_starList_0_named.add(zeroStar);
        List<Star> k3_starList_0_unnamed = new ArrayList<Star>();
        k3_starList_0_unnamed.add(posStar);
        k3_starList_0_unnamed.add(mixStar);
        k3_starList_0_unnamed.add(zeroStar);
        List<Star> k1_starList_0_named = new ArrayList<Star>();
        k1_starList_0_named.add(mixStar);
        List<Star> k1_starList_0_unnamed = new ArrayList<Star>();
        k1_starList_0_unnamed.add(posStar);

        assertEquals(k3_starList_0_unnamed,
                naiveNeighbors.naiveNeighborsCoord(3, 23.5, 2.5, 74.1, false));
        assertEquals(k3_starList_0_named,
                naiveNeighbors.naiveNeighborsCoord(3, 23.5, 2.5, 74.1, true));
        assertEquals(k1_starList_0_unnamed,
                naiveNeighbors.naiveNeighborsCoord(1, 23.5, 2.5, 74.1, false));
        assertEquals(k1_starList_0_named,
                naiveNeighbors.naiveNeighborsCoord(1, 23.5, 2.5, 74.1, true));
    }

    @Test
    public void testNearestNeighborsName() throws StarNotFoundException {
        // distances: 0-1: 79.03170249969311; 0-2: 75.37360280628755; 0-3: 77.77731031605553
        Star posStar = new Star(0, "Timitus Nelsauri", 23.5, 2.5, 74.1);
        Star negStar = new Star(1, "n", -1.4, -5.3, -0.5);
        Star mixStar = new Star(2, "m", 9.4, -24.1, 5.0);
        Star zeroStar = new Star(3, "z", 0, 0, 0);
        List<Star> starList = new ArrayList<Star>();
        starList.add(posStar);
        starList.add(negStar);
        starList.add(mixStar);
        starList.add(zeroStar);
        NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);
        List<Star> k3_starList_0_named = new ArrayList<Star>();
        k3_starList_0_named.add(negStar);
        k3_starList_0_named.add(mixStar);
        k3_starList_0_named.add(zeroStar);
        List<Star> k1_starList_0_named = new ArrayList<Star>();
        k1_starList_0_named.add(mixStar);

        assertEquals(k3_starList_0_named,
                naiveNeighbors.naiveNeighborsName(3,"Timitus Nelsauri"));
        assertEquals(k1_starList_0_named,
                naiveNeighbors.naiveNeighborsName(1,"Timitus Nelsauri"));
    }

    @Test
    public void testFindStar() throws StarNotFoundException {
        Star posStar = new Star(0, "Timitus Nelsauri", 23.5, 2.5, 74.1);
        Star negStar = new Star(1, "n", -1.4, -5.3, -0.5);
        Star mixStar = new Star(2, "m", 9.4, -24.1, 5.0);
        Star zeroStar = new Star(3, "z", 0, 0, 0);

        List<Star> starList = new ArrayList<Star>();
        starList.add(posStar);
        starList.add(negStar);
        starList.add(mixStar);
        starList.add(zeroStar);
        NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);

        assertEquals(posStar, naiveNeighbors.findStar("Timitus Nelsauri"));
        assertEquals(negStar, naiveNeighbors.findStar("n"));
        assertEquals(mixStar, naiveNeighbors.findStar("m"));
        assertEquals(zeroStar, naiveNeighbors.findStar("z"));
        assertThrows(StarNotFoundException.class, () -> naiveNeighbors.findStar("Not a Star"));
    }

    @Test
    public void testFindMax() {
        Star posStar = new Star(0, "Timitus Nelsauri", 23.5, 2.5, 74.1);
        Star negStar = new Star(1, "n", -1.4, -5.3, -0.5);
        Star mixStar = new Star(2, "m", 9.4, -24.1, 5.0);
        Star zeroStar = new Star(3, "z", 0, 0, 0);

        List<Star> starList = new ArrayList<Star>();
        starList.add(posStar);
        starList.add(negStar);
        starList.add(mixStar);
        starList.add(zeroStar);
        NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);

        assertEquals(negStar, naiveNeighbors.findMax(23.5, 2.5, 74.1, starList));
    }

    @Test
    public void testEuclidianDistance() {
        Star posStar = new Star(0, "Timitus Nelsauri", 23.5, 2.5, 74.1);
        Star negStar = new Star(1, "n", -1.4, -5.3, -0.5);
        Star mixStar = new Star(2, "m", 9.4, -24.1, 5.0);
        Star zeroStar = new Star(3, "z", 0, 0, 0);

        List<Star> starList = new ArrayList<Star>();
        starList.add(posStar);
        starList.add(negStar);
        starList.add(mixStar);
        starList.add(zeroStar);
        NaiveNeighbors naiveNeighbors = new NaiveNeighbors(starList);

        assertEquals(79.03170249969311,
                naiveNeighbors.euclideanDistance(-1.4, -5.3, -0.5, posStar), 0.01);
        assertEquals(75.37360280628755,
                naiveNeighbors.euclideanDistance(9.4, -24.1, 5.0, posStar), 0.01);
        assertEquals(77.77731031605553,
                naiveNeighbors.euclideanDistance(0, 0, 0, posStar), 0.01);
    }
}
