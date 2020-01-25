package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSwitches extends SubsystemBase {
    private static ConveyorSwitches instance;
    private DigitalInput entranceOpticalSwitch = new DigitalInput(com.github.mittyrobotics.Constants.ENTRANCE_OPTICAL_SWITCH);

    public static ConveyorSwitches getInstance(){
        if(instance == null){
            instance = new ConveyorSwitches();
        }
        return instance;
    }

    public void initHardware(){}

    public boolean getEntranceSwitch(){
        return entranceOpticalSwitch.get();
    }

}
