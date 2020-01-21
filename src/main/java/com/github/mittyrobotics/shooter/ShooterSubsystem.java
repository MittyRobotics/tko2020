package com.github.mittyrobotics.shooter;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private static ShooterSubsystem instance;
    private CANSparkMax spark1;
    private double bangSpeed = 0;

    private ShooterSubsystem() {
        super();
        setName("Shooter");
    }

    public static ShooterSubsystem getInstance() {
        if (instance == null) {
            instance = new ShooterSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        spark1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        spark1.restoreFactoryDefaults();
        spark1.getPIDController().setP(Constants.ShooterP);
        spark1.getPIDController().setI(Constants.ShooterI);
        spark1.getPIDController().setD(Constants.ShooterD);
        spark1.getPIDController().setOutputRange(Constants.ShooterOutputMin, Constants.ShooterOutputMax);
    }

    public void manualControl(double speed) {
        if (Math.abs(speed) >= 0.05) {
            spark1.set(speed);
        } else {
            spark1.stopMotor();
        }
    }

    public void setShooterSpeed(double speed) { //in rpm of the motor
        spark1.getPIDController().setReference(speed, ControlType.kVelocity);
    }

    public void bangControl(double speed, double threshold) {
        if (!(Math.abs(speed - spark1.getEncoder().getVelocity()) < threshold)) {
            if ((spark1.getEncoder().getVelocity()) > speed) {
                bangSpeed -= .01;
            } else if ((spark1.getEncoder().getVelocity()) < speed) {
                bangSpeed += .01;
            }
        }
        bangSpeed = Math.max(-1, Math.min(1, bangSpeed));
        spark1.set(bangSpeed);
    }
}
