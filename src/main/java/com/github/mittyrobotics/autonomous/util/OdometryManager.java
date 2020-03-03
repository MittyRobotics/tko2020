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

package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.util.interfaces.IDashboard;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OdometryManager implements IDashboard {

    private static OdometryManager instance;

    public static OdometryManager getInstance() {
        if(instance == null){
            instance = new OdometryManager();
        }
        return instance;
    }

    public void run() {
        //Get left and right encoder positions and gyro heading
        double leftEncoderPosition = DriveTrainSubsystem.getInstance().getLeftPosition();
        double rightEncoderPosition = DriveTrainSubsystem.getInstance().getRightPosition();
        double heading = Gyro.getInstance().getAngle();

        //Update Odometry
        Odometry.getInstance().update(leftEncoderPosition, rightEncoderPosition, heading, Timer.getFPGATimestamp());
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("odometry-x", Odometry.getInstance().getLatestRobotTransform().getPosition().getX());
        SmartDashboard.putNumber("odometry-y", Odometry.getInstance().getLatestRobotTransform().getPosition().getY());
        SmartDashboard
                .putNumber("odometry-heading",
                        Odometry.getInstance().getLatestRobotTransform().getRotation().getHeading());
        SmartDashboard
                .putNumber("odometry-velocity-x", Odometry.getInstance().getLatestRobotVelocity().getPosition().getX());
        SmartDashboard
                .putNumber("odometry-velocity-y", Odometry.getInstance().getLatestRobotVelocity().getPosition().getY());
        SmartDashboard.putNumber("odometry-velocity-heading",
                Odometry.getInstance().getLatestRobotVelocity().getRotation().getHeading());
    }
}
