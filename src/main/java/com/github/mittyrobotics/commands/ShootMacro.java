package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class ShootMacro extends ConditionalCommand {
    public ShootMacro() {
        super(new AutoMacroStuff(), new ManualShootMacro(),
                () -> OI.getInstance().getController2().getTriggerAxis(GenericHID.Hand.kLeft) > 0.5);
    }
}
