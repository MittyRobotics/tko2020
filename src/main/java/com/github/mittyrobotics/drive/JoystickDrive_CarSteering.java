package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickDrive_CarSteering extends CommandBase {
    private boolean isReversed;
    JoystickDrive_CarSteering(){
        //addRequirements(DriveTrainSparks.getInstance());
        addRequirements(DriveTrainFalcon.getInstance());
    }

    @Override
    public void initialize(){
        isReversed = false;
    }

    @Override
    public void execute(){
        if(OI.getInstance().getJoystick1().getRawButtonPressed(2)){
            isReversed = true;
        } else if(OI.getInstance().getJoystick1().getRawButtonPressed(3)){
            isReversed = false;
        }
        double turn = OI.getInstance().getXboxWheel().getX() * 450 / 120;

        double speed = -OI.getInstance().getJoystick1().getY();
        if(Math.abs(speed) < 0.1){
            speed = 0;
        }
        boolean brake = OI.getInstance().getJoystick1().getTrigger();

        if(Math.abs(turn) > 1){
            turn = Math.signum(turn);
        }
        double e = 1 - turn;



        if(brake){
            speed = 0;
            turn = 0;
            DriveTrainFalcon.getInstance().setNeutralMode(NeutralMode.Brake);
        } else {
            DriveTrainFalcon.getInstance().setNeutralMode(NeutralMode.Coast);
        }
        if(isReversed){
            speed = -speed;
        }
        double newSpeed = (speed*e);
        double newTurn = turn;

        if(Math.abs(speed) < 0.05){
            DriveTrainFalcon.getInstance().tankDrive(newTurn, - newTurn);
            //DriveTrainFalcon.getInstance().tankDrive(-newTurn, newTurn);
        }
        else if(speed >= 0){
            if(isReversed){
                newTurn = -newTurn;
            }
            DriveTrainFalcon.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
            //DriveTrainFalcon.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
        } else {
            if(isReversed){
                newTurn = -newTurn;
            }
            DriveTrainFalcon.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
            //DriveTrainFalcon.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
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