package com.github.mittyrobotics.autonomous.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
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
import edu.wpi.first.wpiutil.math.MathUtil;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;
import static com.github.mittyrobotics.motion.controllers.PurePursuitKt.purePursuit;

public class PathFollowingCommand extends CommandBase {

    private PathTrajectory trajectory;
    private double initialTime;
    private double lookaheadDistance = inches(30.0);
    private final double MIN_LOOKAHEAD = inches(10.0);
    private final double MAX_LOOKAHEAD = inches(50.0);
    private final double TRACKWIDTH = inches(25.0);

    public PathFollowingCommand(PathTrajectory trajectory) {
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        initialTime = Timer.getFPGATimestamp();
        DrivetrainSubsystem.getInstance().setMode(NeutralMode.Brake);
    }

    @Override
    public void execute() {
        double dt = Timer.getFPGATimestamp() - initialTime;

        State state = trajectory.next(dt);
        double velocity = state.get(0);
        lookaheadDistance = (MathUtil.clamp(velocity/inches(100.0), 0, 1)*(MAX_LOOKAHEAD-MIN_LOOKAHEAD))+MIN_LOOKAHEAD;
        Vector2D lookAheadPosition = trajectory.getTransform(lookaheadDistance).getVector();

        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector().div(39.37), Odometry.getInstance().getRobotRotation());
        DifferentialDriveState dds = purePursuit(robotTransform, lookAheadPosition, velocity, TRACKWIDTH);

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
        return trajectory.isFinished(inches(3));
    }
}
