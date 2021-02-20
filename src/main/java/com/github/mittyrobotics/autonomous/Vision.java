package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements IDashboard {
    public static Vision instance;

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    private Vision() {
    }

    private VisionTarget latestTarget = new VisionTarget(new Rotation(), new Rotation(), 0);
    private Transform latestTurretTransformEstimate = new Transform();
    private Transform latestRobotTransformEstimate = new Transform();


    public void run() {
        //Update limelight values
        Limelight.getInstance().updateLimelightValues();
        //Get limelight pitch and yaw
        Rotation llYaw = Rotation.fromDegrees(Limelight.getInstance().getYawToTarget());
        Rotation llPitch = Rotation.fromDegrees(Limelight.getInstance().getPitchToTarget());
        //Calculate distance to target
        double distance = calculateDistanceToTarget(llPitch);
        //Update latest vision target
        latestTarget = new VisionTarget(llYaw, llPitch, distance);

        //Get robot and turret transforms
        Rotation robotRotation = Gyro.getInstance().getRotation();
        Rotation turretRotation = TurretSubsystem.getInstance().getRotation();

        //Calculate camera transform relative to target
        Transform cameraTransform = Vision.getInstance().calculateCameraRelativeTransform(latestTarget);
        //Calculate turret transform relative to target
        Transform turretTransform = Vision.getInstance().calculateTurretRelativeTransform(cameraTransform);
        //Calculate turret transform relative to field
        latestTurretTransformEstimate = Vision.getInstance().calculateTurretFieldTransform(turretTransform, turretRotation, robotRotation);
        //Calculate robot transform relative to field
        latestRobotTransformEstimate = Vision.getInstance().calculateRobotFieldTransform(latestTurretTransformEstimate, robotRotation);
    }

    public double calculateDistanceToTarget(Rotation pitch) {
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch.getDegrees() + AutonConstants.LIMELIGHT_PITCH));
    }

    public Transform calculateCameraRelativeTransform(VisionTarget target) {
        return new Transform( target.distance,target.yaw.tan() * target.distance, target.yaw);
    }

    public Transform calculateTurretRelativeTransform(Transform cameraRelativeTransform){
        Position turretPosition = cameraRelativeTransform.getPosition().add(new Position(
                AutonConstants.CAMERA_TURRET_OFFSET, 0
        ));
        return new Transform(turretPosition, turretPosition.angleTo(new Position()));
    }

    public Transform calculateTurretFieldTransform(Transform turretRelativeTransform, Rotation turretRotation, Rotation robotRotation){
        Rotation fieldRotation = robotRotation.add(turretRotation).add(turretRelativeTransform.getRotation());
        double distance = turretRelativeTransform.getPosition().magnitude();
        Position fieldPosition = new Position(distance*fieldRotation.cos(), distance*fieldRotation.sin());
        fieldPosition = AutonCoordinates.SCORING_TARGET.subtract(fieldPosition);
        fieldRotation = fieldRotation.subtract(turretRelativeTransform.getRotation());
        return new Transform(fieldPosition, fieldRotation);
    }

    public Transform calculateRobotFieldTransform(Transform turretFieldTransform, Rotation robotRotation){
        System.out.println(AutonConstants.TURRET_ON_ROBOT.rotateBy(robotRotation));
        return new Transform(turretFieldTransform.getPosition().subtract(AutonConstants.TURRET_ON_ROBOT.rotateBy(robotRotation)), robotRotation);
    }

    public VisionTarget getLatestTarget() {
        return latestTarget;
    }

    public Transform getLatestTurretTransformEstimate() {
        return latestTurretTransformEstimate;
    }

    public Transform getLatestRobotTransformEstimate() {
        return latestRobotTransformEstimate;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("vision-yaw", latestTarget.yaw.getDegrees());
        SmartDashboard.putNumber("vision-pitch", latestTarget.pitch.getDegrees());
        SmartDashboard.putNumber("vision-turret-x", latestTurretTransformEstimate.getPosition().getX());
        SmartDashboard.putNumber("vision-turret-y", latestTurretTransformEstimate.getPosition().getX());
        SmartDashboard.putNumber("vision-turret-theta", latestTurretTransformEstimate.getRotation().getDegrees());
        SmartDashboard.putNumber("vision-robot-x", latestRobotTransformEstimate.getPosition().getX());
        SmartDashboard.putNumber("vision-robot-y", latestRobotTransformEstimate.getPosition().getX());
        SmartDashboard.putNumber("vision-robot-theta", latestRobotTransformEstimate.getRotation().getDegrees());

    }
}
