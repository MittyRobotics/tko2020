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
//        ShooterSubsystem.getInstance().setShooterSpeed(speed);
        ShooterSubsystem.getInstance().bangControl(speed, threshold);
        System.out.println("RPM: " + ShooterSubsystem.getInstance().getShooterSpeed());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
