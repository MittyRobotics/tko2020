package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

//TODO find all numbers
public class DriveToColorWheelCommand extends CommandBase {

    boolean atSpeed;

    public DriveToColorWheelCommand() {
        addRequirements(DriveTrainSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        atSpeed = false;
        System.out.println("Starting");
        DriveTrainSubsystem.getInstance().tankDrive(0, 0);
    }

    @Override
    public void execute() {
        DriveTrainSubsystem.getInstance().tankVelocity(0.2, 0.2);
        if (DriveTrainSubsystem.getInstance().getLeftEncoderVelocity() < -5) {
            DriveTrainSubsystem.getInstance().tankVelocity(0.1, 0.1);
            atSpeed = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainSubsystem.getInstance().tankVelocity(0.05, 0.05);
        System.out.println("END");
    }

    @Override

    public boolean isFinished() {
        return atSpeed && DriveTrainSubsystem.getInstance().getLeftEncoderVelocity() > -0.05;
    }
}
