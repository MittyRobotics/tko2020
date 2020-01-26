package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainFalcon extends SubsystemBase {
    private WPI_TalonFX[] leftDrive = new WPI_TalonFX[2];
    private WPI_TalonFX[] rightDrive = new WPI_TalonFX[2];

    private static DriveTrainFalcon instance;
    public static DriveTrainFalcon getInstance() {
        if(instance == null){
            instance = new DriveTrainFalcon();
        }
        return instance;
    }

    private DriveTrainFalcon() {
        super();
        setName("Drive Train Falcon");
    }

    @Override
    public void periodic() {

    }

    public void initHardware() {

        leftDrive[0] = new WPI_TalonFX(0);
        leftDrive[1] = new WPI_TalonFX(1);
        rightDrive[0] = new WPI_TalonFX(2);
        rightDrive[1] = new WPI_TalonFX(3);

        leftDrive[0].setInverted(true);
        leftDrive[1].setInverted(true);


    }

    public WPI_TalonFX[] getLeftTalons() {
        return leftDrive;
    }

    public WPI_TalonFX[] getRightTalons() {
        return rightDrive;
    }

    public void tankDrive(double left, double right){
        if(Math.abs(left) > 0.1) {
            leftDrive[0].set(TalonFXControlMode.PercentOutput, left);
            leftDrive[1].set(TalonFXControlMode.PercentOutput, left);
        } else {
            leftDrive[0].set(TalonFXControlMode.PercentOutput, 0);
            leftDrive[1].set(TalonFXControlMode.PercentOutput, 0);
        }
        if(Math.abs(right) > 0.1){
            rightDrive[0].set(TalonFXControlMode.PercentOutput, right);
            rightDrive[1].set(TalonFXControlMode.PercentOutput, right);
        }  else {
            rightDrive[0].set(TalonFXControlMode.PercentOutput, 0);
            rightDrive[1].set(TalonFXControlMode.PercentOutput, 0);
        }
    }
}
