package com.github.mittyrobotics.buffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Buffer extends SubsystemBase implements ISubsystem {
    private static Buffer instance;
    private WPI_TalonSRX bufferWheel;

    private Buffer() {
        super();
        setName("Buffer");
    }

    public static Buffer getInstance() {
        if (instance == null) {
            instance = new Buffer();
        }
        return instance;
    }

    public void initHardware() {
        bufferWheel = new WPI_TalonSRX(Constants.BUFFER_WHEEL_ID);
        bufferWheel.setInverted(Constants.BUFFER_WHEEL_INVERSION);
        bufferWheel.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        bufferWheel.setSensorPhase(Constants.BUFFER_WHEEL_ENCODER_INVERSION);
        setDefaultCommand(new LockBall());
    }

    public void resetEncoder(){
        bufferWheel.setSelectedSensorPosition(0);
    }

    @Override
    public void updateDashboard() {

    }

    private void moveWheel(double speed) {
        bufferWheel.set(ControlMode.PercentOutput, speed);

    }

    public void manualBufferSpeed(double speed) {
        if (Math.abs(speed) > 0.1) {
            bufferWheel.set(ControlMode.PercentOutput, speed);
            System.out.println("Buffer Percent Output: " + speed);
        } else {
            bufferWheel.set(ControlMode.PercentOutput, 0);
        }

    }

    public double getBufferPosition() {
        return bufferWheel.getSelectedSensorPosition();
    }

    public void bufferLock(){
        moveWheel(Constants.LOCK_SPEED);
    }

    public void bufferRelease(){
        moveWheel(Constants.RELEASE_SPEED);
    }
}