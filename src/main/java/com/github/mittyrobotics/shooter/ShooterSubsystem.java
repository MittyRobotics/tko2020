package com.github.mittyrobotics.shooter;


import com.github.mittyrobotics.motionprofile.util.datatypes.MechanismBounds;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class ShooterSubsystem extends SubsystemBase {

    private static ShooterSubsystem instance;
    private CANSparkMax spark1, spark2;
    private double bangSpeed = 0;
    private boolean inThreshold;
    public static double currentSetpoint;

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
        double f = 0.00019;
        double p = 0.0001;
        double d = 0.00001;

        spark1 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
        spark1.restoreFactoryDefaults();
        spark1.setInverted(true);
        spark1.getPIDController().setFF(f);
        spark1.getPIDController().setP(p);
        spark1.getPIDController().setD(d);

        spark2 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless); //TODO: find device id
        spark2.restoreFactoryDefaults();
        spark2.getPIDController().setFF(f);
        spark2.getPIDController().setP(p);
        spark2.getPIDController().setD(d);
    }

    public void manualControl(double speed) {
        if (Math.abs(speed) >= 0.05) {
            spark1.set(speed);
            spark2.set(speed);
            System.out.println("Spark speed: " + spark1.getEncoder().getVelocity());
        } else {
            spark1.stopMotor();
            spark2.stopMotor();
        }
        System.out.println("Joystick speed: " + speed);
    }

    public void setShooterSpeed(double setpoint) { //in rpm of the motors
        spark1.getPIDController().setReference(setpoint, ControlType.kVelocity);
        spark2.getPIDController().setReference(setpoint, ControlType.kVelocity);
        currentSetpoint = setpoint;
    }

    public void setPercent(double percent) { //in rpm of the motors
        spark1.set(percent);
        spark2.set(percent);
    }

    public void bangControl(double speed, double threshold) {
        if(!(Math.abs(speed - spark1.getEncoder().getVelocity()) < threshold)) {
            if ((spark1.getEncoder().getVelocity()) > speed) {
                bangSpeed -= Math.pow(MathUtil.clamp(
                        Math.abs(speed - spark1.getEncoder().getVelocity()) * .001, 0.02,
                        .04),2);
                bangSpeed = MathUtil.clamp(bangSpeed, -1, 1);
                spark1.set(bangSpeed);
                spark2.set(bangSpeed);
            } else if ((spark1.getEncoder().getVelocity()) < speed) {
                bangSpeed += Math.pow(MathUtil.clamp(Math.abs(speed - spark1.getEncoder().getVelocity()) * .0005,
                        0.02,
                        .04),2);
                bangSpeed = MathUtil.clamp(bangSpeed, -1, 1);
                spark1.set(bangSpeed);
                spark2.set(bangSpeed);
            }
        }
    }

    public double getShooterSpeed() {
        return (spark1.getEncoder().getVelocity() + spark2.getEncoder().getVelocity())/2;
    }
}
