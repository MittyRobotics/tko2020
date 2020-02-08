package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MaxSpeedTestTalon extends CommandBase {
    public MaxSpeedTestTalon(){ addRequirements(DriveTrainFalcon.getInstance()); }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        DriveTrainTalon.tankDrive(1, 1);
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
