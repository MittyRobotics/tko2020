package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WinchLock extends SubsystemBase implements ISubsystem {
    private static WinchLock instance;

    private Servo linearActuatorLeft, linearActuatorRight;

    public static WinchLock getInstance(){
        if(instance == null){
            instance = new WinchLock();
        }
        return instance;
    }

    public WinchLock(){
        super();
        setName("Winch Lock");
    }

    @Override
    public void initHardware() {
        linearActuatorLeft = new Servo(Constants.LEFT_ACTUATOR_ID);
        linearActuatorRight = new Servo(Constants.RIGHT_ACTATOR_ID);
    }


    @Override
    public void updateDashboard() {

    }

    public void unlockWinch(){
        linearActuatorLeft.set(1);
        linearActuatorRight.set(1);
    }
    public void lockWinch(){
        linearActuatorLeft.set(0);
        linearActuatorRight.set(0);
    }
}
