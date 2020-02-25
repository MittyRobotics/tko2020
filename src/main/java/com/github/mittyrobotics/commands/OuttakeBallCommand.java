package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import com.github.mittyrobotics.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuttakeBallCommand extends CommandBase {

    public OuttakeBallCommand() {
        super();
        addRequirements(IntakeSubsystem.getInstance(), ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeSubsystem.getInstance().outtakeBall();
        ConveyorSubsystem.getInstance().setConveyorSpeed(.2); //TODO find speed
        ConveyorSubsystem.getInstance().setReverse(true);
    }

    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().setReverse(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}