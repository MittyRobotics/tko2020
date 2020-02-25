package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeBallCommand extends CommandBase {


    public IntakeBallCommand() {
        super();
        addRequirements(IntakeSubsystem.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        IntakeSubsystem.getInstance().intakeBall();
    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
