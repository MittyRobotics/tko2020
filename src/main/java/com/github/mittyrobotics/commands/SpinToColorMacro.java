package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.WheelColor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class SpinToColorMacro extends SequentialCommandGroup {
    public SpinToColorMacro(WheelColor color) {
        super();
        addCommands(new SpinnerUpCommand(), new WaitCommand(.5), new DriveToColorWheelCommand(), new SpinToColorCommand(color));
    }
}