package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MaxSpeedTestFalcon extends CommandBase {
    public MaxSpeedTestFalcon(){ addRequirements(DriveTrainFalcon.getInstance()); }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        DriveTrainFalcon.getInstance().tankDrive(1, 1);
        System.out.println("Left Speed: "+DriveTrainFalcon.getInstance().getLeftEncoderVelocity());
        System.out.println("Right Speed: "+DriveTrainFalcon.getInstance().getRightEncoderVelocity());
    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
