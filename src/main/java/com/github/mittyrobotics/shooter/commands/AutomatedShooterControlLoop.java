package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.autonomous.FieldRotation;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class AutomatedShooterControlLoop extends CommandBase{

    private double lastTime;

    private MedianFilter visionFilter = new MedianFilter(10);
    private MedianFilter velocityFilter = new MedianFilter(10);

    private double lastVision = 0;



    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     */
    public AutomatedShooterControlLoop(){
        addRequirements(ShooterSubsystem.getInstance());
    }

    /**
     * Sets the turret setpoint
     */
    @Override
    public void initialize(){
        lastTime = Timer.getFPGATimestamp();
    }

    /**
     * Updates the turret position
     */
    @Override
    public void execute(){
        double time = Timer.getFPGATimestamp();
        double dt = time-lastTime;
        lastTime = time;

        double visionDistance = visionFilter.calculate(Vision.getInstance().getLatestTarget().distance);
        double visionRPM = getRPMFromTable(visionDistance);

        Transform fieldVelocity = RobotPositionTracker.getInstance().getFilterState().getRotatedDeltaTransform(
                RobotPositionTracker.getInstance().getFilterTransform().getRotation(), dt).divide(dt);

        double velGain = 14;
        double fieldVelocityY = velocityFilter.calculate(fieldVelocity.getPosition().getY());
        double fieldVelocityRPM = Math.abs(fieldVelocityY)*velGain;

        double RPM = visionRPM + fieldVelocityRPM;

        SmartDashboard.putNumber("auto-rpm", RPM);
        SmartDashboard.putNumber("auto-rpm-vision", visionRPM);
        SmartDashboard.putNumber("auto-rpm-vel", fieldVelocityRPM);

        ShooterSubsystem.getInstance().setShooterRpm(RPM);
    }

    private double getRPMFromTable(double distance){
        distance /= 12.0;
        double closest = AutonConstants.SHOOTER_RPM_TABLE[0][0];
        double closestVal = AutonConstants.SHOOTER_RPM_TABLE[0][1];
        for(int i = 1; i < AutonConstants.SHOOTER_RPM_TABLE.length; i++){
            if(Math.abs(distance-AutonConstants.SHOOTER_RPM_TABLE[i][0]) < closest){
                closest = AutonConstants.SHOOTER_RPM_TABLE[0][0];
                closestVal = AutonConstants.SHOOTER_RPM_TABLE[0][1];
            }
        }
        return closestVal;
    }

    /**
     * Stops the turret from moving
     */
    @Override
    public void end(boolean interrupted){
        TurretSubsystem.getInstance().stopMotor();
    }

    /**
     * Returns if the command should end
     *
     * @return the error is within the threshold
     */
    @Override
    public boolean isFinished(){
        return false;
    }
}
