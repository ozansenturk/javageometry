package com.example.geometry.data;

import com.example.geometry.util.ShapeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.naming.OperationNotSupportedException;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Square implements Shape, Comparable<Square>{

    @NotNull
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    private LocalDateTime modified;

    public Square(Point minPoint, Point maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public Square(double x1, double y1, double x2, double y2) {
        this.minPoint = new Point(x1, y1);
        this.maxPoint = new Point(x2, y2);
    }

    public Point getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(Point minPoint) {
        this.minPoint = minPoint;
    }

    public Point getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Point maxPoint) {
        this.maxPoint = maxPoint;
    }

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="minPoint")
    public Point minPoint;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="maxPoint")
    public Point maxPoint; // point with maximum coordinates

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }
    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }

    @Override
    public double distanceTo(Point p) throws OperationNotSupportedException {

        if (containsPoints(p)) // point inside square
            return 0;

        Point p1 = new Point(this.maxPoint.x, this.minPoint.y); // bottom-right point
        Point p2 = new Point(this.minPoint.x, this.maxPoint.y); // upper-left point

        /* Edges of a rectangle */
        LineSegment[] rectEdges = new LineSegment[]{
                new LineSegment(this.minPoint, p1),
                new LineSegment(p1, this.maxPoint),
                new LineSegment(this.maxPoint, p2),
                new LineSegment(p2, this.minPoint)
        };

        double distance = Double.MAX_VALUE;

        for (LineSegment edge : rectEdges)
            distance = Math.min(distance, edge.distanceTo(p));

        return distance;
    }

    @Override
    public Square clone() {

        return new Square(minPoint.x, minPoint.y, maxPoint.x, maxPoint.y);
    }


    @Override
    public boolean isIntersected(Shape shape) throws OperationNotSupportedException {


        if (shape instanceof Point)
            return ShapeUtil.squarePointIntersection((Point) shape, this);


        if (shape instanceof Square) {
            Square rect = (Square) shape;

            /* part of one rectangle is inside the other one */
            return this.maxPoint.x + EPS > rect.minPoint.x // this.maxPoint.x >= rect.minPoint.x
                    && this.maxPoint.y + EPS > rect.minPoint.y // this.maxPoint.y >= rect.minPoint.y
                    && rect.maxPoint.x + EPS > this.minPoint.x // this.minPoint.x <= rect.maxPoint.x
                    && rect.maxPoint.y + EPS > this.minPoint.y; // this.minPoint.y <= rect.maxPoint.y
        }

        if (shape instanceof LineSegment)
            return ShapeUtil.squareLineSegmentIntersection((LineSegment) shape, this);


        throw new OperationNotSupportedException("Contains operation in Rectangle does not support " + shape.getClass());
    }


    @Override
    public Point getCenterPoint() {
        return null;
    }

    @Override
    public int compareTo(Square square) {

        // Sort by minPoint's x then y
        if (this.minPoint.x - square.minPoint.x < -EPS)
            return -1;
        if (this.minPoint.x - square.minPoint.x > EPS)
            return 1;
        if (this.minPoint.y - square.minPoint.y < -EPS)
            return -1;
        if (this.minPoint.y - square.minPoint.y > EPS)
            return 1;

        // Sort by maxPoint's x then y
        if (this.maxPoint.x - square.maxPoint.x < -EPS)
            return -1;
        if (this.maxPoint.x - square.maxPoint.x > EPS)
            return 1;
        if (this.maxPoint.y - square.maxPoint.y < -EPS)
            return -1;
        if (this.maxPoint.y - square.maxPoint.y > EPS)
            return 1;

        return 0;
    }

    /**
     * Iterate over points and check if that all points are inside the square
     */
    private boolean containsPoints(Point... points) throws OperationNotSupportedException {
        for (Point point : points)
            if (!this.isIntersected(point))
                return false;

        return true;
    }

    @Override
    public boolean contains(Shape shape) throws OperationNotSupportedException {

        if (shape instanceof Point)
            return ShapeUtil.squarePointIntersection((Point) shape, this);

        if (shape instanceof Square)
            return containsPoints(((Square) shape).minPoint,
                    ((Square) shape).maxPoint);


        /* For a rectangle to contain a line segment,
         * the two points of line segment should be inside the rectangle
         */
        if (shape instanceof LineSegment)
            return containsPoints(((LineSegment) shape).p1, ((LineSegment) shape).p2);

        throw new OperationNotSupportedException("Contains operation in Square does not support " + shape.getClass());
    }

    public boolean isEdgeIntersection(Shape shape) throws OperationNotSupportedException {

        if (shape instanceof Point)
            return isIntersected(shape);

        if (shape instanceof Square) {
            Square rect = (Square) shape;
            return rect.isIntersected(this.maxPoint)
                    || this.isIntersected(rect.minPoint)
                    || rect.isIntersected(this.minPoint)
                    || this.isIntersected(rect.maxPoint);
        }

        throw new OperationNotSupportedException("isEdgeIntersection operation in Rectangle does not support " + shape.getClass());

    }
}
