package com.github.mittyrobotics.autonomous;

public class Pose2D {
    private Point2D position;
    private Angle angle;

    private double x;
    private double y;
    private double rotation;

    public Pose2D() {
        this(new Point2D(), new Angle());
    }

    public Pose2D(Vector2D position, Angle angle) {
        this.angle = angle;
        this.position = new Point2D(position);
        this.x = position.getX();
        this.y = position.getY();
        this.rotation = angle.getAngle();
    }

    public Pose2D(Point2D position, Angle angle) {
        this.angle = angle;
        this.position = position;
        this.x = position.getX();
        this.y = position.getY();
        this.rotation = angle.getAngle();
    }

    public Pose2D(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.rotation = angle;

        this.position = new Point2D(x, y);
        this.angle = new Angle(angle);
    }

    public double distance(Pose2D other) {
        return other.getPosition().distance(this.position);
    }

    public Point2D getPosition() {
        return position;
    }

    public double getAngleRadians() {
        return rotation;
    }

    public Angle getAngle() {
        return angle;
    }

    public void print() {
        System.out.println("(" + position.getX() + ", " + position.getY() + ", " + rotation + " rad)");
    }
}
