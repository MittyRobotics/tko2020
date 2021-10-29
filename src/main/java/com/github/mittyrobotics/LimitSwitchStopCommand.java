package com.github.mittyrobotics;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimitSwitchStopCommand extends CommandBase {

    public LimitSwitchStopCommand() {
        addRequirements(Slider.getInstance());
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        if(OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft) > 0 && Slider.getInstance().getSwitch(1)) {
            Slider.getInstance().setMotors(OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft));
        } else if (OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft) < 0 && Slider.getInstance().getSwitch(0)) {
            Slider.getInstance().setMotors(OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft));
        } else if (Slider.getInstance().getSwitch(0) || Slider.getInstance().getSwitch(1)) {
            Slider.getInstance().setMotors(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
