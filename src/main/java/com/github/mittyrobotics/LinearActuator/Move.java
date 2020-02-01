package com.github.mittyrobotics.LinearActuator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Move extends CommandBase {
    private double position;

    @Override
    public void initialize(){
        Actuator.getInstance().initHardware();
    }

    @Override
    public void execute(){

        Actuator.getInstance().setPos(position);
    }


}
