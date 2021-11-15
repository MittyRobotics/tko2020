package com.github.mittyrobotics.drivetrain.commands;

import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JerkLowerIntake extends CommandBase {

    double counter;

    public JerkLowerIntake() {
        addRequirements(DrivetrainSubsystem.getInstance());
        counter = 0.0;
    }

    @Override
    public void initialize() {
        DrivetrainSubsystem.getInstance().brake();
    }

    @Override
    public void execute() {
        if(counter % 50.0 == 0) {
            DrivetrainSubsystem.getInstance().tankDrive(0.3, 0.3);
        } else if(counter % 30 == 0) {
            DrivetrainSubsystem.getInstance().tankDrive(-0.3, -0.3);
        }
        counter++;
    }

    @Override
    public void end(boolean interrupted) {
        DrivetrainSubsystem.getInstance().brake();
    }

    @Override
    public boolean isFinished() {
        return counter > 1500;
    }
}
