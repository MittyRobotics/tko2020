package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConveyorOverrideOuttake extends RunCommand {

    public ConveyorOverrideOuttake() {
        super(() -> {
            ConveyorSubsystem.getInstance().outtakeBall();
            IntakeSubsystem.getInstance().setMotor(IntakeConstants.OUTTAKE_SPEED);
        }, ConveyorSubsystem.getInstance());
    }

    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().stopMotor();
        IntakeSubsystem.getInstance().setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return !OI.getInstance().getXboxController2().getXButton();
    }
}
