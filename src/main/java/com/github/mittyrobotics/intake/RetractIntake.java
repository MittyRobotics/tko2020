package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractIntake extends CommandBase {

    public RetractIntake() {
        super();
        addRequirements(IntakePiston.getInstance(), Intake.getInstance());
    }

    @Override
    public void initialize() {
        IntakePiston.getInstance().retractIntake();
        Intake.getInstance().stopWheel();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}