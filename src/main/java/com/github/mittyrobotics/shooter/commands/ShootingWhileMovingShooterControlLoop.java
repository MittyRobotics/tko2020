package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootingWhileMovingShooterControlLoop extends CommandBase {
    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     */
    public ShootingWhileMovingShooterControlLoop(){
        addRequirements(ShooterSubsystem.getInstance());
    }

    /**
     * Sets the turret setpoint
     */
    @Override
    public void initialize(){
    }

    /**
     * Updates the turret position
     */
    @Override
    public void execute(){
        ShooterSubsystem.getInstance().setShooterRpm(Autonomous.getInstance().getAutoShooterRPM());
    }

    /**
     * Stops the turret from moving
     */
    @Override
    public void end(boolean interrupted){
        ShooterSubsystem.getInstance().stopMotor();
    }

    /**
     * Returns if the command should end
     *
     * @return the error is within the threshold
     */
    @Override
    public boolean isFinished(){
        return false;
    }
}