/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.datatypes.motion.DrivetrainVelocities;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Translate2dTrajectory extends CommandBase {
    private PathFollower pathFollower;
    private double previousTime;

    public Translate2dTrajectory(PathFollower pathFollower) {
        this.pathFollower = pathFollower;
    }

    public Translate2dTrajectory(Transform goal, PathFollowerProperties properties,
                                 PathFollowerProperties.PurePursuitProperties purePursuitProperties) {
        PathFollower pathFollower = new PathFollower(properties, purePursuitProperties);
        pathFollower.setDrivingGoal(goal);
    }

    public Translate2dTrajectory(Transform goal, PathFollowerProperties properties,
                                 PathFollowerProperties.RamseteProperties ramseteProperties) {
        PathFollower pathFollower = new PathFollower(properties, ramseteProperties);
        pathFollower.setDrivingGoal(goal);
    }

    @Override
    public void initialize() {
        previousTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double currentTime = Timer.getFPGATimestamp();
        double deltaTime = currentTime - previousTime;
        //TODO: Set current velocities to drivetrain velocities
        DrivetrainVelocities currentVelocities = DrivetrainVelocities.empty();
        DrivetrainVelocities output = pathFollower.updatePathFollower(Odometry.getInstance().getRobotTransform()
                , currentVelocities, deltaTime);
        //TODO: Set drivetrain velocities to output
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return pathFollower.isFinished(Odometry.getInstance().getRobotTransform(), 2);
    }

    public PathFollower getPathFollower() {
        return pathFollower;
    }
}
