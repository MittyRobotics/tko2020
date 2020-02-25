package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import com.github.mittyrobotics.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopBallCommand extends CommandBase {


    public StopBallCommand() {
        super();
        addRequirements(IntakeSubsystem.getInstance(), ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeSubsystem.getInstance().stopWheel();
        ConveyorSubsystem.getInstance().setConveyorSpeed(0);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}