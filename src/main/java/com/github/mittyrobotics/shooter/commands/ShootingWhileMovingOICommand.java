package com.github.mittyrobotics.shooter.commands;


import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootingWhileMovingOICommand extends CommandBase {


    public ShootingWhileMovingOICommand() {
        addRequirements(TurretSubsystem.getInstance(), ShooterSubsystem.getInstance());
    }

    /**
     * Initializes the starting states and setpoints
     */
    @Override
    public void initialize() {

    }

    /**
     * Runs state machine, setting motor speeds and updating ball counts depending on sensor values and assigned states
     */
    @Override
    public void execute() {
        ShooterSubsystem.getInstance().setShooterRpm(Autonomous.getInstance().getAutoShooterRPM());
        TurretSubsystem.getInstance().setMotor(Autonomous.getInstance().getAutoTurretOutput());
    }


    /**
     * Returns if the command should end
     *
     * @return false because this is a default command
     */
    @Override
    public boolean isFinished() {
        return !(OI.getInstance().getXboxController2().getTriggerAxis(GenericHID.Hand.kLeft) > 0.1);
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stopMotor();
        ShooterSubsystem.getInstance().setShooterRpm(0);
        ShooterSubsystem.getInstance().stopMotor();
    }

}
