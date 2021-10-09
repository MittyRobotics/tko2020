package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.kinematics.DifferentialDriveState;
import com.github.mittyrobotics.core.math.spline.Path;
import com.github.mittyrobotics.motion.State;
import com.github.mittyrobotics.motion.profiles.PathTrajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;
import static com.github.mittyrobotics.motion.controllers.PurePursuitKt.purePursuit;

public class PathFollowingCommand extends CommandBase {

    private PathTrajectory trajectory;
    private double initialTime;
    private final double LOOKAHEADDISTANCE = inches(20.0);

    public PathFollowingCommand(PathTrajectory trajectory) {
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double dt = Timer.getFPGATimestamp() - initialTime;

        State state = trajectory.next(dt);
        Vector2D lookAheadPosition = trajectory.getTransform(LOOKAHEADDISTANCE).getVector();

        DifferentialDriveState dds = purePursuit()

        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
