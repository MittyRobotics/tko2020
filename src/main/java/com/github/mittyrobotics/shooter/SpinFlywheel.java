package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinFlywheel extends CommandBase {
    private double speed, threshold;
    private double prevSpeed;
    private boolean hasPassed = false, hasPassed2 = true, hasPassed3 = false;
    private double t, t2;
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
//        if(Math.abs(ShooterSubsystem.getInstance().getShooterSpeed() - prevSpeed) > 100){
//            System.out.println("ERROR: " + (ShooterSubsystem.getInstance().getShooterSpeed() - prevSpeed));
//        }
//        prevSpeed = ShooterSubsystem.getInstance().getShooterSpeed();
//        if(ShooterSubsystem.getInstance().getShooterSpeed() > 3800 && !hasPassed){
//            hasPassed = true;
//        }
//        if(ShooterSubsystem.getInstance().getShooterSpeed() < 3800 && hasPassed){
//            t = Timer.getFPGATimestamp();
//            hasPassed2 = false;
//        }
//        if(ShooterSubsystem.getInstance().getShooterSpeed() > 3800 && !hasPassed2){
//            t2 = Timer.getFPGATimestamp() - t;
//            hasPassed3 = true;
//        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("TIME: " + t2);
    }

    @Override
    public boolean isFinished() {
        return DriverStation.getInstance().isDisabled();
    }
}
