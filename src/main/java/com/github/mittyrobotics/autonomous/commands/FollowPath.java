package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.units.Conversions;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.pathfollowing.PathFollower;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FollowPath extends CommandBase {
    private PathFollower follower;
    private double lastTime;


    public FollowPath(PathFollower follower, Path path) {
        addRequirements(DrivetrainSubsystem.getInstance());
        this.follower = follower;
        this.follower.setPath(path);
    }

    @Override
    public void initialize() {
        lastTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double time = Timer.getFPGATimestamp();
        double dt = time-lastTime;
        lastTime = time;
        DrivetrainState state = follower.updatePathFollower(
                RobotPositionTracker.getInstance().getFilterTransform().inToM(),
                DrivetrainState.fromWheelSpeeds(
                        DrivetrainSubsystem.getInstance().getLeftVelocity() * Conversions.IN_TO_M,
                        DrivetrainSubsystem.getInstance().getRightVelocity() * Conversions.IN_TO_M,
                        AutonConstants.DRIVETRAIN_TRACK_WIDTH * Conversions.IN_TO_M
                ),
                dt
        );
        DrivetrainSubsystem.getInstance().setVelocity(state.getLeft() * Conversions.M_TO_IN, state.getRight() * Conversions.M_TO_IN);
    }

    @Override
    public void end(boolean interrupted) {
        DrivetrainSubsystem.getInstance().setTankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return follower.getDistanceToEnd() < .1;
    }
}
