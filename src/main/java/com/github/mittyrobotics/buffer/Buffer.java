package com.github.mittyrobotics.buffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Buffer extends SubsystemBase implements ISubsystem {
    private static Buffer instance;
    private WPI_TalonSRX bufferWheel;
    //TODO not sure if we will use these
    private boolean isOptimalSpeed = true; //TODO: Assign value properly when merging
    private boolean isOptimalAngle = true; //TODO: Assign value properly when merging

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
        bufferWheel = new WPI_TalonSRX(Constants.TalonID1);
        bufferWheel.setInverted(true);
        bufferWheel.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    @Override
    public void updateDashboard() {

    }

    public void bufferLock(double speed) {
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

    public void bufferRelease(double speed) {
        bufferWheel.set(ControlMode.PercentOutput, speed);
    }

    public boolean isOptimalAngle() {
        return isOptimalAngle;
    }

    public boolean isOptimalSpeed() {
        return isOptimalSpeed;
    }

    public WPI_TalonSRX getBufferWheel() {
        return getBufferWheel();
    }

    public double getBufferPosition() {
        return bufferWheel.getSelectedSensorPosition();
    }
}
