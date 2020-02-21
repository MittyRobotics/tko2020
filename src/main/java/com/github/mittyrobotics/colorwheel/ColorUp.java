package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ColorUp extends CommandBase {
    public ColorUp(){
        addRequirements(ColorPiston.getInstance());
    }

    @Override
    public void initialize() {
        ColorPiston.getInstance().up();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
