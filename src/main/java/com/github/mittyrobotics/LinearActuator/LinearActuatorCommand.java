package com.GitHub.mittyrobotics.LinearActuator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LinearActuatorCommand extends CommandBase {
    @Override
    public void initialize() {
        LinearActuator.getInstance().initHardware(Constants.channel);
    }

    @Override
    public void execute() {
        //for(double i = 0.2; i < 0.8; i += 0.05) {
            LinearActuator.getInstance().set(.5);
            //Thread.sleep(10);
        //}

        //for(double i = 0.8; i > 0.2; i -= 0.05) {
            //LinearActuator.getInstance().set(i);
            //Thread.sleep(10);
        //}
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
