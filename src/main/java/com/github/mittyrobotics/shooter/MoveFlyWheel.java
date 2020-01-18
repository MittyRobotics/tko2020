package com.github.mittyrobotics.shooter;

import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveFlyWheel extends CommandBase {
    public MoveFlyWheel(){
        super();
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void initialize(){ }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
