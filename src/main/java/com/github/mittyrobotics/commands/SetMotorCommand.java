package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.OI.OI;
import com.github.mittyrobotics.subsystems.Slider;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetMotorCommand extends InstantCommand {
    public SetMotorCommand() {
        super(() -> Slider.getInstance().setMotors(OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft)), Slider.getInstance());
    }
}
