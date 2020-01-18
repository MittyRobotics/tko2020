package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.Constants;
import edu.wpi.first.wpilibj.DigitalInput;

public class ConveyorSwitches {
    private static ConveyorSwitches instance;
    DigitalInput opticalSwitch1 = new DigitalInput(com.github.mittyrobotics.Constants.OPTICAL_SWITCH_1);
    DigitalInput opticalSwitch2 = new DigitalInput(Constants.OPTICAL_SWITCH_2);

    public static ConveyorSwitches getInstance(){
        if(instance == null){
            instance = new ConveyorSwitches();
        }
        return instance;
    }

    public void initHardware(){
    }

    public boolean getSwitch1(){
        return opticalSwitch1.get();
    }
    public boolean getSwitch2(){
        return opticalSwitch2.get();
    }
}
