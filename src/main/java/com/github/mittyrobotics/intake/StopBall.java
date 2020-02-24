package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopBall extends CommandBase {


    public StopBall() {
        super();
        addRequirements(Intake.getInstance(), Conveyor.getInstance());
    }

    @Override
    public void initialize() {
        Intake.getInstance().stopWheel();
        Conveyor.getInstance().setConveyorSpeed(0);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}