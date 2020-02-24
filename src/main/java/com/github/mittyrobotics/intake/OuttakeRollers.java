package com.github.mittyrobotics.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuttakeRollers extends CommandBase {
    public OuttakeRollers(){
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize(){
        Intake.getInstance().outtakeBall();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
