package com.github.mittyrobotics.conveyor2;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConveyorJoystickCommand extends RunCommand {
    public ConveyorJoystickCommand() {
        super(()->Conveyor2Subsystem.getInstance().setMotor(OI.getInstance().getJoystick1().getX()), Conveyor2Subsystem.getInstance());
    }
}
