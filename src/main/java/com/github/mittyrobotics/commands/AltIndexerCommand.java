package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class AltIndexerCommand extends CommandBase {
    public AltIndexerCommand() {
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ConveyorSubsystem.getInstance().manualSetConveyorSpeed(1);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        CommandScheduler.getInstance().schedule(new FourBallConveyorIndexCommand(6));
    }

    @Override
    public boolean isFinished() {
        return !ConveyorSubsystem.getInstance().getEntranceSwitch();
    }
}
