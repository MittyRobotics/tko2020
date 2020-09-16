package com.github.mittyrobotics.NewClimber;

import com.github.mittyrobotics.subsystems.IntakePistonSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ExtendIntake extends InstantCommand {
    public ExtendIntake(){
        super(()-> IntakePistonSubsystem.getInstance().extendPiston(), IntakePistonSubsystem.getInstance());
    }
}
