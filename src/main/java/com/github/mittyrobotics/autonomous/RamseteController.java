package com.github.mittyrobotics.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RamseteController {

    public static double ex, ey, et, k, vel, angVel, rvel, rAngVel;
    public static Angle t, td;

    public static DifferentialDriveState ramsete(Pose2D curPose, Pose2D desiredPose, double desiredVelocity, double desiredAngularVelocity, double b, double Z, double trackwidth) {
        //https://file.tavsys.net/control/controls-engineering-in-frc.pdf

        //b > 0, 0 < Z < 1, larger b -> faster convergence, larger Z -> more dampening

        vel = desiredVelocity;
        angVel = desiredAngularVelocity;


        k = 2*Z*Math.sqrt(desiredAngularVelocity*desiredAngularVelocity + b * desiredVelocity * desiredVelocity);

        t = curPose.getAngle();
        td = desiredPose.getAngle();
        double x = curPose.getPosition().x;
        double xd = desiredPose.getPosition().x;
        double y = curPose.getPosition().y;
        double yd = desiredPose.getPosition().y;

        ex = t.cos() * (xd - x) + t.sin() * (yd - y);
        ey = t.sin() * (x - xd) + t.cos() * (yd - y);
        et = td.getAngleBetween(t);

        System.out.println("ex: " + ex * Path.TO_INCHES + " | ey: " + ey * Path.TO_INCHES + " | et: " + et * 180 / Math.PI);

//        System.out.println("ex: " + ex + " | ey: " + ey + " | et: " + et);

        System.out.println("des velocity: " + desiredVelocity * Path.TO_INCHES + " | des ang: " + desiredAngularVelocity * Path.TO_INCHES);

        rvel = desiredVelocity * Math.cos(et) + k * ex;
        rAngVel = desiredAngularVelocity + k * et + b * desiredVelocity * sinc(et) * ey;

                System.out.println("r velocity: " + rvel * Path.TO_INCHES + " | r ang: " + rAngVel* Path.TO_INCHES);

        SmartDashboard.putNumber("rvel", rvel * Path.TO_INCHES);
        SmartDashboard.putNumber("rang", rAngVel* Path.TO_INCHES);
        SmartDashboard.putNumber("dvel", desiredVelocity * Path.TO_INCHES);
        SmartDashboard.putNumber("dang", desiredAngularVelocity* Path.TO_INCHES);
        SmartDashboard.putNumber("ex", ex * Path.TO_INCHES);
        SmartDashboard.putNumber("ey", ey* Path.TO_INCHES);
        SmartDashboard.putNumber("et", et * Path.TO_INCHES);


//        if(desiredAngularVelocity == 0) {
//            System.out.println("velocity: " + velocity + " | angularvelocity: " + angularVelocity);
//        }
        DifferentialDriveState dds = new DifferentialDriveState(trackwidth);

        dds.updateFromLinearAndAngularVelocity(rvel, rAngVel, trackwidth);
        return dds;
    }

    public static double sinc(double e) {
        if(e == 0) return 1;
        return Math.sin(e)/e;
    }
}
