package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.autonomous.FieldRotation;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class AutomatedShooterControlLoop extends CommandBase{
    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     */
    public AutomatedShooterControlLoop(){
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
