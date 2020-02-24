package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WinchLock extends SubsystemBase implements ISubsystem {
    private static WinchLock instance;

    private PWM linearActuatorLeft, linearActuatorRight;

    public WinchLock() {
        super();
        setName("Winch Lock");
    }

    public static WinchLock getInstance() {
        if (instance == null) {
            instance = new WinchLock();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        linearActuatorLeft = new PWM(Constants.LEFT_ACTUATOR_ID);
        linearActuatorRight = new PWM(Constants.RIGHT_ACTATOR_ID);
    }


    @Override
    public void updateDashboard() {

    }

    public void unlockWinch() {
        linearActuatorLeft.setPosition(1);
        linearActuatorRight.setPosition(1);
    }

    public void lockWinch() {
        linearActuatorLeft.setPosition(0);
        linearActuatorRight.setPosition(0);
    }
}
