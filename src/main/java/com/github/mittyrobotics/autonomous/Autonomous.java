package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous implements IDashboard {
    private static Autonomous instance;
    public static Autonomous getInstance(){
        if(instance == null){
            instance = new Autonomous();
        }
        return instance;
    }

    private DrivetrainState currentDriveCommand;

    private Autonomous(){

    }

    public void run(){
        //Predict RobotPositionTracker with currentDriveCommand
        //Correct RobotPositionTracker with encoder distances
        //Get current robot position on field
        //Set velocity
        DrivetrainSubsystem.getInstance().setVelocity(currentDriveCommand.getLeft(), currentDriveCommand.getRight());
        //Calculate required turret angle
        //Calculate required shooter speed
        //Set shooter angle and speed
    }

    public Rotation getTurretRotationFromFieldRotation(Rotation fieldRotation, Rotation robotRotation){
        return fieldRotation.subtract(robotRotation);
    }

    public Rotation getFieldRotationToTarget(Position robotPosition, Position target){
        return robotPosition.angleTo(target);
    }

    public void setDriveCommand(DrivetrainState state){
        currentDriveCommand = state;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("odometry-robot-x", RobotPositionTracker.getInstance().getOdometryTransform().getPosition().getX());
        SmartDashboard.putNumber("odometry-robot-y", RobotPositionTracker.getInstance().getOdometryTransform().getPosition().getY());
        SmartDashboard.putNumber("odometry-robot-theta", RobotPositionTracker.getInstance().getOdometryTransform().getRotation().getDegrees());
        SmartDashboard.putNumber("filter-robot-x", RobotPositionTracker.getInstance().getFilterTransform().getPosition().getX());
        SmartDashboard.putNumber("filter-robot-y", RobotPositionTracker.getInstance().getFilterTransform().getPosition().getY());
        SmartDashboard.putNumber("filter-robot-theta", RobotPositionTracker.getInstance().getFilterTransform().getRotation().getDegrees());
    }
}
