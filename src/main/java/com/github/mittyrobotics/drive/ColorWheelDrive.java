package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.drive.Constants.COLORWHEELDRIVE_CURRENT;

public class ColorWheelDrive extends CommandBase {

    boolean atSpeed;

    public ColorWheelDrive() {
        addRequirements(DriveTrainTalon.getInstance());
    }

    @Override
    public void initialize() {
        atSpeed = false;
        System.out.println("Starting");
        DriveTrainTalon.getInstance().tankDrive(0, 0);
    }

    @Override
    public void execute() {
        DriveTrainTalon.getInstance().tankDrive(0.17, 0.17);
        System.out.println("Left: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
        System.out.println("Right: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());
        if(DriveTrainTalon.getInstance().getLeftEncoderVelocity() < -8) {
            atSpeed = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainTalon.getInstance().tankDrive(0.1,0.1);
        System.out.println("END");
    }

    @Override
    public boolean isFinished() {
        return atSpeed;
    }
}
