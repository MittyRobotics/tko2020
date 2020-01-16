package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.controls.TKODifferentialDrive;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase {
    CANSparkMax left;
    CANSparkMax right;
    TKODifferentialDrive differentialDrive;
    public Drive() {
        super();
        addRequirements(DriveTrainSparks.getInstance());
    }

    @Override
    public void initialize() {
        left = DriveTrainSparks.getInstance().leftSpark1;
        right = DriveTrainSparks.getInstance().rightSpark1;
        differentialDrive = new TKODifferentialDrive(left, right);
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
