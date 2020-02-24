package com.github.mittyrobotics.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
//TODO find all numbers
public class ColorWheelDrive extends CommandBase {

    boolean atSpeed;

    public ColorWheelDrive() {
        addRequirements(DriveTrainFalcon.getInstance());
    }

    @Override
    public void initialize() {
        atSpeed = false;
        System.out.println("Starting");
        DriveTrainFalcon.getInstance().tankDrive(0, 0);
    }

    @Override
    public void execute() {
        DriveTrainTalon.getInstance().tankVelocity(0.2, 0.2);
        if(DriveTrainTalon.getInstance().getLeftEncoderVelocity() < -5) {
            DriveTrainTalon.getInstance().tankVelocity(0.1, 0.1);
            atSpeed = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainTalon.getInstance().tankVelocity(0.05, 0.05);
        System.out.println("END");
    }

    @Override

    public boolean isFinished() {
        return atSpeed && DriveTrainTalon.getInstance().getLeftEncoderVelocity() > -0.05;
    }
}
