package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConveyorOverrideOuttake extends RunCommand {

    public ConveyorOverrideOuttake() {
        super(() -> {
            ConveyorSubsystem.getInstance().outtakeBall();
            IntakeSubsystem.getInstance().setMotor(IntakeConstants.OUTTAKE_SPEED);
        });
    }

    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().stopMotor();
        IntakeSubsystem.getInstance().setMotor(0);
    }

}
