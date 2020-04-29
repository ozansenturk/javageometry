package com.example.geometry.data;

public class Vector {

    public double x, y;

    public Vector(double a, double b) {
        x = a;
        y = b;
    }

    public Vector(Point a, Point b) {
        this(b.x - a.x, b.y - a.y);
    }

    public Vector scale(double s) {
        return new Vector(x * s, y * s);
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y;
    }

    public double cross(Vector v) {
        return x * v.y - y * v.x;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector unitVector() {
        double magnitude = magnitude();
        return new Vector(this.x / magnitude, this.y / magnitude);
    }

    public double norm2() {
        return x * x + y * y;
    }
}
