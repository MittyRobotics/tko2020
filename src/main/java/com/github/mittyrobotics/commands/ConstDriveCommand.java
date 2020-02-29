package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConstDriveCommand extends RunCommand {
    public ConstDriveCommand(double leftSpeed, double rightSpeed){
        super(() -> DriveTrainSubsystem.getInstance().tankDrive(leftSpeed, rightSpeed, 0, 1),
                DriveTrainSubsystem.getInstance());
    }

}