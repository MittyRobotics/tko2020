package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetIntake extends CommandBase {
    public ResetIntake() {
        addRequirements(IntakeRaiseSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        IntakeRaiseSubsystem.getInstance().raiseIntake();
        IntakeRaiseSubsystem.getInstance().overrideSetMotor(IntakeConstants.INTAKE_MANUAL_RAISE_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRaiseSubsystem.getInstance().stop();
        IntakeRaiseSubsystem.getInstance().resetEncoder();
    }

    @Override
    public boolean isFinished() {
        return IntakeRaiseSubsystem.getInstance().getSwitch(1);
    }
}
