package com.github.mittyrobotics.subsystems;

import com.github.mittyrobotics.constants.ClimberConstants;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WinchLockSubsystem extends SubsystemBase implements ISubsystem {
    private static WinchLockSubsystem instance;

    private PWM linearActuatorLeft, linearActuatorRight;

    public WinchLockSubsystem() {
        super();
        setName("Winch Lock");
    }

    public static WinchLockSubsystem getInstance() {
        if (instance == null) {
            instance = new WinchLockSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        linearActuatorLeft = new PWM(ClimberConstants.LEFT_ACTUATOR_ID);
        linearActuatorRight = new PWM(ClimberConstants.RIGHT_ACTATOR_ID);
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
