package com.github.mittyrobotics.NewClimber;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ManualArmCommand extends RunCommand {
    public ManualArmCommand() {
        super(
                () -> ClimberSubsystem.getInstance().setSparks(OI.getInstance().getJoystick1().getY(), OI.getInstance().getJoystick2().getY()),
                ClimberSubsystem.getInstance());
    }
}
