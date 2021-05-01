package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ManualSetConveyorCommand extends RunCommand {
    public ManualSetConveyorCommand(double conveyorSpeed, double intakeSpeed){
        super(()-> {IntakeSubsystem.getInstance().overrideSetMotor(intakeSpeed);
            ConveyorSubsystem.getInstance().overrideSetMotor(conveyorSpeed);
        });
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
