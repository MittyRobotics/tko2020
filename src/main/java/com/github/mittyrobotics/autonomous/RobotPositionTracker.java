package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.kinematics.DifferentialDriveState;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
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
                odometryTransform.getVector().plus(odometry.getDeltaPosition()),
                odometry.getRobotRotation()
        );
        DifferentialDriveState state = DifferentialDriveState.Companion.fromWheels(DrivetrainSubsystem.getInstance().getLeftVelocity(),
                DrivetrainSubsystem.getInstance().getRightVelocity(),
                AutonConstants.DRIVETRAIN_TRACK_WIDTH);


        addStateMeasurement(state);
        if(Vision.getInstance().isVisionSafe() && Vision.getInstance().getLatestTarget().distance < 20*12){
            addVisionMeasurement(Vision.getInstance().getLatestRobotTransformEstimate().getVector());
        }
    }

    public void addStatePrediction(DifferentialDriveState statePrediction){
        double time = Timer.getFPGATimestamp();
        double dt = lastStatePredictionTime == 0? 0.02 : time-lastStatePredictionTime;
        lastStatePredictionTime = time;
        Vector2D deltaPosition = getRotatedVelocityTransform(statePrediction, getFilterTransform().getRotation()).getVector().times(dt);
        SimpleMatrix u = new SimpleMatrix(new double[][]{
                {deltaPosition.getX()/dt},
                {deltaPosition.getY()/dt}
        });
        filter.predict(u);
    }

    public void addStateMeasurement(DifferentialDriveState measurement){
        double time = Timer.getFPGATimestamp();
        double dt = time-lastStateMeasurementTime;
        lastStateMeasurementTime = time;
        Vector2D deltaPosition = getRotatedVelocityTransform(measurement, getFilterTransform().getRotation()).getVector().times(dt);
        SimpleMatrix u = new SimpleMatrix(new double[][]{
                {deltaPosition.getX()/dt},
                {deltaPosition.getY()/dt}
        });
        filter.predict(u);
    }

    public static Transform getRotatedVelocityTransform(DifferentialDriveState state, Rotation robotRotation){
        Transform relativeDeltaTransform = calculateVelocityTransform(state);
        relativeDeltaTransform = new Transform(relativeDeltaTransform.getVector().rotateBy(robotRotation), relativeDeltaTransform.getRotation());
        return relativeDeltaTransform;
    }

    public static Transform calculateVelocityTransform(DifferentialDriveState state){
        double deltaPos = (state.getLeft() + state.getRight()) / 2;
        Rotation rotation =
                new Rotation(Math.atan2((state.getLeft() - state.getRight()),
                        state.getTrackWidth()));
        Vector2D position = new Vector2D(rotation.cos() * deltaPos, rotation.sin() * deltaPos);
        return new Transform(position, rotation);
    }

    public void addVisionMeasurement(Vector2D visionPosition){
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
                {newTransform.getVector().getX()},
                {newTransform.getVector().getY()},
                {0},
                {0}
        }));
    }

    public void setPosition(Vector2D position){

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
        return new Transform(new Vector2D(xHat.get(0), xHat.get(1)), getOdometryTransform().getRotation());
    }

    public DifferentialDriveState getFilterState() {
        SimpleMatrix xHat = filter.getxHat();
        return DifferentialDriveState.Companion.fromWheels(xHat.get(2), xHat.get(3), AutonConstants.DRIVETRAIN_TRACK_WIDTH);
    }
}