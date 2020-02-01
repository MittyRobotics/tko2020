package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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
        addRequirements(DriveTrainFalcon.getInstance());
    }

    @Override
    public void initialize() {
}


    @Override
    public void execute() {
        //WPI_TalonFX[] left = DriveTrainFalcon.getInstance().getLeftTalons();
        //WPI_TalonFX[] right = DriveTrainFalcon.getInstance().getRightTalons();

        //left[0].set(TalonFXControlMode.PercentOutput, OI.getInstance().getJoystick1().getY());

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
