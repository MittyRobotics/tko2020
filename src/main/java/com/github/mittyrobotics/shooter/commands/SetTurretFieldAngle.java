package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetTurretFieldAngle extends CommandBase{

    /**
     * Angle to set the turret to
     */
    private final double angle;


    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     * @param angle angle to set turret to
     *
     */
    public SetTurretFieldAngle(double angle){
        this.angle = angle;
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
        System.out.println(Autonomous.getInstance().getTurretRotationFromFieldRotation(
                Rotation.fromDegrees(angle), Gyro.getInstance().getRotation()).getDegrees());
        TurretSubsystem.getInstance().setTurretAngle(Autonomous.getInstance().getTurretRotationFromFieldRotation(
                Rotation.fromDegrees(angle), Gyro.getInstance().getRotation()).getDegrees());
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
        return false;
    }
}
