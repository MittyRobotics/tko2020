package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.drive.Constants.COLORWHEELDRIVE_CURRENT;

public class ColorWheelDrive extends CommandBase {

    public ColorWheelDrive() {
        addRequirements(DriveTrainTalon.getInstance());
    }

    @Override
    public void initialize() {
        DriveTrainTalon.getInstance().getLeftTalon().set(ControlMode.Current, COLORWHEELDRIVE_CURRENT);
        DriveTrainTalon.getInstance().getRightTalon().set(ControlMode.Current, COLORWHEELDRIVE_CURRENT);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainTalon.getInstance().tankDrive(0,0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
