package com.github.mittyrobotics.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExtendClimberMacro extends SequentialCommandGroup {
    public ExtendClimberMacro(){
        addCommands(new RetractIntake(),
                new SetTurretAngle(0),
                new ExtendClimberCommand());
    }
}
