package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.WheelColor;
import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpinWheelMacro extends ConditionalCommand {
    public SpinWheelMacro(){
        super(new SpinRevsMacro(), new SpinToColorMacro(), ()->SpinnerSubsystem.getInstance().getGameMessage() == WheelColor.None);
    }
}
