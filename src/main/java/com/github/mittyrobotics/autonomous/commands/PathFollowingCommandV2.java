package com.github.mittyrobotics.autonomous.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.autonomous.pathfollowing.*;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

import com.github.mittyrobotics.autonomous.*;

public class PathFollowingCommandV2 extends CommandBase {

    private PurePursuitPath trajectory;
    private double lastTime;
    private double LOOKAHEAD, end_threshold, adjust_threshold;
    private final double TRACKWIDTH = inches(25.0);
    private boolean reverse;

    public PathFollowingCommandV2(PurePursuitPath trajectory, double LOOKAHEAD, double end_threshold, double adjust_threshold, boolean reverse) {
        addRequirements(DrivetrainSubsystem.getInstance());
        this.trajectory = trajectory;
        this.reverse = reverse;
        this.end_threshold = end_threshold;
        this.adjust_threshold = adjust_threshold;
        this.LOOKAHEAD = LOOKAHEAD;
    }

    @Override
    public void initialize() {
        lastTime = Timer.getFPGATimestamp();
        DrivetrainSubsystem.getInstance().setMode(NeutralMode.Brake);

    }

    @Override
    public void execute() {
        double dt = Timer.getFPGATimestamp() - lastTime;


        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector().div(39.37), Odometry.getInstance().getRobotRotation().plus(reverse?new Rotation(degrees(180)):new Rotation(0)));
        Pose2D robotPose = new Pose2D(robotTransform.getX(), robotTransform.getY(), robotTransform.getRadians());


//        update(Pose2D robotPose, double dt, double lookahead, double end_threshold, double adjust_threshold, int newtonsSteps, double trackwidth)
        DifferentialDriveState dds = trajectory.update(robotPose, dt, LOOKAHEAD, adjust_threshold, 30, TRACKWIDTH);

        SmartDashboard.putNumber("left dds", dds.getLeftVelocity() * 39.37);
        SmartDashboard.putNumber("right dds", dds.getRightVelocity() * 39.37);

        System.out.println("X: " + robotTransform.getX() * PurePursuitPath.TO_INCHES + " | Y: " + robotTransform.getY() * PurePursuitPath.TO_INCHES);

        if(reverse) {
            DrivetrainSubsystem.getInstance().tankVelocity(-dds.getRightVelocity() * 39.37, -dds.getLeftVelocity() * 39.37);
        } else {
            DrivetrainSubsystem.getInstance().tankVelocity(dds.getLeftVelocity() * 39.37, dds.getRightVelocity() * 39.37);
        }

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

        return trajectory.isFinished(robotPose, end_threshold);
    }
}
