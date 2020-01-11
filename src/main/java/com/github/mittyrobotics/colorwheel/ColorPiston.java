package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.github.mittyrobotics.colorwheel.Constants.*;

public class ColorPiston extends SubsystemBase {

    private static ColorPiston instance;
    private DoubleSolenoid piston;

    private ColorPiston() {
        super();
        setName("Color Piston");
    }

    public static ColorPiston getInstance() {
        if(instance == null) {
            instance = new ColorPiston();
        }
        return instance;
    }

    public void initHardware() {
        piston = new DoubleSolenoid(SOLENOID_FOWARD_CHANNEL, SOLENOID_REVERSE_CHANNEL);

    }

    public void up(){
        piston.set(DoubleSolenoid.Value.kForward);
    }

    public void down(){
        piston.set(DoubleSolenoid.Value.kReverse);
    }
}
