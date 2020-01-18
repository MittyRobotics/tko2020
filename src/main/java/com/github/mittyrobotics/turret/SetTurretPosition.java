package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetTurretPosition extends CommandBase {
    private int position;
    public SetTurretPosition(int position){
        super();
        this.position = position;
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void initialize(){
        TurretSubsystem.getInstance().setPosition(position);
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}