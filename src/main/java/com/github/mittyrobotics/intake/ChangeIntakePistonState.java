package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ChangeIntakePistonState extends SequentialCommandGroup {
    public ChangeIntakePistonState() {
        if (IntakePiston.getInstance().isExtended()) {
            IntakePiston.getInstance().retractIntake();
        } else {
            IntakePiston.getInstance().extendIntake();
        }
    }
}
