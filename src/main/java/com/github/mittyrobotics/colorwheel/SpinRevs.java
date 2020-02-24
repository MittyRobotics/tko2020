package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.drive.DriveTrainFalcon;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.REVS;

public class SpinRevs extends CommandBase {

    boolean done;
    private double initPos;

    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance(), DriveTrainFalcon.getInstance(), ColorPiston.getInstance());
    }

    @Override
    public void initialize() {
        //sets motor to fast velocity
        DriveTrainFalcon.getInstance().tankDrive(0.1, 0.1, 0, 1);
        System.out.println("Starting");
//        Spinner.getInstance().zeroEncoder();
        done = false;
        initPos = Spinner.getInstance().getRevolutions();

    }

    @Override
    public void execute() {
        //System.out.println(Spinner.getInstance().getEncoder());
        Spinner.getInstance().setMotorFast();

        if (Spinner.getInstance().getRevolutions() - initPos > REVS) {
            done = true;
            Spinner.getInstance().setMotorOff();
        }
    }

    @Override
    public void end(boolean interrupted) {
        //turns off motor, updates status
        Spinner.getInstance().setMotorOff();
        DriveTrainFalcon.getInstance().tankDrive(0, 0);
        ColorPiston.getInstance().down();
        System.out.println("END");
        //OI.getInstance().passedStage2();
    }

    @Override
    public boolean isFinished() {
        return done && !Spinner.getInstance().isSpinnerMoving();
    }
}