package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.ClimberSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetManualClimberPosition extends CommandBase {

    public SetManualClimberPosition() {
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().brake();
        ClimberSubsystem.getInstance().resetEncoder();
    }

    @Override
    public void execute() {
        if(OI.getInstance().getXboxController().getAButton()) {
            ClimberSubsystem.getInstance().setMotor(0.1);
        } else if(OI.getInstance().getXboxController().getBButton()) {
            ClimberSubsystem.getInstance().setMotor(-0.1);
        } else {
            ClimberSubsystem.getInstance().brake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().brake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
