package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualSpinWheel extends CommandBase {
    public ManualSpinWheel() {
        addRequirements(Spinner.getInstance());
    }

    @Override
    public void execute() {
        Spinner.getInstance().setSpinnerManual(OI.getInstance().getJoystick1().getX());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
