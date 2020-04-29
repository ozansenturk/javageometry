package com.example.geometry.data;


import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SquareTest {


    @Test
    public void testSquareContiainsPointWithFloatCoordinates() throws OperationNotSupportedException {
        Point point1 = new Point(30.4259967, 30.7862083);
        Point point2 = new Point(32.2359967, 32.5661083);
        Square Square = new Square(point1.clone(), point2.clone());

        Point point3 = new Point(31.0, 31.0);

        assertTrue(Square.contains(point1),
                "Square doesn't contain point with float coordinate");
        assertTrue(Square.contains(point2),
                "Square doesn't contain point with float coordinate");
        assertTrue(Square.contains(point3),
                "Square doesn't contain point with float coordinate");

        Point point4 = new Point(33.0, 33.0);
        Point point5 = new Point(30.0, 30.0);

        assertFalse(Square.contains(point4),
                "Square does contain point with float coordinate");
        assertFalse(Square.contains(point5),
                "Square does contain point with float coordinate");

    }

    @Test
    public void testSquareIntersectSquare() throws OperationNotSupportedException {
        Point point1 = new Point(30.4259967, 30.7862083);
        Point point2 = new Point(32.2359967, 32.5661083);
        Square Square = new Square(point1.clone(), point2.clone());

        Point point3 = new Point(31.0, 31.0);
        assertTrue(Square.isIntersected(point1),
                "Square doesn't intersect point with float coordinate");
        assertTrue(Square.isIntersected(point2),
                "Square doesn't intersect point with float coordinate");
        assertTrue(Square.isIntersected(point3),
                "Square doesn't intersect point with float coordinate");


        Point point4 = new Point(33.0, 33.0);
        Point point5 = new Point(30.0, 30.0);

        assertFalse(Square.isIntersected(point4),
                "Square does intersect point with float coordinate");
        assertFalse(Square.isIntersected(point5),
                "Square does intersect point with float coordinate");

    }

    @Test
    public void testContainsPoint() throws OperationNotSupportedException {
        Square square = new Square(2.0, 2.0, 4.0, 4.0);

        assertFalse(square.contains(new Point(1, 2)),
                "Square does contain point");
        assertFalse(square.contains(new Point(1, 3)),
                "Square does contain point");
        assertFalse(square.contains(new Point(1, 4)),
                "Square does contain point");

        // to the bottom of minPoint
        assertFalse(square.contains(new Point(2, 1)),
                "Square does contain point");
        assertFalse(square.contains(new Point(1, 4)),
                "Square does contain point");
        assertFalse(square.contains(new Point(5, 1)),
                "Square does contain point");

        // to the right of maxPoint
        assertFalse(square.contains(new Point(5, 4)),
                "Square does contain point");
        assertFalse(square.contains(new Point(5, 3)),
                "Square does contain point");
        assertFalse(square.contains(new Point(5, 2)),
                "Square does contain point");


        // above of maxPoint
        assertFalse(square.contains(new Point(2, 5)),
                "Square does contain point");
        assertFalse(square.contains(new Point(3, 6)),
                "Square does contain point");
        assertFalse(square.contains(new Point(3.2, 4.1)),
                "Square does contain point");


        // above maxPoint
        assertFalse(square.contains(new Point(4, 5)),
                "Square does contain point");
        assertFalse(square.contains(new Point(3, 5)),
                "Square does contain point");
        assertFalse(square.contains(new Point(6, 5)),
                "Square does contain point");

        // On bottom border
        assertTrue(square.contains(new Point(3, 2)),
                "Square does not contain point");
        assertTrue(square.contains(new Point(4, 2)),
                "Square does not contain point");

        // On left border
        assertTrue(square.contains(new Point(2, 3)),
                "Square does not contain point");
        assertTrue(square.contains(new Point(2, 4)),
                "Square does not contain point");

        // On upper border
        assertTrue(square.contains(new Point(3, 4)),
                "Square does not contain point");

        // On right border
        assertTrue(square.contains(new Point(4, 3)),
                "Square does not contain point");

        // same minPoint
        assertTrue(square.contains(new Point(2, 2)),
                "Square does not contain point");

        // same maxPoint
        assertTrue(square.contains(new Point(4, 4)),
                "Square does not contain point");

        // inside Square
        assertTrue(square.contains(new Point(3, 3)),
                "Square does not contain point");
        assertTrue(square.contains(new Point(2.5, 2.4)),
                "Square does not contain point");
        assertTrue(square.contains(new Point(3.1, 2.1)),
                "Square does not contain point");
        assertTrue(square.contains(new Point(3.9, 3.9)),
                "Square does not contain point");
        assertTrue(square.contains(new Point(2.001, 2.0001)),
                "Square does not contain point");

        // outside Square due to high y-coordinate
        assertFalse(square.contains(new Point(3, 5)),
                "Square does contain point");
        assertFalse(square.contains(new Point(3, 4.2111)),
                "Square does contain point");


        // outside Square due to low y-coordinate
        assertFalse(square.contains(new Point(3, -5)),
                "Square does contain point");
        assertFalse(square.contains(new Point(3, 1.99121)),
                "Square does contain point");


        // outside Square due to high x-coordinate
        assertFalse(square.contains(new Point(6, 3)),
                "Square does contain point");
        assertFalse(square.contains(new Point(4.12222, 3)),
                "Square does contain point");

        // outside Square due to low x-coordinate
        assertFalse(square.contains(new Point(1, 3)),
                "Square does contain point");
        assertFalse(square.contains(new Point(1.999221, 3)),
                "Square does contain point");

    }


    @Test
    public void testEdgeIntersectionTest() throws OperationNotSupportedException {
        Square range = new Square(3.0, 2.0, 6.0, 4.0);
        Square square = new Square(2.0, 4.0, 6.0, 6.0);


        assertTrue(range.isIntersected(square),
                "Square does not intersect with square");
        assertFalse(range.contains(square),
                "Square does contain square");

        assertTrue(range.isEdgeIntersection(square),
                "Square edge doesn't intersect with other square");


        Point point = new Point(6.0, 4.0);

        assertTrue(range.isIntersected(point),
                "Square doesn't intersect with point");
        assertTrue(square.isIntersected(point),
                "2nd Square doesn't intersect with point");

        Square square3 = new Square(2, 4, 5, 4);

        assertTrue(range.isIntersected(square3),
                "Square does not intersect with square");
        assertFalse(range.contains(square3),
                "Square does contain square");

        assertTrue(range.isEdgeIntersection(square3),
                "Square edge doesn't intersect with other square");

        assertTrue(square3.isIntersected(range),
                "Square does not intersect with square");
        assertFalse(square3.contains(range),
                "Square does contain square");

        assertTrue(square3.isEdgeIntersection(range),
                "Square edge doesn't intersect with other square");

    }


    @Test
    public void testNoIntersectionWithSquare() throws OperationNotSupportedException {
        Square square1 = new Square(3, 2, 6, 4);
        Square square2 = new Square(2, 5, 6, 6);

        assertFalse(square1.isIntersected(square2),
                "Square 1 does intersect with square 2");
        assertFalse(square1.contains(square2),
                "Square 1 does contain with square 2");
        assertFalse(square1.isEdgeIntersection(square2),
                "");

    }

    @Test
    public void testFullIntersectionWithSquare() throws OperationNotSupportedException {
        Square square1 = new Square(3, 2, 6, 4);
        Square square2 = new Square(3, 2, 6, 4);

        assertTrue(square1.isIntersected(square2),
                "Square edge doesn't intersect with other square");
        assertTrue(square1.contains(square2),
                "Square edge doesn't intersect with other square");
        assertTrue(square1.isEdgeIntersection(square2),
                "Square edge doesn't intersect with other square");

        assertTrue(square2.isIntersected(square1),
                "Square edge doesn't intersect with other square");
        assertTrue(square2.contains(square1),
                "Square edge doesn't intersect with other square");
        assertTrue(square2.isEdgeIntersection(square1),
                "Square edge doesn't intersect with other square");

    }



}
