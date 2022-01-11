package com.github.mittyrobotics.autonomous.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.autonomous.Pose2D;
import com.github.mittyrobotics.autonomous.DifferentialDriveState;
import com.github.mittyrobotics.autonomous.Path;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

import com.github.mittyrobotics.autonomous.*;

public class PathFollowingCommandV2 extends CommandBase {

    private Path trajectory;
    private double lastTime;
    private final double LOOKAHEAD = inches(15.0);
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


//        update(Pose2D robotPose, double dt, double lookahead, double end_threshold, double adjust_threshold, int newtonsSteps, double trackwidth)
        DifferentialDriveState dds = trajectory.update(robotPose, dt, LOOKAHEAD, inches(3), inches(2), 30, TRACKWIDTH);

        SmartDashboard.putNumber("left dds", dds.getLeftVelocity() * 39.37);
        SmartDashboard.putNumber("right dds", dds.getRightVelocity() * 39.37);

        System.out.println("Left: " + dds.getLeftVelocity() + " | Right: " + dds.getRightVelocity());


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

        return trajectory.isFinished(robotPose, inches(4));
    }
}
