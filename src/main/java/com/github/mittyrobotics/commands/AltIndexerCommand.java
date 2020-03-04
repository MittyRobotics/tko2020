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
        ConveyorSubsystem.getInstance().setMotor(1);
    }

    @Override
    public void execute() {
        if(!ConveyorSubsystem.getInstance().getSwitch()){
            CommandScheduler.getInstance().schedule(new FourBallConveyorIndexCommand(3));
        }
    }

    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
