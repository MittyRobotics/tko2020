package com.github.mittyrobotics.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class SpinRevsMacro extends SequentialCommandGroup {
    public SpinRevsMacro() {
        super();
        addCommands(new SpinnerUpCommand(), new WaitCommand(.5), new DriveToColorWheelCommand(), new SpinRevsCommand());
    }
}