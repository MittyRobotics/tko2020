package com.github.mittyrobotics.LinearActuator;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.Winch;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Actuator extends SubsystemBase {
    private static Actuator instance = new Actuator();
    private Servo testActuator;

    private Actuator(){
        super();
    }
    public static Actuator getInstance() {
        return instance;
    }

    public void initHardware(){
        testActuator = new Servo(ActuatorConstants.ActuatorID);
    }

    public void setPos(double position){
        testActuator.set(position);
    }

    public double getSolenoidValue(){
        return testActuator.get();
    }
}
