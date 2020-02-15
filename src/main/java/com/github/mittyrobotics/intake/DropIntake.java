package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DropIntake extends CommandBase {


    public DropIntake() {
        super();
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
        Intake.getInstance().extendIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }


}
