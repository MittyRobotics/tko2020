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
        spark1 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
        spark1.restoreFactoryDefaults();
        spark1.getPIDController().setFF(0.00017822 );
        spark1.getPIDController().setP(0.000001);
        spark1.getPIDController().setD(0.0000001);

        spark2 = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless); //TODO: find device id
        spark2.restoreFactoryDefaults();
        spark2.setInverted(true);
        spark2.getPIDController().setFF(0.00017822 );
        spark2.getPIDController().setP(0.000001);
        spark2.getPIDController().setD(0.0000001);

        //        spark1.getPIDController().setOutputRange(Constants.ShooterOutputMin, Constants.ShooterOutputMax);
       // pF(3500);

        setShooterSpeed(3500);

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

    public void pF(double setpoint) {

        double error = setpoint - getShooterSpeed();

        double ff = 0.00017822*setpoint;
        double fb = 0.000001*error;
        spark1.set(ff+fb);
        spark2.set(ff+fb);
        currentSetpoint = setpoint;

    }


    public void setShooterSpeed(double setpoint) { //in rpm of the motors
        spark1.getPIDController().setReference(setpoint, ControlType.kVelocity);
        spark2.getPIDController().setReference(setpoint, ControlType.kVelocity);
        currentSetpoint = setpoint;
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
        return spark1.getEncoder().getVelocity();
    }
}
