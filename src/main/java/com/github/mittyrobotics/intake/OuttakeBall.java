package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuttakeBall extends CommandBase {


    public OuttakeBall() {
        super();
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Intake.getInstance().intakeBall(-.5);
        Conveyor.getInstance().updateBallCount(-1);
    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
