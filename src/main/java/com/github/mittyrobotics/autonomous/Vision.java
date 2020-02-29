package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.interfaces.IDashboard;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements IDashboard {

    private static Vision instance = new Vision();

    public static Vision getInstance(){
        return instance;
    }

    private double visionAlignedTimer;
    private double visionAlignedTimerStart;

    private double latestVisionLatency;

    private VisionTarget latestVisionTarget;

    public void run() {
        Limelight.getInstance().updateLimelightValues();
        latestVisionLatency = Limelight.getInstance().getLimelightLatency();
        if (isSafeToUseVision()) {
            Rotation visionYaw = new Rotation(Limelight.getInstance().getYawToTarget());
            Rotation visionPitch = new Rotation(Limelight.getInstance().getPitchToTarget());
            double visionDistance = computeVisionDistance(visionPitch);
            visionDistance = visionToTurretDistance(visionDistance, visionYaw);
            visionYaw = visionToTurretYaw(visionDistance, visionDistance, visionYaw);

            //Transform turretTransform = computeTurretTransform(visionDistance, visionYaw,
//                    Gyro.getInstance().getRotation());

//            Transform turretTransform = computeLatencyCompensatedTransform(turretTransform, latestVisionLatency);

            Transform turretTransform = new Transform();

            latestVisionTarget = new VisionTarget(turretTransform, visionYaw, visionDistance);
        }
        visionAlignedTimer = Timer.getFPGATimestamp() - visionAlignedTimerStart;
        if (!isVisionAligned()) {
            visionAlignedTimerStart = Timer.getFPGATimestamp();
        }
    }

    public boolean isSafeToUseVision() {
        return Limelight.getInstance().isHasValidTarget();
    }

    public boolean isVisionAligned() {
        return Limelight.getInstance().getYawToTarget() < AutonConstants.SAFE_VISION_ANGLE_THRESHOLD;
    }

    public boolean isVisionLocked(double lockedTime) {
        return visionAlignedTimer >= lockedTime;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("vision-turret-yaw", latestVisionTarget.getObserverYawToTarget().getHeading());
        SmartDashboard.putNumber("vision-field-yaw", latestVisionTarget.getObserverTransform().getRotation().getHeading());
        SmartDashboard.putNumber("vision-localization-x", latestVisionTarget.getObserverTransform().getPosition().getX());
        SmartDashboard.putNumber("vision-localization-y", latestVisionTarget.getObserverTransform().getPosition().getY());
        SmartDashboard.putNumber("vision-distance", latestVisionTarget.getObserverDistanceToTarget());
        SmartDashboard.putNumber("vision-latency", latestVisionLatency);
    }

    private Transform computeTurretTransform(double visionDistance, Rotation visionYaw, Rotation gyro) {
        Position turretPosition = computeTurretPosition(visionDistance, visionYaw);
        Rotation robotrelativeAngle = AutomatedTurretSuperstructure.getInstance().turretToRobotRelativeAngle(
                AutomatedTurretSuperstructure.getInstance().getRobotRelativeRotation(), visionYaw);
        Rotation fieldRelativeAngle = AutomatedTurretSuperstructure.getInstance().robotToFieldRelativeAngle(
                gyro, robotrelativeAngle);

        return new Transform(turretPosition, fieldRelativeAngle);
    }

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
        Rotation turretRotationAtLatency = AutomatedTurretSuperstructure.getInstance().getTurretRobotRelativeRotations().getElementFromTimestamp(timestampAtLatency);
        Rotation currentTurretRotation = AutomatedTurretSuperstructure.getInstance().getRobotRelativeRotation();
        Transform turretTransformAtLatency = new Transform(AutomatedTurretSuperstructure.getInstance().robotToTurretPosition(robotTransformAtLatency), turretRotationAtLatency);
        Transform currentTurretTransform = new Transform(AutomatedTurretSuperstructure.getInstance().robotToTurretPosition(currentRobotTransform), currentTurretRotation);
        Transform difference = currentTurretTransform.subtract(turretTransformAtLatency);

        return turretTransform.subtract(difference);
    }

    public VisionTarget getLatestVisionTarget() {
        return latestVisionTarget;
    }
}
