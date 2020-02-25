package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import com.github.mittyrobotics.constants.WheelColor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpinWheelMacro extends SequentialCommandGroup {
    public SpinWheelMacro() {
        super();
        if (SpinnerSubsystem.getInstance().getGameMessage() == WheelColor.None) {
            addCommands(new SpinRevsMacro());
        } else {
            addCommands(new SpinToColorCommand(SpinnerSubsystem.getInstance().getGameMessage()));
        }
    }
}