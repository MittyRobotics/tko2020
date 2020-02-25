package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.IntakePiston;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChangeIntakePistonCommand extends CommandBase {
    public ChangeIntakePistonCommand() {
        super();
        addRequirements(IntakePiston.getInstance());
    }
    @Override
    public void initialize(){
        if (IntakePiston.getInstance().isExtended()) {
            IntakePiston.getInstance().retractIntake();
        } else {
            IntakePiston.getInstance().extendIntake();
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
