package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.BufferConstants;
import com.github.mittyrobotics.commands.LockBallCommand;
import com.github.mittyrobotics.util.interfaces.ISubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BufferSubsystem extends SubsystemBase implements ISubsystem {
    private static BufferSubsystem instance;
    private WPI_TalonSRX bufferWheel;

    private BufferSubsystem() {
        super();
        setName("Buffer");
    }

    public static BufferSubsystem getInstance() {
        if (instance == null) {
            instance = new BufferSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        bufferWheel = new WPI_TalonSRX(BufferConstants.BUFFER_WHEEL_ID);
        bufferWheel.configFactoryDefault();
        bufferWheel.setInverted(BufferConstants.BUFFER_WHEEL_INVERSION);
        bufferWheel.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        bufferWheel.setSensorPhase(BufferConstants.BUFFER_WHEEL_ENCODER_INVERSION);
        setDefaultCommand(new LockBallCommand());
    }

    public void resetEncoder() {
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
//            System.out.println("Buffer Percent Output: " + speed);
        } else {
            bufferWheel.set(ControlMode.PercentOutput, 0);
        }

    }

    public double getBufferPosition() {
        return bufferWheel.getSelectedSensorPosition();
    }

    public void bufferLock() {
        moveWheel(BufferConstants.LOCK_SPEED);
    }

    public void bufferRelease() {
        moveWheel(BufferConstants.RELEASE_SPEED);
    }
}