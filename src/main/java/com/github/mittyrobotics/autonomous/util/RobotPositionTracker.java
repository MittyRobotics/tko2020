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
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.datatypes.CircularTimestampedList;
import com.github.mittyrobotics.datatypes.TimestampedElement;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.observers.KalmanFilter;
import com.github.mittyrobotics.motion.observers.Odometry;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.Timer;
import org.ejml.simple.SimpleMatrix;

public class RobotPositionTracker {
    private static RobotPositionTracker instance;
    public static RobotPositionTracker getInstance() {
        if (instance == null) {
            instance = new RobotPositionTracker();
        }
        return instance;
    }

    private final Odometry odometry;

    private Transform odometryTransform = new Transform();

    private KalmanFilter filter;

    private double lastStateMeasurementTime;
    private double lastStatePredictionTime;

    private RobotPositionTracker(){
        odometry = new Odometry();
    }

    public void init(double dt){
        SimpleMatrix A = new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }); //transition matrix
        SimpleMatrix B = new SimpleMatrix(new double[][]{
                {dt, 0},
                {0, dt},
                {1, 0},
                {0, 1}
        }); //control matrix
        SimpleMatrix H = new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }); //measurement matrix
        SimpleMatrix Q = new SimpleMatrix(new double[][]{
                {.01, 0, 0, 0},
                {0, .01, 0, 0},
                {0, 0, .01, 0},
                {0, 0, 0, .01}
        }); //process noise
        SimpleMatrix R = new SimpleMatrix(new double[][]{
                {20, 0, 0, 0},
                {0, 20, 0, 0},
                {0, 0, 20, 0},
                {0, 0, 0, 20}
        }); //measurement noise

        filter = new KalmanFilter(A, B, H, Q, R);

        lastStateMeasurementTime = Timer.getFPGATimestamp();
    }

    public void updateOdometry(){
        odometry.update(
                DrivetrainSubsystem.getInstance().getLeftPosition(),
                DrivetrainSubsystem.getInstance().getRightPosition(),
                Gyro.getInstance().getAngle()
        );
        odometryTransform = new Transform(
                odometryTransform.getPosition().add(odometry.getDeltaPosition()),
                odometry.getRobotRotation()
        );
        DrivetrainState state = DrivetrainState.fromWheelSpeeds(DrivetrainSubsystem.getInstance().getLeftVelocity(),
                DrivetrainSubsystem.getInstance().getRightVelocity(),
                AutonConstants.DRIVETRAIN_TRACK_WIDTH);

//        Transform delta = state.getDeltaTransform(0.02);
//        odometryTransform = odometryTransform.add(new Transform(delta.getPosition().rotateBy(odometry.getRobotRotation()), new Rotation()));
//        odometryTransform.setRotation(odometry.getRobotRotation());

        addStateMeasurement(state);
        if(Vision.getInstance().isVisionSafe()){
            addVisionMeasurement(Vision.getInstance().getLatestRobotTransformEstimate().getPosition());
        }

    }

    public void addStatePrediction(DrivetrainState statePrediction){
        double time = Timer.getFPGATimestamp();
        double dt = lastStatePredictionTime == 0? 0.02 : time-lastStatePredictionTime;
        lastStatePredictionTime = time;
        Position deltaPosition = statePrediction.getRotatedVelocityTransform(getFilterTransform().getRotation()).multiply(dt).getPosition();
        SimpleMatrix u = new SimpleMatrix(new double[][]{
                {deltaPosition.getX()/dt},
                {deltaPosition.getY()/dt}
        });
        filter.predict(u);
    }

    public void addStateMeasurement(DrivetrainState measurement){
        double time = Timer.getFPGATimestamp();
        double dt = time-lastStateMeasurementTime;
        lastStateMeasurementTime = time;
        Position deltaPosition = measurement.getRotatedVelocityTransform(getFilterTransform().getRotation()).multiply(dt).getPosition();
        SimpleMatrix u = new SimpleMatrix(new double[][]{
                {deltaPosition.getX()/dt},
                {deltaPosition.getY()/dt}
        });
        filter.predict(u);
    }

    public void addVisionMeasurement(Position visionPosition){
        SimpleMatrix z = new SimpleMatrix(new double[][]{
                {visionPosition.getX()},
                {visionPosition.getY()},
                {filter.getxHat().get(2)},
                {filter.getxHat().get(3)}
        });
        filter.correct(z);
    }

    public void setOdometryTransform(Transform newTransform){
        odometryTransform = newTransform;
        filter.setxHat(new SimpleMatrix(new double[][]{
                {newTransform.getPosition().getX()},
                {newTransform.getPosition().getY()},
                {0},
                {0}
        }));
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

    public Transform getOdometryTransform() {
        return odometryTransform;
    }

    public Transform getFilterTransform() {
        SimpleMatrix xHat = filter.getxHat();
        return new Transform(xHat.get(0), xHat.get(1), getOdometryTransform().getRotation());
    }

    public DrivetrainState getFilterState() {
        SimpleMatrix xHat = filter.getxHat();
        return DrivetrainState.fromWheelSpeeds(xHat.get(2), xHat.get(3), AutonConstants.DRIVETRAIN_TRACK_WIDTH);
    }
}
