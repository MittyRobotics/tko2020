package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuttakeBall extends CommandBase {

    public OuttakeBall() {
        super();
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
        Intake.getInstance().outtakeBall();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}