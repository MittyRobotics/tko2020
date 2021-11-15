package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;

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

    private MedianFilter xFilter = new MedianFilter(10);
    private MedianFilter yFilter = new MedianFilter(10);

    private boolean visionSafe = true;

    public void run() {
        //Update limelight values
        Limelight.getInstance().updateLimelightValues();
        //Get limelight pitch and yaw
        Rotation llYaw = new Rotation(degrees(-Limelight.getInstance().getYawToTarget()));
        Rotation llPitch = new Rotation(degrees(Limelight.getInstance().getPitchToTarget()));
        //Calculate distance to target
        double distance = calculateDistanceToTarget(llPitch);
        //Update latest vision target
        latestTarget = new VisionTarget(llYaw, llPitch, distance);

        //Get robot and turret transforms
        Rotation robotRotation = Gyro.getInstance().getRotation();
        Rotation turretRotation = new Rotation(degrees(TurretSubsystem.getInstance().getAngle()));

        //Calculate camera transform relative to target
        Transform cameraTransform = Vision.getInstance().calculateCameraRelativeTransform(latestTarget);

        //Calculate turret transform relative to target
        Transform turretTransform = Vision.getInstance().calculateTurretRelativeTransform(cameraTransform);

        //Calculate turret transform relative to field
        Transform transformEstimate = Vision.getInstance().calculateTurretFieldTransform(turretTransform, turretRotation, robotRotation);
//        System.out.println(transformEstimate);
        cameraTransform.setX(xFilter.calculate(latestTurretTransformEstimate.getX()));
        cameraTransform.setY(yFilter.calculate(latestTurretTransformEstimate.getY()));

        if((Math.abs(transformEstimate.getVector().minus(latestRobotTransformEstimate.getVector()).getX()) > 30
                || Math.abs(transformEstimate.getVector().minus(latestRobotTransformEstimate.getVector()).getX()) > 30) &&
                (latestRobotTransformEstimate.getX() != 0 && latestRobotTransformEstimate.getY() != 0)){
            System.out.println("Warning: Inacurate Vision Data");
            visionSafe = true;
        }
        else{
            visionSafe = true;

            latestTurretTransformEstimate = transformEstimate;

            //Calculate robot transform relative to field
            latestRobotTransformEstimate = Vision.getInstance().calculateRobotFieldTransform(latestTurretTransformEstimate, robotRotation);
        }
    }

    public void reset(){
        latestTurretTransformEstimate = new Transform();
        latestRobotTransformEstimate = new Transform();
    }

    public double calculateDistanceToTarget(Rotation pitch) {
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(pitch.getRadians() + degrees(AutonConstants.LIMELIGHT_PITCH));
    }

    public Transform calculateCameraRelativeTransform(VisionTarget target) {
        return new Transform(new Vector2D(target.distance, target.yaw.tan() * target.distance), target.yaw);
    }

    public Transform calculateTurretRelativeTransform(Transform cameraRelativeTransform) {
        Vector2D turretPosition = cameraRelativeTransform.getVector().plus(new Vector2D(
                AutonConstants.CAMERA_TURRET_OFFSET, 0
        ));
        return new Transform(turretPosition, turretPosition.angleTo(new Vector2D()));
    }

    public Transform calculateTurretFieldTransform(Transform turretRelativeTransform, Rotation turretRotation, Rotation robotRotation) {
        Rotation fieldRotation = robotRotation.plus(turretRotation).plus(turretRelativeTransform.getRotation());
        double distance = turretRelativeTransform.getVector().magnitude();
        Vector2D fieldPosition = new Vector2D(distance * fieldRotation.cos(), distance * fieldRotation.sin());
        fieldPosition = AutonCoordinates.SCORING_TARGET.minus(fieldPosition);
        fieldRotation = fieldRotation.minus(turretRelativeTransform.getRotation());
        return new Transform(fieldPosition, fieldRotation);
    }

    public Transform calculateRobotFieldTransform(Transform turretFieldTransform, Rotation robotRotation) {
        return new Transform(turretFieldTransform.getVector().minus(AutonConstants.TURRET_ON_ROBOT.rotateBy(robotRotation)), robotRotation);
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
        SmartDashboard.putNumber("vision-yaw", latestTarget.yaw.getRadians());
        SmartDashboard.putNumber("vision-pitch", latestTarget.pitch.getRadians());
        SmartDashboard.putNumber("vision-turret-x", latestTurretTransformEstimate.getX());
        SmartDashboard.putNumber("vision-turret-y", latestTurretTransformEstimate.getY());
        SmartDashboard.putNumber("vision-turret-theta", latestTurretTransformEstimate.getRotation().getRadians());
        SmartDashboard.putNumber("vision-robot-x", latestRobotTransformEstimate.getX());
        SmartDashboard.putNumber("vision-robot-y", latestRobotTransformEstimate.getY());
        SmartDashboard.putNumber("vision-robot-theta", latestRobotTransformEstimate.getRotation().getRadians());
    }

    public boolean isVisionSafe() {
        return visionSafe;
    }
}