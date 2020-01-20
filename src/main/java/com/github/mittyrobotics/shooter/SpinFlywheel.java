package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinFlywheel extends CommandBase {
    private double speed, threshold;
    public SpinFlywheel(double speed, double threshold) {
        super();
        this.speed = speed;
        this.threshold = threshold;
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ShooterSubsystem.getInstance().setShooterSpeed(speed); //TODO test both (only use 1)
        ShooterSubsystem.getInstance().bangControl(speed, threshold);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    } //TODO make a condition
}
