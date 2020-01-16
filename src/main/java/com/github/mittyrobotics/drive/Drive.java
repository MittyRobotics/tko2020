package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.controls.TKODifferentialDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase {
    SpeedController left;
    SpeedController right;
    TKODifferentialDrive differentialDrive = new TKODifferentialDrive(left, right);
    public Drive() {
        super();
        addRequirements(DriveTrainSparks.getInstance());
    }

    @Override
    public void initialize() {
        left = DriveTrainSparks.getInstance().leftSpark1;
        right = DriveTrainSparks.getInstance().rightSpark1;

        differentialDrive.carDriveCarSteering(0.5, false, false, false, 0);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
