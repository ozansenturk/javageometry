package com.example.geometry.data;

import com.example.geometry.util.ShapeUtil;

import javax.naming.OperationNotSupportedException;

public class Point implements Shape, Comparable<Point>{

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(Double.MIN_VALUE, Double.MIN_VALUE);
    }

    public Point(Point s) {
        this.x = s.x;
        this.y = s.y;
    }

    @Override
    public double distanceTo(Point p) {
        double dx = p.x - this.x;
        double dy = p.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }

    @Override
    public Point getCenterPoint() {
        return this;
    }


    @Override
    public boolean isIntersected(Shape shape) throws OperationNotSupportedException {
        if (shape instanceof Point)
            return this.equals(shape);

        if (shape instanceof Square)
            return ShapeUtil.squarePointIntersection(this, (Square) shape);

        throw new OperationNotSupportedException("isIntersected operation in Point " +
                "is not supported for " + shape.getClass());
    }

    @Override
    public String toString() {
        return "Point: (" + x + "," + y + ")";
    }

    @Override
    public int compareTo(Point pt2) {
        /* Sort on x then y for resolving ties*/
        if (this.x - pt2.x < -EPS)
            return -1;

        if (this.x - pt2.x > EPS)
            return 1;

        if (this.y - pt2.y < -EPS)
            return -1;

        if (this.y - pt2.y > EPS)
            return 1;

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point))
            return false;

        Point point = (Point) obj;
        double xDiff = this.x - point.x;
        double yDiff = this.y - point.y;

        return xDiff > -EPS && yDiff > -EPS
                && xDiff < EPS && yDiff < EPS;
    }

    public Point translate(Vector vector) {
        return new Point(this.x + vector.x, this.y + vector.y);
    }

    @Override
    public boolean contains(Shape shape) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Check if point contains a shape is a fatal error");
    }
}
