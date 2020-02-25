package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualSpinColorWheelCommand extends CommandBase {
    public ManualSpinColorWheelCommand() {
        addRequirements(SpinnerSubsystem.getInstance());
    }

    @Override
    public void execute() {
        SpinnerSubsystem.getInstance().setSpinnerManual(OI.getInstance().getJoystick1().getX());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
