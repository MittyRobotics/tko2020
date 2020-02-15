/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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

package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.datatypes.motion.DrivetrainVelocities;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import edu.wpi.first.wpilibj.Timer;

public class AutonDriver {
    private static AutonDriver instance = new AutonDriver();
    private PathFollower pathFollower;
    private boolean finishedPath;
    private boolean disabled;
    private double previousTime;

    public static AutonDriver getInstance() {
        return instance;
    }

    private AutonDriver() {
        this.finishedPath = true;
        this.previousTime = 0;
    }

    public void initNewPathFollower(PathFollower pathFollower) {
        this.pathFollower = pathFollower;
    }

    public void initNewPathFollower(PathFollowerProperties properties,
                                    PathFollowerProperties.PurePursuitProperties purePursuitProperties) {
        this.pathFollower = new PathFollower(properties, purePursuitProperties);
    }

    public void initNewPathFollower(PathFollowerProperties properties,
                                    PathFollowerProperties.RamseteProperties ramseteProperties) {
        this.pathFollower = new PathFollower(properties, ramseteProperties);
    }

    public void setReversed(boolean reversed) {
//        PathFollowerProperties properties =
//                new PathFollowerProperties(pathFollower.getProperties().velocityController, reversed,
//                        pathFollower.getProperties().continuouslyAdaptivePath);
//
//        if (pathFollower.getPurePursuitProperties() != null) {
//            PathFollowerProperties.PurePursuitProperties purePursuitProperties =
//                    pathFollower.getPurePursuitProperties();
//
//            this.pathFollower = new PathFollower(properties, purePursuitProperties);
//        } else if (pathFollower.getRamseteProperties() != null) {
//            PathFollowerProperties.RamseteProperties ramseteProperties =
//                    pathFollower.getRamseteProperties();
//
//            this.pathFollower = new PathFollower(properties, ramseteProperties);
//        }
    }

    public void setPath(Path path) {
        pathFollower.setPath(path);
        finishedPath = false;
        disabled = false;
    }

    public void setGoalTransform(Transform goal) {
        pathFollower.setDrivingGoal(goal);
        finishedPath = false;
        disabled = false;
    }

    public void initAutonDriver(){
        this.previousTime = 0;
    }

    public void run() {
        if (!disabled) {
            double currentTime = Timer.getFPGATimestamp();
            double deltaTime = currentTime - previousTime;
            this.previousTime = currentTime;
            if (!isFinishedPath()) {
                updatePathFollower(deltaTime);
                this.finishedPath = pathFollower.isFinished(4);
            } else {
                DriveTrainFalcon.getInstance().tankDrive(0, 0);
            }
        }
    }

    public void disableAutonDriver() {
        DriveTrainFalcon.getInstance().tankDrive(0, 0);
        this.pathFollower = null;
        this.finishedPath = true;
        this.disabled = true;
    }

    private void updatePathFollower(double deltaTime) {
        DrivetrainVelocities currentVelocities = DrivetrainVelocities.calculateFromWheelVelocities(
                DriveTrainFalcon.getInstance().getLeftEncoderVelocity(),
                DriveTrainFalcon.getInstance().getRightEncoderVelocity()
        );

        DrivetrainVelocities output = pathFollower.updatePathFollower(Odometry.getInstance().getRobotTransform()
                , currentVelocities, deltaTime);

        DriveTrainFalcon.getInstance().customTankVelocity(output.getLeftVelocity(), output.getRightVelocity());
    }

    public double getRoughDistanceToEnd() {
        return pathFollower.getCurrentDistanceToEnd();
    }

    public boolean isFinishedPath() {
        return finishedPath;
    }
}
