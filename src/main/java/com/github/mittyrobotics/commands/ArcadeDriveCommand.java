package com.github.mittyrobotics.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDriveCommand extends CommandBase {
    private boolean isReversed;
    ArcadeDriveCommand(){
        addRequirements(DriveTrainSubsystem.getInstance());
    }

    @Override
    public void initialize(){
        isReversed = false;
    }

    @Override
    public void execute(){
        if(OI.getInstance().getJoystick2().getRawButtonPressed(2)){
            isReversed = true;
        } else if(OI.getInstance().getJoystick2().getRawButtonPressed(3)){
            isReversed = false;
        }
        double turn = OI.getInstance().getXboxWheel().getX() * 450 / 120;

        double speed = -OI.getInstance().getJoystick2().getY();
        if(Math.abs(speed) < 0.1){
            speed = 0;
        }
        boolean brake = OI.getInstance().getJoystick2().getTrigger();

        if(Math.abs(turn) > 1){
            turn = Math.signum(turn);
        }
        double e = 1 - turn;



        if(brake){
            speed = 0;
            turn = 0;
            DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Brake);
        } else {
            DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Coast);
        }
        if(isReversed){
            speed = -speed;
        }
        double newSpeed = (speed*e);
        double newTurn = turn;

        if(Math.abs(speed) < 0.05){
            DriveTrainSubsystem.getInstance().tankDrive(newTurn, - newTurn);
        }
        else if(speed >= 0){
            if(isReversed){
                newTurn = -newTurn;
            }
            DriveTrainSubsystem.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
        } else {
            if(isReversed){
                newTurn = -newTurn;
            }
            DriveTrainSubsystem.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
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