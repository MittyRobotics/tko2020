package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.datatypes.motion.DrivetrainVelocities;
import com.github.mittyrobotics.datatypes.motion.DrivetrainWheelVelocities;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Translate2DTrajectory extends CommandBase {
    private PathFollower pathFollower;
    private double previousTime;

    public Translate2DTrajectory(PathFollower pathFollower){
        this.pathFollower = pathFollower;
    }

    public Translate2DTrajectory(Transform goal, PathFollowerProperties properties,
                                 PathFollowerProperties.PurePursuitProperties purePursuitProperties){
        PathFollower pathFollower = new PathFollower(properties,purePursuitProperties);
        pathFollower.setDrivingGoal(goal);
    }

    public Translate2DTrajectory(Transform goal, PathFollowerProperties properties,
                                 PathFollowerProperties.RamseteProperties ramseteProperties){
        PathFollower pathFollower = new PathFollower(properties,ramseteProperties);
        pathFollower.setDrivingGoal(goal);
    }

    @Override
    public void initialize() {
        previousTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double currentTime = Timer.getFPGATimestamp();
        double deltaTime = currentTime-previousTime;
        //TODO: Set current velocities to drivetrain velocities
        DrivetrainVelocities currentVelocities = DrivetrainVelocities.empty();
        DrivetrainVelocities output = pathFollower.updatePathFollower(Odometry.getInstance().getRobotTransform()
                ,currentVelocities,deltaTime);
        //TODO: Set drivetrain velocities to output
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return pathFollower.isFinished(Odometry.getInstance().getRobotTransform(),2);
    }

    public PathFollower getPathFollower() {
        return pathFollower;
    }
}
