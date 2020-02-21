package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.github.mittyrobotics.colorwheel.ColorPiston;
import com.github.mittyrobotics.colorwheel.Spinner;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.awt.*;

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
        DriveTrainTalon.getInstance().setMotor(0, 0);
    }

    @Override
    public void execute() {
        DriveTrainTalon.getInstance().setMotor(0.2, 0.2);
        System.out.println("Left: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
        System.out.println("Right: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());
        if(DriveTrainTalon.getInstance().getLeftEncoderVelocity() < -5) {
            System.out.println("ATSPEED");
            DriveTrainTalon.getInstance().setMotor(0.1,0.1);
            atSpeed = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainTalon.getInstance().setMotor(0.05,0.05);
        System.out.println("END");
    }

    @Override

    public boolean isFinished() {
        return atSpeed && DriveTrainTalon.getInstance().getLeftEncoderVelocity() > -0.05;
    }
}

