package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootingWhileMovingTurretControlLoop extends CommandBase {
    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     */
    public ShootingWhileMovingTurretControlLoop(){
        addRequirements(TurretSubsystem.getInstance());
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
        TurretSubsystem.getInstance().setMotor(Autonomous.getInstance().getAutoTurretOutput());
        SmartDashboard.putNumber("turret-control-loop", Autonomous.getInstance().getAutoTurretOutput());
    }

    /**
     * Stops the turret from moving
     */
    @Override
    public void end(boolean interrupted){
        TurretSubsystem.getInstance().stopMotor();
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