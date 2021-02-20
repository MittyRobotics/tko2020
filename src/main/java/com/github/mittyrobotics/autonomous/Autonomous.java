package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;

public class Autonomous {
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

    public Rotation getFieldRelativeTurretRotation(double robotAngle, double turretAngle){
        return Rotation.fromDegrees(robotAngle+turretAngle);
    }

    public void setDriveCommand(DrivetrainState state){
        currentDriveCommand = state;
    }
}
