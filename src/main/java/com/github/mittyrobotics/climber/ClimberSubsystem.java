package com.github.mittyrobotics.climber;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberSubsystem extends SubsystemBase implements IMotorSubsystem {

    private static ClimberSubsystem instance;

    private PIDController controller;

    private PWM actuatorLock;
    private WPI_TalonSRX motor;
    private boolean drumUnlocked = false;
    private boolean climberRaised = false;

    public ClimberSubsystem() {
        setName("ClimberSubsystem");
    }

    public static ClimberSubsystem getInstance() {
        if(instance == null) {
            instance = new ClimberSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        actuatorLock = new PWM(ClimberConstants.ACTUATOR_ID);

        motor = new WPI_TalonSRX(ClimberConstants.MOTOR_ID);
        motor.configFactoryDefault();

        motor.setNeutralMode(ClimberConstants.CLIMBER_NEUTRAL_MODE);
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);


        controller = new PIDController(ClimberConstants.POSITION_P, ClimberConstants.POSITION_I, ClimberConstants.POSITION_D);

        resetEncoder();

        brake();

    }

    @Override
    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
    }

    @Override
    public double getPosition() {
        return motor.getSelectedSensorPosition();
    }

    public void raiseClimber() {
        controller.setSetpoint(ClimberConstants.CLIMBER_EXTENDED);
        setClimberRaised(true);
    }

    public void lowerClimber() {
        controller.setSetpoint(ClimberConstants.CLIMBER_DEEXTENDED);
        setClimberRaised(false);
    }

    public void runPositionPID() {
        double val = controller.calculate(ClimberSubsystem.getInstance().getPosition());
        overrideSetMotor(val);
    }

    public double getError() {
        return Math.abs(controller.getPositionError());
    }

    @Override
    public void overrideSetMotor(double percent) {
        if(drumUnlocked) {
            motor.set(percent);
        } else {
            brake();
        }
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Climber Raised", isClimberRaised());
        SmartDashboard.putNumber("Climber Encoder", getPosition());
    }

    public void setClimberRaised(boolean raised) {
        climberRaised = raised;
    }

    public boolean isClimberRaised() {
        return climberRaised;
    }

    public void brake() {
        motor.set(0);
    }

    public void unlockDrum() {
        drumUnlocked = true;
        actuatorLock.setRaw(1);
    }

    public void lockDrum() {
        drumUnlocked = false;
        actuatorLock.setRaw(-1);
    }

}