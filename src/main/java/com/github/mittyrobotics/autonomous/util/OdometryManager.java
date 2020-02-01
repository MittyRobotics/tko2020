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

import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OdometryManager {

    private static OdometryManager instance = new OdometryManager();

    public static OdometryManager getInstance() {
        return instance;
    }

    public void run() {
        //Get left and right encoder positions and gyro heading
        double leftEncoderPosition = DriveTrainTalon.getInstance().getLeftEncoder();
        double rightEncoderPosition = DriveTrainTalon.getInstance().getRightEncoder();
        double heading = Gyro.getInstance().getAngle();

        //Update odometry
        Odometry.getInstance().update(leftEncoderPosition, rightEncoderPosition, heading);

        //Update smart dashboard numbers
        SmartDashboard.putNumber("robot_x", Odometry.getInstance().getRobotTransform().getPosition().getX());
        SmartDashboard.putNumber("robot_y", Odometry.getInstance().getRobotTransform().getPosition().getY());
        SmartDashboard
                .putNumber("robot_heading", Odometry.getInstance().getRobotTransform().getRotation().getHeading());
        SmartDashboard.putNumber("robot_vel_left", DriveTrainTalon.getInstance().getLeftEncoderVelocity());
        SmartDashboard.putNumber("robot_vel_right", DriveTrainTalon.getInstance().getRightEncoderVelocity());
    }
}
