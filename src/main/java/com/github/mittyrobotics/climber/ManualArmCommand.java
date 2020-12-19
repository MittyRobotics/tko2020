package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ManualArmCommand extends RunCommand {
    public ManualArmCommand() {
        super(
                () -> ClimberSubsystem.getInstance().overrideSetMotor(OI.getInstance().getJoystick1().getY(), OI.getInstance().getJoystick2().getY()),
                ClimberSubsystem.getInstance());
    }
}
