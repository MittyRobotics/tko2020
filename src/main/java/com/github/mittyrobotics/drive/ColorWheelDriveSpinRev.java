
package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.github.mittyrobotics.colorwheel.ColorPiston;
import com.github.mittyrobotics.colorwheel.Spinner;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.awt.*;

import static com.github.mittyrobotics.drive.Constants.COLORWHEELDRIVE_CURRENT;
//TODO make stuff constants
public class ColorWheelDriveSpinRev extends CommandBase {

    boolean atSpeed;
    private States states;
    private double startPos;
    private double t;
    private boolean isFinished;
    public ColorWheelDriveSpinRev() {
        addRequirements(DriveTrainTalon.getInstance(), ColorPiston.getInstance(), Spinner.getInstance());
    }

    @Override
    public void initialize() {
        atSpeed = false;
        System.out.println("Starting");
        states = States.DRIVE_FORWARD;
        ColorPiston.getInstance().up();
        startPos = Spinner.getInstance().getRevolutions();
        isFinished = false;
    }

    @Override
    public void execute() {
        switch (states){
            case DRIVE_FORWARD:
                DriveTrainFalcon.getInstance().customTankVelocity(0.2, 0.2);
                if(DriveTrainFalcon.getInstance().getAverageVelocity() < -7){
                    states = States.SPIN;
                }
                break;
            case SPIN:
                Spinner.getInstance().setMotorFast();
                DriveTrainFalcon.getInstance().customTankVelocity(0.1, 0.1);
                if(Spinner.getInstance().getRevolutions() - startPos > 3){
                    states = States.STOP;
                    t = Timer.getFPGATimestamp();
                }
                break;
            case STOP:
                if(Timer.getFPGATimestamp() - t > 5){
                    isFinished = true;
                }
                break;
        }
        DriveTrainFalcon.getInstance().customTankVelocity(0.2, 0.2);
        System.out.println("Left: " + DriveTrainTalon.getInstance().getLeftEncoderVelocity());
        System.out.println("Right: " + DriveTrainTalon.getInstance().getRightEncoderVelocity());
        if(DriveTrainTalon.getInstance().getLeftEncoderVelocity() < -7) {
            atSpeed = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainFalcon.getInstance().customTankVelocity(0.2, 0.2);
        System.out.println("END");
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    private enum States {
        DRIVE_FORWARD, SPIN, STOP
    }
}