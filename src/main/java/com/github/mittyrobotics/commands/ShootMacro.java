package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class ShootMacro extends ConditionalCommand {
    public ShootMacro() {
        super(new AutoShootMacro(), new ManualShootMacro(),
                () -> OI.getInstance().inAutoShootMode());
    }
}
