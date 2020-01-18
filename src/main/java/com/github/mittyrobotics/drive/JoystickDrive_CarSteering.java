package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickDrive_CarSteering extends CommandBase {

    JoystickDrive_CarSteering(){
        addRequirements(DriveTrainTalon.getInstance());
        //addRequirements(DriveTrainSparks.getInstance());
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){

        double turn = OI.getInstance().getXboxWheel().getX() * 450 / 120;

        double speed = -OI.getInstance().getJoystick1().getY();
        boolean brake = OI.getInstance().getJoystick1().getTrigger();

        if(Math.abs(turn) > 1){
            turn = Math.signum(turn);
        }
        double e = 1 - turn;


        if(brake){
            speed = 0;
            turn = 0;
        }

        double newSpeed = speed*e;
        double newTurn = turn;

        if(Math.abs(speed) < 0.05){
            DriveTrainTalon.getInstance().tankDrive(newTurn, - newTurn);
            //DriveTrainSparks.getInstance().tankDrive(newTurn, - newTurn);

        }
        else if(speed >= 0){
            DriveTrainTalon.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
            //DriveTrainSparks.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
        } else {
            DriveTrainTalon.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
            //DriveTrainSparks.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
        }

    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished() {
        return false;
    }
}