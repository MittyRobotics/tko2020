package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractIntake extends CommandBase {

    public RetractIntake() {
        super();
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
        Intake.getInstance().retractIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}