package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.BufferSubsystem;
import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UnloadConveyorCommand extends CommandBase {
    public UnloadConveyorCommand() {
        addRequirements(ConveyorSubsystem.getInstance(), BufferSubsystem.getInstance(), ShooterSubsystem.getInstance());
    }

    @Override
    public void execute() {
        ConveyorSubsystem.getInstance().manualSetConveyorSpeed(.4);
        BufferSubsystem.getInstance().manualBufferSpeed(.4);
        ShooterSubsystem.getInstance().setShooterPercent(.25);
//        Intake.getInstance().intakeBall();
    }

    @Override
    public boolean isFinished() {
        return DriverStation.getInstance().isDisabled();
    }
}
