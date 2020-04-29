package com.example.geometry.data;

import javax.naming.OperationNotSupportedException;
import java.io.Serializable;

public interface Shape extends Serializable, Cloneable {

    double EPS = 1e-9; // Epsilon error for comparing floating points

    /**
     * Gets the Euclidean distance of this shape to a given point.
     *
     * @param p the point
     * @return The Euclidean distance between this object and the given point
     */
    double distanceTo(Point p) throws OperationNotSupportedException;

    /**
     * Check for intersection of this shape with the given shape
     *
     * @param s The other shape to test for intersection with this shape
     * @return <code>true</code> if this shape intersects with s; <code>false</code> otherwise.
     */
    boolean isIntersected(final Shape s) throws OperationNotSupportedException;

    /**
     * Clones this shape
     *
     * @return A new object which is a copy of this shape
     */
    Shape clone();

    boolean contains(Shape shape) throws OperationNotSupportedException;

}
