package com.github.mittyrobotics.LinearActuator;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LinearActuator extends SubsystemBase {
    private PWM actuator;
    private static LinearActuator instance;

    public LinearActuator() {}

    public static LinearActuator getInstance() {
        if(instance == null) {
            instance = new LinearActuator();
        }
        return instance;
    }

    public void initHardware() {
        actuator = new PWM(0);
    }

    public void set(double lengthValue) {
        actuator.setPosition(lengthValue);
    }

    public double get() {
        return actuator.getPosition();
    }

}
