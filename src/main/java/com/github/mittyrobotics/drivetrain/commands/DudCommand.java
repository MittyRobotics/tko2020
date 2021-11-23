package com.github.mittyrobotics.drivetrain.commands;

import com.github.mittyrobotics.drivetrain.DriveMotorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DudCommand extends CommandBase {

    @Override
    public void initialize() {
        addRequirements(DriveMotorSubsystem.getInstance());
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        DriveMotorSubsystem.getInstance().driveMotor(0, 0.2);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
