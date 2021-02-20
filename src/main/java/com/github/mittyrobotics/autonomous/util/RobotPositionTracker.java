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
import com.github.mittyrobotics.datatypes.CircularTimestampedList;
import com.github.mittyrobotics.datatypes.TimestampedElement;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.observers.Odometry;
import com.github.mittyrobotics.util.Gyro;
import org.opencv.video.KalmanFilter;

public class RobotPositionTracker {
    private static RobotPositionTracker instance;
    public static RobotPositionTracker getInstance() {
        if (instance == null) {
            instance = new RobotPositionTracker();
        }
        return instance;
    }

    private final Odometry odometry;
    private CircularTimestampedList<Transform> robotVelocities;
    private CircularTimestampedList<Transform> robotTransform;
    private RobotPositionTracker(){
        odometry = new Odometry();
    }

    public void run(double timestamp) {
        //Get left and right encoder positions and gyro heading
        double leftEncoderPosition = DrivetrainSubsystem.getInstance().getLeftPosition();
        double rightEncoderPosition = DrivetrainSubsystem.getInstance().getRightPosition();
        double heading = Gyro.getInstance().getAngle();

        //Update Odometry
        odometry.update(leftEncoderPosition, rightEncoderPosition, heading);

        Position delta = odometry.getDeltaPosition();
        Rotation rotation = odometry.getRobotRotation();

        Transform currentRobotTransform = new Transform(getCurrentRobotTransform().getPosition().add(delta), rotation);
        robotTransform.addFront(new TimestampedElement<>(currentRobotTransform, timestamp));
        if(robotTransform.size() > 1) {
            TimestampedElement<Transform> lastRobotTransform = robotTransform.get(1);
            double deltaTime = robotTransform.getLatest().getTimestamp()-lastRobotTransform.getTimestamp();
            robotVelocities.addFront(new TimestampedElement<>(robotTransform.getLatest().getObject().subtract(lastRobotTransform.getObject()).divide(deltaTime), timestamp));
        }

    }

    public void addStatePrediction(DrivetrainState statePrediction){

    }

    public void addStateMeasurement(DrivetrainState measurement){

    }

    public void addVisionMeasurement(Position visionPosition, double timestamp){

    }

    public void setRobotTransform(Transform newTransform){

    }

    public void setPosition(Position position){

    }

    public void calibrateEncoders(double leftEncoder, double rightEncoder){
        odometry.zeroEncoders(leftEncoder, rightEncoder);
    }

    public void setHeading(double heading, double gyro){
        odometry.setHeading(heading, gyro);
    }

    public Odometry getOdometry(){
        return odometry;
    }

    public Transform getCurrentRobotTransform() {
        return robotTransform.getLatest().getObject();
    }
}
