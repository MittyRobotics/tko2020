package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import com.github.mittyrobotics.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ManualSpinnerButtonCommand extends InstantCommand {
    public ManualSpinnerButtonCommand(double speed){
        super(()-> SpinnerSubsystem.getInstance().setSpinnerManual(speed), SpinnerSubsystem.getInstance());
    }

}
