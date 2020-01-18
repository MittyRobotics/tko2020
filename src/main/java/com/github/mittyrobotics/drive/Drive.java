package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.Gyro;
import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.controls.TKODifferentialDrive;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase {
    TKODifferentialDrive differentialDrive;
    public Drive() {
        super();
        addRequirements(DriveTrainSparks.getInstance());
    }

    @Override
    public void initialize() {
        differentialDrive = new TKODifferentialDrive(DriveTrainSparks.getInstance().leftSpark1, DriveTrainSparks.getInstance().rightSpark1);
}


    @Override
    public void execute() {
        differentialDrive.carDriveCompassSteering(OI.getInstance().getXboxWheel().getX()/3, Gyro.getInstance().getAngle(),OI.getInstance().getXboxWheel().getBumper(GenericHID.Hand.kLeft), OI.getInstance().getXboxWheel().getBumper(GenericHID.Hand.kRight), OI.getInstance().getXboxWheel().getBButton());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
