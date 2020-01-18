package com.github.mittyrobotics.buffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BufferSubsystem extends SubsystemBase {
    private WPI_TalonSRX talon1, talon2;

    private boolean isOptimalSpeed = true; //TODO: Assign value properly when merging
    private boolean isOptimalAngle = true; //TODO: Assign value properly when merging

    private static BufferSubsystem instance;
    public static BufferSubsystem getInstance(){
        if(instance == null){
            instance = new BufferSubsystem();
        }
        return instance;
    }
    private BufferSubsystem(){
        super();
        setName("Buffer");
    }

    public void initHardware(){
        talon1 = new WPI_TalonSRX(com.github.mittyrobotics.buffer.Constants.TalonID1);
        talon2 = new WPI_TalonSRX(com.github.mittyrobotics.buffer.Constants.TalonID2);
    }

    public void bufferLock(double speed) {
        talon1.set(ControlMode.PercentOutput, speed);
        talon2.set(ControlMode.PercentOutput, speed);
    }

    public void bufferRelease(double speed) {
        talon1.set(ControlMode.PercentOutput, speed);
        talon2.set(ControlMode.PercentOutput, -speed);
    }

    public boolean isOptimalAngle() { return isOptimalAngle; }
    public boolean isOptimalSpeed() { return isOptimalSpeed; }
}
