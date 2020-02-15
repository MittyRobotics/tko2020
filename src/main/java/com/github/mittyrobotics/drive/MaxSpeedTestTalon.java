package com.github.mittyrobotics.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MaxSpeedTestTalon extends CommandBase {

    MaxSpeedTestTalon(){
        addRequirements(DriveTrainTalon.getInstance());
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        DriveTrainTalon.getInstance().tankDrive(1, 1);
        System.out.println("Left Speed: "+DriveTrainTalon.getInstance().getLeftEncoderVelocity());
        System.out.println("Right Speed: "+DriveTrainTalon.getInstance().getRightEncoderVelocity());
    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
