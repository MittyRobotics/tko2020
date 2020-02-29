package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import com.github.mittyrobotics.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ManualSpinnerButtonCommand extends CommandBase {
    double speed;
    public ManualSpinnerButtonCommand(double speed){
        this.speed = speed;
        addRequirements(SpinnerSubsystem.getInstance());
    }

    @Override
    public void initialize(){
        SpinnerSubsystem.getInstance().setSpinnerManual(speed);
    }

    @Override
    public void end(boolean initialize){
        SpinnerSubsystem.getInstance().setSpinnerManual(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
