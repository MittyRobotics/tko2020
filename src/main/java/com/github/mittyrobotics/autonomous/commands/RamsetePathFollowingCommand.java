package com.github.mittyrobotics.autonomous.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.autonomous.Pose2D;
import com.github.mittyrobotics.autonomous.DifferentialDriveState;
import com.github.mittyrobotics.autonomous.Path;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.controllers.Ramsete;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

import com.github.mittyrobotics.autonomous.*;

public class RamsetePathFollowingCommand extends CommandBase {

    private RamsetePath trajectory;
    private double lastTime;
    private final double TRACKWIDTH = inches(25.0);
    private double b, Z, end_threshold, adjust_threshold;
    private boolean reverse;

    public RamsetePathFollowingCommand(RamsetePath trajectory, double b, double Z, double end_threshold, double adjust_threshold, boolean reverse) {
        addRequirements(DrivetrainSubsystem.getInstance());
        this.b = b;
        this.Z = Z;
        this.end_threshold = end_threshold;
        this.adjust_threshold = adjust_threshold;
        this.trajectory = trajectory;
        this.reverse = reverse;
    }

    public RamsetePathFollowingCommand(RamsetePath trajectory, double b, double Z, boolean reverse) {
        this(trajectory, b, Z, inches(1), inches(5), reverse);
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


//        public DifferentialDriveState update(Pose2D robotPose, double dt, double end_threshold, double adjust_threshold, int newtonsSteps, double b, double Z, double trackwidth) {
//        System.out.println(end_threshold * Path.TO_INCHES);
        DifferentialDriveState dds = trajectory.update(robotPose, dt, end_threshold, adjust_threshold, 50, b, Z, TRACKWIDTH);

        SmartDashboard.putNumber("left dds", dds.getLeftVelocity() * 39.37);
        SmartDashboard.putNumber("right dds", dds.getRightVelocity() * 39.37);

        System.out.println("ACTUAL: X: " + robotTransform.getX() * Path.TO_INCHES + " | Y: " + robotTransform.getY() * Path.TO_INCHES);


        if(reverse) {
            DrivetrainSubsystem.getInstance().tankVelocity(-dds.getRightVelocity() * 39.37, -dds.getLeftVelocity() * 39.37);
        } else {
            DrivetrainSubsystem.getInstance().tankVelocity(dds.getLeftVelocity() * 39.37, dds.getRightVelocity() * 39.37);
        }


        lastTime = Timer.getFPGATimestamp();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("DONE BLYAT");
        DrivetrainSubsystem.getInstance().overrideSetMotor(0, 0);
    }

    @Override
    public boolean isFinished() {
        Transform robotTransform = new Transform(Odometry.getInstance().getRobotVector().div(39.37), Odometry.getInstance().getRobotRotation());
        Pose2D robotPose = new Pose2D(robotTransform.getX(), robotTransform.getY(), robotTransform.getRadians());

        return trajectory.isFinished(robotPose, end_threshold);
    }
}
