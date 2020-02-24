package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DropIntake extends CommandBase {


    public DropIntake() {
        super();
        addRequirements(IntakePiston.getInstance());
    }

    @Override
    public void initialize() {
        IntakePiston.getInstance().extendIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }


}
