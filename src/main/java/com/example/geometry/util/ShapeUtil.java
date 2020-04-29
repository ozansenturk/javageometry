package com.example.geometry.util;

import com.example.geometry.data.LineSegment;
import com.example.geometry.data.Point;
import com.example.geometry.data.Shape;
import com.example.geometry.data.Square;

import javax.naming.OperationNotSupportedException;

public class ShapeUtil {

    public static boolean squarePointIntersection(Point point, Square square) {
        double minDiffX = square.minPoint.x - point.x;
        double minDiffY = square.minPoint.y - point.y;
        double maxDiffX = square.maxPoint.x - point.x;
        double maxDiffY = square.maxPoint.y - point.y;

        if (minDiffX >= Shape.EPS) // to the left of rect.minPoint
            return false;

        if (minDiffY >= Shape.EPS) // to the bottom of rect.minPoint
            return false;

        if (maxDiffX <= -Shape.EPS) // to the right of rect.maxPoint
            return false;

        return !(maxDiffY <= -Shape.EPS);
    }

    public static boolean squareLineSegmentIntersection(LineSegment lineSegment, Square square) throws OperationNotSupportedException {
        Point p1 = new Point(square.maxPoint.x, square.minPoint.y); // bottom-right point
        Point p2 = new Point(square.minPoint.x, square.maxPoint.y); // upper-left point

        /* Edges of a rectangle */
        LineSegment[] rectEdges = new LineSegment[]{
                new LineSegment(square.minPoint, p1),
                new LineSegment(p1, square.maxPoint),
                new LineSegment(square.maxPoint, p2),
                new LineSegment(p2, square.minPoint)
        };


        /* Iterate over four edges of rectangle and check for edge intersection with
         * line segment
         */
        for (LineSegment edge : rectEdges)
            if (edge.isIntersected(lineSegment))
                return true;

        /* No Edges intersection thus check if the line is in the rectangle */
        return squarePointIntersection(lineSegment.p1, square)
                || squarePointIntersection(lineSegment.p2, square);
    }

    public static boolean lineSegmentPointIntersection(Point point, LineSegment lineSegment) {
        /* Check that point is on line */
        if (lineSegment.b > Shape.EPS) {
            double y = -(lineSegment.a * point.x) - lineSegment.c;

            if (Math.abs(y - point.y) > Shape.EPS) // point is not on the line
                return false;

        } else {
            if (Math.abs(point.x + lineSegment.c) > Shape.EPS) // vertical line check x that x != -c
                return false;
        }


        /* Check if point is between p1 and p2 */
        double ab = lineSegment.p1.distanceTo(lineSegment.p2);
        double ap = lineSegment.p1.distanceTo(point);
        double pb = point.distanceTo(lineSegment.p2);

        /* point between a and b if dist(a,p) + dist(p, b) == dist(a,b)*/
        return Math.abs(ab - (ap + pb)) < Shape.EPS;
    }
}
