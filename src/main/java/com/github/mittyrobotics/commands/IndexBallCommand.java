package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class IndexBallCommand extends RunCommand {
    public IndexBallCommand(){
        super(()-> ConveyorSubsystem.getInstance().indexBall(), ConveyorSubsystem.getInstance());
    }
}
