package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Limelight;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.State;
import com.github.mittyrobotics.motion.profiles.TrapezoidalMotionProfile;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PIDDrive  extends CommandBase {
    private TrapezoidalMotionProfile motionProfile;
    private double startTime;
    private double time;
    private double distance;
    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link ConveyorSubsystem}
     */
    public PIDDrive(double distance) {
        addRequirements(DrivetrainSubsystem.getInstance());
        this.distance = distance;
    }


    /**
     * Initializes the starting states and setpoints
     */
    @Override
    public void initialize() {
        motionProfile = new TrapezoidalMotionProfile(new State(0.0, 0.0), new State(distance, 0.0), new State(100.0, 100.0), new State(100.0, 100.0));
        startTime = Timer.getFPGATimestamp();
    }

    /**
     * Runs state machine, setting motor speeds and updating ball counts depending on sensor values and assigned states
     */
    @Override
    public void execute() {
        time = Timer.getFPGATimestamp() - startTime;
        State state = motionProfile.getStateAtTime(time);
        double position = state.get(0);
        double velocity = state.get(1);
        DrivetrainSubsystem.getInstance().tankVelocity(velocity, velocity);
    }

    /**
     * Returns if the command should end
     *
     * @return false because this is a default command
     */
    @Override
    public boolean isFinished() {
        return time >= motionProfile.getTotalTime();
    }

    @Override
    public void end(boolean interrupted) {

    }

}
