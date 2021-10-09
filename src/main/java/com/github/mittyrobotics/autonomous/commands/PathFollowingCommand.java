package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.kinematics.DifferentialDriveState;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.State;
import com.github.mittyrobotics.motion.profiles.PathTrajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;
import static com.github.mittyrobotics.motion.controllers.PurePursuitKt.purePursuit;

public class PathFollowingCommand extends CommandBase {

    private PathTrajectory trajectory;
    private double time;

    private final double LOOKAHEADDISTANCE = inches(20.0);
    private final double TRACKWIDTH = inches(25);
    private final double THRESHOLD = inches(0.5);

    public PathFollowingCommand(PathTrajectory trajectory) {
        this.trajectory = trajectory;
        this.time = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double dt = Timer.getFPGATimestamp() - time;
        this.time = Timer.getFPGATimestamp();
        State state = trajectory.next(dt);

        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector(), Odometry.getInstance().getRobotRotation());

        Vector2D lookAheadPosition = trajectory.getTransform(LOOKAHEADDISTANCE).getVector();

        DifferentialDriveState driveState = purePursuit(robotTransform, lookAheadPosition, state.get(0), TRACKWIDTH);

        DrivetrainSubsystem.getInstance().tankVelocity(driveState.getLeft(), driveState.getRight());
    }

    @Override
    public boolean isFinished() {
        return trajectory.isFinished(THRESHOLD);
    }
}
