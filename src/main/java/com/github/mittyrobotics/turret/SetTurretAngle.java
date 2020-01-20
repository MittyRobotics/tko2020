package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetTurretAngle extends CommandBase {
    private double angle;

    public SetTurretAngle(double angle) {
        super();
        this.angle = angle;
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        TurretSubsystem.getInstance().setAngle(angle);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}