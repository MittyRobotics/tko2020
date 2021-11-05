package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.OI.OI;
import com.github.mittyrobotics.subsystems.Slider;
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
        double val = OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft)*0.5;
        Slider.getInstance().motorsWithLimitSwitch(val);
//        System.out.println(Slider.getInstance().getMotors()[1].getSelectedSensorPosition());

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
