package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.Conveyor;
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
    public void execute() { //TODO Need to have else to make it stop
        if (Conveyor.getInstance().getTotalBallCount() < 4) {
            Intake.getInstance().intakeBall(0.5);
        } else {
            Intake.getInstance().intakeBall(0.2);
        }


    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
