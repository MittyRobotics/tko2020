package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualSpinnerButtonCommand extends CommandBase {
    double speed;

    public ManualSpinnerButtonCommand(double speed) {
        this.speed = speed;
        addRequirements(SpinnerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        SpinnerSubsystem.getInstance().setMotor(speed);
    }

    @Override
    public void end(boolean initialize) {
        SpinnerSubsystem.getInstance().setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
