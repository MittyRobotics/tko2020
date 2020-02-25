package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ColorPistonSubsystem;
import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.constants.ColorWheelConstants.REVS;

public class SpinRevsCommand extends CommandBase {

    boolean done;
    private double initPos;

    public SpinRevsCommand() {
        super();
        addRequirements(SpinnerSubsystem.getInstance(), DriveTrainSubsystem.getInstance(), ColorPistonSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        //sets motor to fast velocity
        DriveTrainSubsystem.getInstance().tankDrive(0.1, 0.1, 0, 1);
        System.out.println("Starting");
//        Spinner.getInstance().zeroEncoder();
        done = false;
        initPos = SpinnerSubsystem.getInstance().getRevolutions();

    }

    @Override
    public void execute() {
        //System.out.println(Spinner.getInstance().getEncoder());
        SpinnerSubsystem.getInstance().setMotorFast();

        if (SpinnerSubsystem.getInstance().getRevolutions() - initPos > REVS) {
            done = true;
            SpinnerSubsystem.getInstance().setMotorOff();
        }
    }

    @Override
    public void end(boolean interrupted) {
        //turns off motor, updates status
        SpinnerSubsystem.getInstance().setMotorOff();
        DriveTrainSubsystem.getInstance().tankDrive(0, 0);
        ColorPistonSubsystem.getInstance().down();
        System.out.println("END");
        //OI.getInstance().passedStage2();
    }

    @Override
    public boolean isFinished() {
        return done && !SpinnerSubsystem.getInstance().isSpinnerMoving();
    }
}