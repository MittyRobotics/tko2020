package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.kinematics.DifferentialDriveState;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.State;
import com.github.mittyrobotics.motion.profiles.PathTrajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;
import static com.github.mittyrobotics.motion.controllers.PurePursuitKt.purePursuit;

public class PathFollowingCommand extends CommandBase {

    private PathTrajectory trajectory;
    private double initialTime;
    private final double LOOKAHEADDISTANCE = inches(10.0);
    private final double TRACKWIDTH = inches(24.0);

    public PathFollowingCommand(PathTrajectory trajectory) {
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double dt = Timer.getFPGATimestamp() - initialTime;

        State state = trajectory.next(dt);
        Vector2D lookAheadPosition = trajectory.getTransform(LOOKAHEADDISTANCE).getVector();

        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector().div(39.37), Odometry.getInstance().getRobotRotation());
        DifferentialDriveState dds = purePursuit(robotTransform, lookAheadPosition, state.get(0), TRACKWIDTH);

        SmartDashboard.putNumber("left dds", dds.getLeft()*39.37);
        SmartDashboard.putNumber("right dds", dds.getRight()*39.37);

        DrivetrainSubsystem.getInstance().tankVelocity(dds.getLeft()*39.37, dds.getRight()*39.37);

        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void end(boolean interrupted) {
        DrivetrainSubsystem.getInstance().overrideSetMotor(0, 0);
    }

    @Override
    public boolean isFinished() {
        return trajectory.isFinished(inches(1));
    }
}
