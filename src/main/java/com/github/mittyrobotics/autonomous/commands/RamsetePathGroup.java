package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Path;
import com.github.mittyrobotics.autonomous.Pose2D;
import com.github.mittyrobotics.autonomous.QuinticHermiteSpline;
import com.github.mittyrobotics.autonomous.RamsetePath;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

public class RamsetePathGroup extends SequentialCommandGroup {

    private QuinticHermiteSpline splineForward, splineReverse;
    private RamsetePath pathForward, pathReverse;

    public RamsetePathGroup(Pose2D point1, Pose2D point2, int cycles) {
        this.splineForward = new QuinticHermiteSpline(point1, point2);
        this.splineReverse = new QuinticHermiteSpline(point2, point1);

        pathForward = new RamsetePath(splineForward,
                40 * Path.TO_METERS, 40 * Path.TO_METERS,
                50 * Path.TO_METERS, 100 * Path.TO_METERS,
                0 * Path.TO_METERS, 0 * Path.TO_METERS
        );

        pathReverse = new RamsetePath(splineReverse,
                40 * Path.TO_METERS, 40 * Path.TO_METERS,
                50 * Path.TO_METERS, 100 * Path.TO_METERS,
                0 * Path.TO_METERS, 0 * Path.TO_METERS
        );

        for(int i = 0; i < cycles; i++) {
            addCommands(
                    sequence(
                            new RamsetePathFollowingCommand(pathForward, 1.5, 0.2, inches(1), 1000, false),
                            new RamsetePathFollowingCommand(pathReverse, 1.5, 0.2, inches(1), 1000, true)
                    )
            );
        }
    }


}
