package com.github.mittyrobotics.autonomous.path;

import com.github.mittyrobotics.autonomous.math.Circle;
import com.github.mittyrobotics.autonomous.math.Point2D;
import com.github.mittyrobotics.autonomous.math.Pose2D;

public class PurePursuitController {
    public static DifferentialDriveState purePursuit(Pose2D robotPose, Point2D lookaheadPoint, double linearVelocity, double trackwidth) {
        DifferentialDriveState dds = new DifferentialDriveState(trackwidth);
        Circle tangentCircle = new Circle();
        tangentCircle.updateFromPoseAndPoint(robotPose, lookaheadPoint);

        dds.updateFromLinearVelocityAndRadius(linearVelocity, tangentCircle.getRadius(), trackwidth);

        return dds;
    }

    public static DifferentialDriveState purePursuit(double tangentRadius, double linearVelocity, double trackwidth) {
        DifferentialDriveState dds = new DifferentialDriveState(trackwidth);
        dds.updateFromLinearVelocityAndRadius(linearVelocity, tangentRadius, trackwidth);
        return dds;
    }
}
