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
        differentialDrive.joystickCarSteering(0, 0.8, false);
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
