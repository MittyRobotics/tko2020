package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopBall extends CommandBase {


    public StopBall() {
        super();
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() { //TODO Need to have else to make it stop
        Intake.getInstance().intakeBall(0);


    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
