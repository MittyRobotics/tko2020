package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeBall extends CommandBase {


    public IntakeBall() {
        super();
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Intake.getInstance().intakeBall();
    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
