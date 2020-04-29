package com.example.geometry.data;


import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LineSegmentTest {

    @Test
    public void testGetCenterPoint() {
        /* Horizontal line */
        LineSegment l1 = new LineSegment(new Point(3, 4), new Point(5, 4));
        Point mid1 = new Point(4, 4);
        assertThat(l1.getCenterPoint()).isEqualTo(mid1);

        /* Vertical LineSegment */
        LineSegment l2 = new LineSegment(new Point(3, 5), new Point(3, 9));
        Point mid2 = new Point(3, 7);
        assertThat(l2.getCenterPoint()).isEqualTo(mid2);

        /* Incline LineSegment (m > 0) */
        LineSegment l3 = new LineSegment(new Point(1, 2), new Point(2, 4));
        Point mid3 = new Point(1.5, 3);
        assertThat(l3.getCenterPoint()).isEqualTo(mid3);

        /* Incline LineSegment (m < 0) */
        LineSegment l4 = new LineSegment(new Point(2, 1), new Point(1, 2));
        Point mid4 = new Point(1.5, 1.5);
        assertThat(l4.getCenterPoint()).isEqualTo(mid4);
    }

    @Test
    public void testToDistance() throws OperationNotSupportedException {
        Point p1 = new Point(3, 3);
        Point a1 = new Point(2, 1);
        Point b1 = new Point(4, 1);
        LineSegment l1 = new LineSegment(a1, b1);
        assertTrue(checkAccuracyOfPointDistanceUsingTrigonometry(l1, p1), "Distance calculation is incorrect for the Line Segment");

        Point p2 = new Point(3, 3);
        Point a2 = new Point(1, 2);
        Point b2 = new Point(1, 4);
        LineSegment l2 = new LineSegment(a2, b2);

        assertTrue(checkAccuracyOfPointDistanceUsingTrigonometry(l2, p2), "Distance calculation is incorrect for the Line Segment");

        Point p3 = new Point(0, 2);
        Point a3 = new Point(1, 2);
        Point b3 = new Point(2, 4);
        LineSegment l3 = new LineSegment(a3, b3);
        assertTrue(checkAccuracyOfPointDistanceUsingTrigonometry(l3, p3), "Distance calculation is incorrect for the Line Segment");


        Point p4 = new Point(1.5, 0);
        Point a4 = new Point(1, 2);
        Point b4 = new Point(2, 4);
        LineSegment l4 = new LineSegment(a4, b4);
        assertTrue(checkAccuracyOfPointDistanceUsingTrigonometry(l4, p4), "Distance calculation is incorrect for the Line Segment");
    }


    @Test
    public void testIntersectionWithPoint() throws OperationNotSupportedException {
        LineSegment line = new LineSegment(new Point(1, 2), new Point(4, 8));

        Point p1 = new Point(2, 4);
        assertTrue(line.isIntersected(p1), "Point not intersected with the line");

        Point p2 = new Point(1.5, 3);
        assertTrue(line.isIntersected(p2), "Point not intersected with the line");

        Point p3 = new Point(1.1234, 2.2468);
        assertTrue(line.isIntersected(p3), "Point not intersected with the line");

        Point p4 = new Point(1.001234, 2.002468);
        assertTrue(line.isIntersected(p4), "Point not intersected with the line");

    }

    @Test
    public void testIntersectionWithLine() throws OperationNotSupportedException {
        /* y= 2x */
        LineSegment line = new LineSegment(new Point(1, 2), new Point(4, 8));

        /* y = -2x+8 */
        LineSegment l1 = new LineSegment(new Point(-1, 10), new Point(4, 0));
        assertTrue(line.isIntersected(l1), "Line not intersected with the line");

        LineSegment l2 = new LineSegment(new Point(6, 1), new Point(3, 6));
        assertTrue(line.isIntersected(l2), "Line not intersected with the line");

        /* y = 8 */
        LineSegment l3 = new LineSegment(new Point(1, 8), new Point(6, 8));
        assertTrue(line.isIntersected(l3), "Line not intersected with the line");

        /* x = 4 */
        LineSegment l4 = new LineSegment(new Point(4, 2), new Point(4, 10));
        assertTrue(line.isIntersected(l4), "Line not intersected with the line");
    }

    @Test
    public void testContainsWithLine() throws OperationNotSupportedException {
        /* y= 2x */
        LineSegment line = new LineSegment(new Point(1, 2), new Point(4, 8));

        /* y = -2x+8 */
        LineSegment l1 = new LineSegment(new Point(-1, 10), new Point(4, 0));
        assertFalse(line.contains(l1), "Line contains the point");

        LineSegment l2 = new LineSegment(new Point(6, 1), new Point(3, 6));
        assertFalse(line.contains(l2), "Line contains the point");

        /* y = 8 */
        LineSegment l3 = new LineSegment(new Point(1, 8), new Point(6, 8));
        assertFalse(line.contains(l3), "Line contains the point");

        /* x = 4 */
        LineSegment l4 = new LineSegment(new Point(4, 2), new Point(4, 10));
        assertFalse(line.contains(l4), "Line contains the point");

    }

    /**
     * LineSegment intersect the left side of rectangle
     */
    @Test
    public void testIntersectionWithRectangleLeftSide() throws OperationNotSupportedException {
        Square square = new Square(3, 2, 6, 4);

        /* horizontal line (m=0) */
        LineSegment line1 = new LineSegment(new Point(2, 3), new Point(4, 3));

        /*  right-inclined LineSegment (m >1) */
        LineSegment line2 = new LineSegment(new Point(2, 2), new Point(4, 3));

        /* left-inclined LineSegment (m <1) */
        LineSegment line3 = new LineSegment(new Point(4, 3), new Point(2, 5));

        assertTrue(line1.isIntersected(square), "Line not intersected with the line1");
        assertTrue(line2.isIntersected(square), "Line not intersected with the line2");
        assertTrue(line3.isIntersected(square), "Line not intersected with the line3");

        /* Should throw exception because line segment does not contain rectangle! */
        assertThrows(OperationNotSupportedException.class, ()-> line1.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line2.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line3.contains(square),"Expected line.contains() to throw, but it didn't");
    }

    /**
     * LineSegment intersect the right side of rectangle
     */
    @Test
    public void testIntersectionWithRectangleRightSide() throws OperationNotSupportedException {
        Square square = new Square(3, 2, 6, 4);

        /* horizontal line (m=0) */
        LineSegment line1 = new LineSegment(new Point(7, 3), new Point(4, 3));
        /*  right-inclined LineSegment (m >1) */
        LineSegment line2 = new LineSegment(new Point(4, 3), new Point(7, 4));
        /* left-inclined LineSegment (m <1) */
        LineSegment line3 = new LineSegment(new Point(7, 1), new Point(3, 3));

        assertTrue(line1.isIntersected(square), "Line not intersected with the line1");
        assertTrue(line2.isIntersected(square), "Line not intersected with the line2");
        assertTrue(line3.isIntersected(square), "Line not intersected with the line3");

        assertThrows(OperationNotSupportedException.class, ()-> line1.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line2.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line3.contains(square),"Expected line.contains() to throw, but it didn't");

    }

    /**
     * LineSegment intersect the upper side of rectangle
     */
    @Test
    public void testIntersectionWithRectangleUpperSide() throws OperationNotSupportedException {
        Square square = new Square(3, 2, 6, 4);

        /* vertical line (m not defined) */
        LineSegment line1 = new LineSegment(new Point(4, 3), new Point(4, 5));

        /*  right-inclined LineSegment (m >1) */
        LineSegment line2 = new LineSegment(new Point(4, 3), new Point(5, 6));

        /* left-inclined LineSegment (m <1) */
        LineSegment line3 = new LineSegment(new Point(5, 3), new Point(4, 6));

        assertTrue(line1.isIntersected(square), "Line not intersected with the line1");
        assertTrue(line2.isIntersected(square), "Line not intersected with the line2");
        assertTrue(line3.isIntersected(square), "Line not intersected with the line3");

        /* Should throw exception because line segment does not contain rectangle! */
        assertThrows(OperationNotSupportedException.class, ()-> line1.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line2.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line3.contains(square),"Expected line.contains() to throw, but it didn't");
    }


    /**
     * LineSegment intersect the bottom side of rectangle
     */
    @Test
    public void testIntersectionWithRectangleBottomSide() throws OperationNotSupportedException {
        Square square = new Square(3, 2, 6, 4);

        /* vertical line (m not defined) */
        LineSegment line1 = new LineSegment(new Point(4, 1), new Point(4, 3));

        /*  right-inclined LineSegment (m >1) */
        LineSegment line2 = new LineSegment(new Point(2, 2), new Point(4, 3));

        /* left-inclined LineSegment (m <1) */
        LineSegment line3 = new LineSegment(new Point(5, 1), new Point(4, 3));

        assertTrue(line1.isIntersected(square), "Line not intersected with the line1");
        assertTrue(line2.isIntersected(square), "Line not intersected with the line2");
        assertTrue(line3.isIntersected(square), "Line not intersected with the line3");

        /* Should throw exception because line segment does not contain rectangle! */
        assertThrows(OperationNotSupportedException.class, ()-> line1.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line2.contains(square),"Expected line.contains() to throw, but it didn't");
        assertThrows(OperationNotSupportedException.class, ()-> line3.contains(square),"Expected line.contains() to throw, but it didn't");
    }

    /**
     * Check accuracy of distance from point to line using Trigonometry
     *
     * @param line represented by two points endPoint e and startPoint s
     * @param p    point to calculate distance from it
     */
    private boolean checkAccuracyOfPointDistanceUsingTrigonometry(LineSegment line, Point p) throws OperationNotSupportedException {
        Vector ep = new Vector(line.p1, p); // Calculate vector ep

        /* Magnitude of ep vector */
        double hypotenuse = ep.magnitude();
        /* Calculate vertical distance between point and line */
        double oppsite = line.distanceTo(p);
        /* Calculate adjacent side using Pythagoras theorem */
        double actualAjacent = Math.sqrt(hypotenuse * hypotenuse - oppsite * oppsite);
        /* Calculate angle between se vector and ep vector */
        double theta = Math.asin(oppsite / hypotenuse);
        /* Calculate adjacent side using trigonometry a = ||h||cos(theta) */
        double expectedAdjacent = hypotenuse * Math.cos(theta);

        return Math.abs(actualAjacent - expectedAdjacent) < Shape.EPS;
    }
}
