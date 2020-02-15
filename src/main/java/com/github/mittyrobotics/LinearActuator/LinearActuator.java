package com.github.mittyrobotics.LinearActuator;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LinearActuator extends SubsystemBase {
    private Servo actuator;
    private static LinearActuator instance;

    public LinearActuator() {}

    public static LinearActuator getInstance() {
        if(instance == null) {
            instance = new LinearActuator();
        }
        return instance;
    }

    public void initHardware(int channel) {
        actuator = new Servo(channel);
    }

    public void set(double len) {
        actuator.set(len);
    }

    public double get() {
        return actuator.get();
    }

}
