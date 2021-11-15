package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.shooter.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets the {@link TurretSubsystem} angle
 */
public class SetTurretAngle extends CommandBase {

    /**
     * Angle to set the turret to
     */
    private final double angle;

    /**
     * Margin of error tolerated
     */
    private final double threshold;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     * @param angle angle to set turret to
     *
     * @param threshold margin of error allowed
     */
    public SetTurretAngle(double angle, double threshold){
        this.angle = angle;
        this.threshold = threshold;
        addRequirements(TurretSubsystem.getInstance());
    }

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     * Allows margin of error of 2 degrees
     *
     * @param angle angle to set turret to
     */
    public SetTurretAngle(double angle){
        this(angle, 2);
    }

    /**
     * Sets the turret setpoint
     */
    @Override
    public void initialize(){
        TurretSubsystem.getInstance().setTurretAngle(angle);
    }

    /**
     * Updates the turret position
     */
    @Override
    public void execute(){
        TurretSubsystem.getInstance().updateTurretControlLoop();
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
        return Math.abs(TurretSubsystem.getInstance().getError()) < threshold;
    }
}
