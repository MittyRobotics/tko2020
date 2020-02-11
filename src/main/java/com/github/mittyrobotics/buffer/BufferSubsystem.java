package com.github.mittyrobotics.buffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BufferSubsystem extends SubsystemBase {
    private WPI_TalonSRX bufferWheel;

    //TODO not sure if we will use these
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

    public void initHardware(){ //TODO add encoder
        bufferWheel = new WPI_TalonSRX(Constants.TalonID1);

    }

    public void bufferLock(double speed) {
        bufferWheel.set(ControlMode.PercentOutput, speed);

    }

    public void bufferRelease(double speed) {
        bufferWheel.set(ControlMode.PercentOutput, speed);
    }

    public boolean isOptimalAngle() { return isOptimalAngle; }
    public boolean isOptimalSpeed() { return isOptimalSpeed; }
}
