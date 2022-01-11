package com.github.mittyrobotics.autonomous;

public class Angle {
    private double radians;

    public Angle() {
        this(0);
    }

    public Angle(double x, double y) {
        this(Math.atan2(y, x));
    }

    public Angle(double radians) {
        this.radians = radians;
    }

    public Angle(Point2D point) {
        this(point.getX(), point.getY());
    }

    public double tan() {
        return Math.tan(radians);
    }

    public double sin() {
        return Math.sin(radians);
    }

    public double cos() {
        return Math.cos(radians);
    }

    public double getAngle() {
        return radians;
    }

    public void print() {
        System.out.println(radians + " Radians");
    }

    public double getAngleBetween(Angle other) {
        double phi = Math.abs(this.radians - other.radians) % (2*Math.PI);
        return phi > Math.PI ? 2*Math.PI - phi : phi;
    }
}
