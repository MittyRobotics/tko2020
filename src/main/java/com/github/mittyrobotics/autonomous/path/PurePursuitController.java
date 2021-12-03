package com.github.mittyrobotics.autonomous.path;

import com.github.mittyrobotics.autonomous.math.Circle;
import com.github.mittyrobotics.autonomous.math.Point2D;
import com.github.mittyrobotics.autonomous.math.Pose2D;

public class PurePursuitController {
    public static DifferentialDriveState purePursuit(double tangentRadius, double linearVelocity, boolean turnRight, double trackwidth) {
        DifferentialDriveState dds = new DifferentialDriveState(trackwidth);
        dds.updateFromLinearVelocityAndRadius(linearVelocity, tangentRadius, turnRight, trackwidth);
        return dds;
    }
}

