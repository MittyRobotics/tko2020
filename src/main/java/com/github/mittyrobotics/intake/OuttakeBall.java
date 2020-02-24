package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuttakeBall extends CommandBase {

    public OuttakeBall() {
        super();
        addRequirements(Intake.getInstance(), Conveyor.getInstance());
    }

    @Override
    public void initialize() {
        Intake.getInstance().outtakeBall();
        Conveyor.getInstance().setConveyorSpeed(.2); //TODO find speed
        Conveyor.getInstance().setReverse(true);
    }

    @Override
    public void end(boolean interrupted){
        Conveyor.getInstance().setReverse(false);
    }
    @Override
    public boolean isFinished() {
        return false;
    }

}