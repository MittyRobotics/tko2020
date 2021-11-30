package com.github.mittyrobotics.autonomous.splines;

import com.github.mittyrobotics.autonomous.math.Angle;
import com.github.mittyrobotics.autonomous.math.Point2D;
import com.github.mittyrobotics.autonomous.math.Pose2D;
import com.github.mittyrobotics.autonomous.math.Vector2D;

public class QuinticHermiteSpline extends Parametric {
    private Pose2D pose0, pose1;
    private Vector2D velocity0, velocity1;
    private Vector2D acceleration0, acceleration1;

    public QuinticHermiteSpline(Pose2D pose0, Pose2D pose1, Vector2D velocity0, Vector2D velocity1,
                                Vector2D acceleration0, Vector2D acceleration1) {
        this.pose0 = pose0;
        this.pose1 = pose1;
        this.velocity0 = velocity0;
        this.velocity1 = velocity1;
        this.acceleration0 = acceleration0;
        this.acceleration1 = acceleration1;

        this.length = getGaussianQuadratureLength(11);

    }

    public QuinticHermiteSpline(Pose2D pose0, Pose2D pose1, Vector2D velocity0, Vector2D velocity1) {
        this(pose0, pose1, velocity0, velocity1, new Vector2D(), new Vector2D());
    }

    public QuinticHermiteSpline(Pose2D pose0, Pose2D pose1) {
        this(pose0, pose1,
                new Vector2D(new Angle(pose0.getAngle()), pose0.distance(pose1)),
                new Vector2D(new Angle(pose1.getAngle()), pose1.distance(pose0)));
    }

    public QuinticHermiteSpline(Pose2D pose0, Pose2D pose1, double curvature0, double curvature1) {
        this(pose0, pose1);
        this.acceleration0 = new Vector2D(new Angle(pose0.getAngle()),
                getAccelerationMagnitudeFromCurvature(curvature0, pose0.distance(pose1)));
        this.acceleration1 = new Vector2D(new Angle(pose1.getAngle()),
                getAccelerationMagnitudeFromCurvature(curvature1, pose1.distance(pose0)));
    }

    @Override
    public Point2D getPoint(double t) {
        if(t >= 0 && t <= 1) {
            double h0 = -6 * t * t * t * t * t + 15 * t * t * t * t - 10 * t * t * t + 1;
            double h1 = -3 * t * t * t * t * t + 8 * t * t * t * t - 6 * t * t * t + t;
            double h2 = -(t * t * t * t * t) / 2 + 3 * t * t * t * t / 2 - 3 * t * t * t / 2 + t * t / 2;
            double h3 = t * t * t * t * t / 2 - t * t * t * t + t * t * t / 2;
            double h4 = -3 * t * t * t * t * t + 7 * t * t * t * t - 4 * t * t * t;
            double h5 = 6 * t * t * t * t * t - 15 * t * t * t * t + 10 * t * t * t;

            return getPointFromCoefficients(h0, h1, h2, h3, h4, h5);

        } else if (t < 0) {
            return pose0.getPosition();
        } else {
            return pose1.getPosition();
        }
    }

    @Override
    public Angle getAngle(double t) {
        return new Angle(getDerivative(t, 1));
    }

    @Override
    public Pose2D getPose(double t) {
        return new Pose2D(getPoint(t), getAngle(t));
    }

    @Override
    public Point2D getDerivative(double t, int n) {
        switch(n) {
            case 1:
                double h0 = -30 * t * t * t * t + 60 * t * t * t - 30 * t * t;
                double h1 = -15 * t * t * t * t + 32 * t * t * t - 18 * t * t + 1;
                double h2 = -(5 * t * t * t * t) / 2 + 6 * t * t * t - 9 * t * t / 2 + t;
                double h3 = 5 * t * t * t * t / 2 - 4 * t * t * t + 3 * t * t / 2;
                double h4 = -15 * t * t * t * t + 28 * t * t * t - 12 * t * t;
                double h5 = 30 * t * t * t * t - 60 * t * t * t + 30 * t * t;

                return getPointFromCoefficients(h0, h1, h2, h3, h4, h5);
            case 2:
                h0 = -120 * t * t * t + 180 * t * t - 60 * t;
                h1 = -60 * t * t * t + 96 * t * t - 36 * t;
                h2 = -10 * t * t * t + 18 * t * t - 9 * t + 1;
                h3 = t * (10 * t * t - 12 * t + 3);
                h4 = -60 * t * t * t + 84 * t * t - 24 * t;
                h5 = 120 * t * t * t - 180 * t * t + 60 * t;

                return getPointFromCoefficients(h0, h1, h2, h3, h4, h5);
            default:
                return new Point2D(0, 0);
        }
    }

    public Point2D getPointFromCoefficients(double h0, double h1, double h2, double h3, double h4, double h5) {
        return new Point2D(
                h0 * pose0.getPosition().getX() + h1 * velocity0.getX() + h2 * acceleration0.getX() +
                        h3 * acceleration1.getX() + h4 * velocity1.getX() + h5 * pose1.getPosition().getX(),
                h0 * pose0.getPosition().getY() + h1 * velocity0.getY() + h2 * acceleration0.getY() +
                        h3 * acceleration1.getY() + h4 * velocity1.getY() + h5 * pose1.getPosition().getY());
    }
}
