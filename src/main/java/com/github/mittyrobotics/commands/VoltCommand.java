package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.Slider;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class VoltCommand extends CommandBase {

    private double voltz;
    private double startTime;

    public VoltCommand(boolean direction) {
        if (direction) {
            voltz = 0.25;
        } else {
            voltz = -0.25;
        }
    }
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        Slider.getInstance().setMotors(voltz);

    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime > 1000;
    }

    public void end (boolean interrupted) {
        Slider.getInstance().setMotors(0);
    }

}
