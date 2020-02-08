package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MaxSpeedTest extends CommandBase {
    public MaxSpeedTest(){ addRequirements(DriveTrainFalcon.getInstance()); }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        DriveTrainFalcon.getInstance().tankDrive(1, 1);
        System.out.println("Fucking Left Speed: "+DriveTrainFalcon.getInstance().getLeftEncoderVelocity());
        System.out.println("Fucking Right Speed: "+DriveTrainFalcon.getInstance().getRightEncoderVelocity());
    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
