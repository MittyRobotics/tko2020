package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetTurretEncoder extends CommandBase {
    private double position; //TODO what is the purpose of position?
    private boolean isDone = false;
    public ResetTurretEncoder(double position){
        super();
        this.position = position;
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void execute() {
        if (!TurretSubsystem.getInstance().limitSwitchValue()) {
            TurretSubsystem.getInstance().setTurretSpeed(-.2);
        } else {
            TurretSubsystem.getInstance().setTurretSpeed(0);
            TurretSubsystem.getInstance().setPosition(0);
            isDone = true;
        }
    }
    @Override
    public boolean isFinished(){
        return isDone;
    }



}
