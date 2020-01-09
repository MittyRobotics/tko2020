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
import com.github.mittyrobotics.path.following.controllers.PurePursuitController;
import com.github.mittyrobotics.path.following.controllers.RamseteController;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Translate2dTrajectory extends CommandBase {
    /**
     * The {@link PathFollower} to follow
     */
    private PathFollower pathFollower;
    /**
     * The previous recorded timestamp to get the delta time each execute function call
     */
    private double previousTime;

    /**
     * Constructs a new {@link Translate2dTrajectory} command to follow the {@link PathFollower}.
     *
     * @param pathFollower the {@link PathFollower} to follow
     */
    public Translate2dTrajectory(PathFollower pathFollower) {
        this.pathFollower = pathFollower;
    }


    /**
     * Constructs a new {@link Translate2dTrajectory} command to drive to the {@link Transform} <code>goal</code>
     * using the {@link PurePursuitController}.
     *
     * @param goal                  the {@link PathFollower} to follow
     * @param properties            the {@link PathFollowerProperties} to use in the {@link PathFollower}
     * @param purePursuitProperties the {@link PathFollowerProperties.PurePursuitProperties} to use in the
     *                              {@link PathFollower}
     */
    public Translate2dTrajectory(Transform goal, PathFollowerProperties properties,
                                 PathFollowerProperties.PurePursuitProperties purePursuitProperties) {
        PathFollower pathFollower = new PathFollower(properties, purePursuitProperties);
        pathFollower.setDrivingGoal(goal);
    }

    /**
     * Constructs a new {@link Translate2dTrajectory} command to drive to the {@link Transform} <code>goal</code>
     * using the {@link RamseteController}.
     *
     * @param goal              the {@link PathFollower} to follow
     * @param properties        the {@link PathFollowerProperties} to use in the {@link PathFollower}
     * @param ramseteProperties the {@link PathFollowerProperties.PurePursuitProperties} to use in the
     *                          {@link PathFollower}
     */
    public Translate2dTrajectory(Transform goal, PathFollowerProperties properties,
                                 PathFollowerProperties.RamseteProperties ramseteProperties) {
        PathFollower pathFollower = new PathFollower(properties, ramseteProperties);
        pathFollower.setDrivingGoal(goal);
    }

    /**
     * Initializes the {@link Translate2dTrajectory} command.
     * <p>
     * Sets the <code>previousTime</code> timestamp to the <code>getFPGATimestamp</code> from the {@link Timer} class.
     */
    @Override
    public void initialize() {
        previousTime = Timer.getFPGATimestamp();
    }

    /**
     * Execute function of the {@link Translate2dTrajectory} command. This is called periodically (every 20 ms by
     * default) while the command is running.
     * <p>
     * Gets the current <code>getFPGATimestamp</code> and finds the delta time from the previous timestamp
     * <code>previousTime</code>. Then it gets the robot's current {@link DrivetrainVelocities} from the {insert
     * drivetrain subsystem here} and inputs that along with the robot's current {@link Transform} from
     * {@link Odometry} and the delta time into the {@link PathFollower}. That outputs a new
     * {@link DrivetrainVelocities} to set the {insert drivetrain subsystem here} velocity setpoints to.
     */
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

    /**
     * Code to run at the end of the command.
     *
     * @param interrupted whether or not the command is interrupted.
     */
    @Override
    public void end(boolean interrupted) {
        //TODO: Set drivetrain velocities to the end velocities
    }

    /**
     * Returns whether or not the command is finished.
     *
     * It will return the {@link PathFollower} <code>isFinished()</code> command, which takes in the robot's current
     * {@link Transform} from {@link Odometry} and a distance tolerance to stop the command when within that distance
     * to the end position of the {@link Path}.
     *
     * In this case, it will return true if the robot {@link Transform} is within 2 inches of the end of the path.
     *
     * @return whether or not the command is finished.
     */
    @Override
    public boolean isFinished() {
        return pathFollower.isFinished(Odometry.getInstance().getRobotTransform(), 2);
    }

    /**
     * Returns the {@link PathFollower} that the {@link Translate2dTrajectory} command is following.
     *
     * @return the {@link PathFollower} that the {@link Translate2dTrajectory} command is following.
     */
    public PathFollower getPathFollower() {
        return pathFollower;
    }
}
