package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements IDashboard {

    private static Vision instance;
    private double visionAlignedTimer;
    private double visionAlignedTimerStart;
    private double latestVisionLatency;
    private VisionTarget latestVisionTarget;
    private MedianFilter yawFilter = new MedianFilter(10);
    private MedianFilter pitchFilter = new MedianFilter(10);
    private MedianFilter distanceFilter = new MedianFilter(10);

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    public void run() {
        Limelight.getInstance().updateLimelightValues();
        latestVisionLatency = Limelight.getInstance().getLimelightLatency();
        if (isSafeToUseVision()) {
            Rotation visionYaw = new Rotation(Limelight.getInstance().getYawToTarget());
            Rotation visionPitch = new Rotation(Limelight.getInstance().getPitchToTarget());
            double visionDistance = computeVisionDistance(visionPitch);

            //Update rotations from median filter
            visionYaw = new Rotation(yawFilter.calculate(visionYaw.getHeading()));
            visionPitch = new Rotation(pitchFilter.calculate(visionPitch.getHeading()));
            visionDistance = distanceFilter.calculate(visionDistance);

            //Get distance and angle from turret's center of rotation to the target instead of camera to the target
            visionDistance = visionToTurretDistance(visionDistance, visionYaw);
            visionYaw = visionToTurretYaw(visionDistance, visionDistance, visionYaw);

            //Compute the turret transforms
            Transform turretTransform = computeTurretTransform(visionDistance, visionYaw,
                    Gyro.getInstance().getRotation());
            //Compensate transform based on latency
            turretTransform = computeLatencyCompensatedTransform(turretTransform, latestVisionLatency);

            //Update the latest vision target
            latestVisionTarget = new VisionTarget(turretTransform, visionYaw, visionDistance);
        } else {
            //Reset filters
            yawFilter.reset();
            pitchFilter.reset();
            distanceFilter.reset();
        }

        //Update vision alignment timer
        visionAlignedTimer = Timer.getFPGATimestamp() - visionAlignedTimerStart;
        if (!isVisionAligned()) {
            //Reset vision alignment timer
            visionAlignedTimerStart = Timer.getFPGATimestamp();
        }
    }

    /**
     * Returns if the vision system is safe to use.
     *
     * @return if the vision system is safe to use.
     */
    public boolean isSafeToUseVision() {
        return Limelight.getInstance().isHasValidTarget();
    }

    /**
     * Returns if the vision system is aligned.
     *
     * @return if the vision system is aligned.
     */
    public boolean isVisionAligned() {
        return Limelight.getInstance().getYawToTarget() < AutonConstants.SAFE_VISION_ANGLE_THRESHOLD;
    }

    /**
     * Returns if the vision system is locked.
     *
     * @param lockedTime the time in seconds that the vision system is locked on the target.
     * @return if the vision system is locked.
     */
    public boolean isVisionLocked(double lockedTime) {
        return visionAlignedTimer >= lockedTime;
    }

    /**
     * Updates the {@link SmartDashboard} values associated with the class
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("vision-turret-yaw", latestVisionTarget.getObserverYawToTarget().getHeading());
        SmartDashboard
                .putNumber("vision-field-yaw", latestVisionTarget.getObserverTransform().getRotation().getHeading());
        SmartDashboard
                .putNumber("vision-localization-x", latestVisionTarget.getObserverTransform().getPosition().getX());
        SmartDashboard
                .putNumber("vision-localization-y", latestVisionTarget.getObserverTransform().getPosition().getY());
        SmartDashboard.putNumber("vision-distance", latestVisionTarget.getObserverDistanceToTarget());
        SmartDashboard.putNumber("vision-latency", latestVisionLatency);
    }

    /**
     * Computes the field-relative turret {@link Transform} based on the vision values and robot gyro value
     *
     * @param visionDistance the distance from the turret's center to the vision target
     * @param visionYaw      the yaw angle from the turret's center to the vision target
     * @param gyro           the robot's gyro value
     * @return the field-relative turret {@link Transform}
     */
    private Transform computeTurretTransform(double visionDistance, Rotation visionYaw, Rotation gyro) {
        Position turretPosition = computeTurretPosition(visionDistance, visionYaw);
        Rotation robotrelativeAngle = AutomatedTurretSuperstructure.getInstance().turretToRobotRelativeAngle(
                AutomatedTurretSuperstructure.getInstance().getRobotRelativeRotation(), visionYaw);
        Rotation fieldRelativeAngle = AutomatedTurretSuperstructure.getInstance().robotToFieldRelativeAngle(
                gyro, robotrelativeAngle);

        return new Transform(turretPosition, fieldRelativeAngle);
    }

    /**
     * Computes the field-relative turret {@link Position} based on the vision values.
     *
     * @param visionDistance the distance from the turret's center to the vision target
     * @param visionYaw      the yaw angle from the turret's center to the vision target
     * @return the field-relative turret {@link Position}
     */
    private Position computeTurretPosition(double visionDistance, Rotation visionYaw) {
        //Target relative position
        Position turretPosition = new Position(
                -visionDistance * visionYaw.cos(),
                -visionDistance * visionYaw.sin());

        //Add scoring target to get field-relative position
        turretPosition = turretPosition.add(AutonCoordinates.SCORING_TARGET);

        return turretPosition;
    }

    private double visionToTurretDistance(double visionDistance, Rotation visionYaw) {
        double x = AutonConstants.CAMERA_TURRET_OFFSET;
        return Math.sqrt(x * x + visionDistance * visionDistance +
                2 * x * visionDistance * visionYaw.cos());
    }

    private Rotation visionToTurretYaw(double visionDistance, double turretDistance,
                                       Rotation visionYaw) {
        return new Rotation(
                Math.toDegrees(Math.asin((visionDistance / turretDistance) * visionYaw.sin())));
    }

    private double computeVisionDistance(Rotation pitch) {
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch.getHeading() + AutonConstants.LIMELIGHT_PITCH));
    }

    private Transform computeLatencyCompensatedTransform(Transform turretTransform, double latency) {
        double timestampAtLatency = Timer.getFPGATimestamp() - latency * 1000;
        //Get the robot transform at the estimated time of vision capture
        Transform robotTransformAtLatency = Odometry.getInstance().getRobotTransformAtTimestamp(timestampAtLatency);
        Transform currentRobotTransform = Odometry.getInstance().getLatestRobotTransform();
        Rotation turretRotationAtLatency = AutomatedTurretSuperstructure.getInstance().getTurretRobotRelativeRotations()
                .getElementFromTimestamp(timestampAtLatency);
        Rotation currentTurretRotation = AutomatedTurretSuperstructure.getInstance().getRobotRelativeRotation();
        //Check if previous robot transform and turret rotations exist from their circular timestamped lists
        if (robotTransformAtLatency != null && turretRotationAtLatency != null) {
            Transform turretTransformAtLatency = new Transform(
                    AutomatedTurretSuperstructure.getInstance().robotToTurretPosition(robotTransformAtLatency),
                    turretRotationAtLatency);
            Transform currentTurretTransform =
                    new Transform(
                            AutomatedTurretSuperstructure.getInstance().robotToTurretPosition(currentRobotTransform),
                            currentTurretRotation);
            Transform difference = currentTurretTransform.subtract(turretTransformAtLatency);

            return turretTransform.subtract(difference);
        }
        return turretTransform;
    }

    public VisionTarget getLatestVisionTarget() {
        return latestVisionTarget;
    }
}
