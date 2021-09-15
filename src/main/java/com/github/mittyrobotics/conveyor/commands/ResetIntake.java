package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeRaiseSubsystem;

public class ResetIntake {

    private static ResetIntake instance;

    private boolean ended;

    public static ResetIntake getInstance() {
        if(instance == null) {
            instance = new ResetIntake();
        }
        return instance;
    }

    public void init() {
        IntakeRaiseSubsystem.getInstance().raiseIntake();
    }

    public void run() {
        IntakeRaiseSubsystem.getInstance().overrideSetMotor(IntakeConstants.INTAKE_MANUAL_RAISE_SPEED);
    }

    public void end() {
        if(!ended) {
            IntakeRaiseSubsystem.getInstance().stop();
            IntakeRaiseSubsystem.getInstance().resetEncoder();
            ended = true;
        }
    }

    public boolean getSwitch() {
        return IntakeRaiseSubsystem.getInstance().getSwitch(1);
    }
}
