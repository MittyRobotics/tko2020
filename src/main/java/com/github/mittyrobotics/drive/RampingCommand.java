package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RampingCommand extends CommandBase {
    private double pos;

    public RampingCommand(double pos){
        addRequirements(DriveTrainTalon.getInstance());
        this.pos = pos;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        double tempPosLeft;
        double tempPosRight;
        final double RAMP_RATE = 15;

        tempPosLeft = Math.min(pos - DriveTrainTalon.getInstance().getLeftEncoder(), DriveTrainTalon.getInstance().getLeftEncoder() + RAMP_RATE);
        tempPosRight = Math.min(pos - DriveTrainTalon.getInstance().getRightEncoder(), DriveTrainTalon.getInstance().getRightEncoder() + RAMP_RATE);
        if(tempPosLeft == pos - DriveTrainTalon.getInstance().getLeftEncoder()){
            tempPosLeft = pos;
        }
        DriveTrainTalon.getInstance().movePos(tempPosLeft, tempPosRight);


    }
    @Override
    public void end(boolean interrupted){
        DriveTrainTalon.getInstance().tankDrive(0, 0);
    }
    @Override
    public boolean isFinished() {
//        return ((Math.abs(pos - DriveTrainTalon.getInstance().getLeftEncoder()) < 1) && (Math.abs(pos - DriveTrainTalon.getInstance().getLeftEncoder()) < 1));
        return false;
    }
}