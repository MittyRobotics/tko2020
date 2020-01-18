package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinFlywheel extends CommandBase {
    public SpinFlywheel(){
        super();
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize(){ }

    @Override
    public void execute(){
        ShooterSubsystem.getInstance().setShooterSpeed(Constants.ShooterSpeed);
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
