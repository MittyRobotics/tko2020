package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.ClimberConstants;
import com.github.mittyrobotics.subsystems.WinchSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveWinchCommand extends CommandBase {
    double leftPosition, rightPosition, threshold;
    public MoveWinchCommand(double leftPosition, double rightPosition, double threshold){
        addRequirements(WinchSubsystem.getInstance());
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
        this.threshold = threshold;
    }

    public MoveWinchCommand(double leftPosition, double rightPosition){
        this(leftPosition, rightPosition, ClimberConstants.THRESHOLD);
    }

    @Override
    public void initialize(){
        WinchSubsystem.getInstance().setupWinchPID(leftPosition, rightPosition);
    }

    @Override
    public void execute(){
        WinchSubsystem.getInstance().runWinchControlLoop();
    }

    @Override
    public void end(boolean interrupted){
        WinchSubsystem.getInstance().setMotor(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(WinchSubsystem.getInstance().getLeftError()) < threshold &&
                Math.abs(WinchSubsystem.getInstance().getRightError()) < threshold &&
                Math.abs(WinchSubsystem.getInstance().getAuxError()) < threshold;
    }
}
