package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetManualIntakePosition extends CommandBase {

    public SetManualIntakePosition() {
        addRequirements(IntakeRaiseSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeRaiseSubsystem.getInstance().stop();
        IntakeRaiseSubsystem.getInstance().resetEncoder();
    }

    @Override
    public void execute() {
        if(IntakeRaiseSubsystem.getInstance().getSwitch(0) || IntakeRaiseSubsystem.getInstance().getSwitch(1)) IntakeRaiseSubsystem.getInstance().stop();
        else {
            if (OI.getInstance().getXboxController().getAButton()) {
                IntakeRaiseSubsystem.getInstance().overrideSetMotor(0.2);
            } else if (OI.getInstance().getXboxController().getBButton()) {
                IntakeRaiseSubsystem.getInstance().overrideSetMotor(-0.2);
            } else {
                IntakeRaiseSubsystem.getInstance().stop();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRaiseSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
