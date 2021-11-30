package com.github.mittyrobotics.autonomous.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.autonomous.math.Pose2D;
import com.github.mittyrobotics.autonomous.path.DifferentialDriveState;
import com.github.mittyrobotics.autonomous.path.Path;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

public class PathFollowingCommandV2 extends CommandBase {

    private Path trajectory;
    private double lastTime;
    private double lookaheadPercent = 0.02;
    private final double TRACKWIDTH = inches(25.0);

    public PathFollowingCommandV2(Path trajectory) {
        addRequirements(DrivetrainSubsystem.getInstance());
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        lastTime = Timer.getFPGATimestamp();
        DrivetrainSubsystem.getInstance().setMode(NeutralMode.Brake);

    }

    @Override
    public void execute() {
        double dt = Timer.getFPGATimestamp() - lastTime;


        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector().div(39.37), Odometry.getInstance().getRobotRotation());
        Pose2D robotPose = new Pose2D(robotTransform.getX(), robotTransform.getY(), robotTransform.getRadians());

        DifferentialDriveState dds = trajectory.update(robotPose, dt, lookaheadPercent, TRACKWIDTH);

        SmartDashboard.putNumber("left dds", dds.getLeftVelocity() * 39.37);
        SmartDashboard.putNumber("right dds", dds.getRightVelocity() * 39.37);


        DrivetrainSubsystem.getInstance().tankVelocity(dds.getLeftVelocity() * 39.37, dds.getRightVelocity() * 39.37);


        lastTime = Timer.getFPGATimestamp();
    }

    @Override
    public void end(boolean interrupted) {
        DrivetrainSubsystem.getInstance().overrideSetMotor(0, 0);
    }

    @Override
    public boolean isFinished() {
        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector().div(39.37), Odometry.getInstance().getRobotRotation());
        Pose2D robotPose = new Pose2D(robotTransform.getX(), robotTransform.getY(), robotTransform.getRadians());

        return trajectory.isFinished(robotPose, inches(3));
    }
}
