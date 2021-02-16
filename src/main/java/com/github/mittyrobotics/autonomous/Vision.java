package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements IDashboard {
    public static Vision instance;
    public static Vision getInstance(){
        if(instance == null){
            instance = new Vision();
        }
        return instance;
    }

    private Vision(){
        //Initialize limelight
        Limelight.getInstance().initDefaultLimelightSettings();
    }

    private VisionTarget latestTarget = new VisionTarget(new Rotation(), new Rotation(), 0);

    public void run(){
        //Update limelight values
        Limelight.getInstance().updateLimelightValues();
        //Get limelight pitch and yaw
        Rotation llYaw = Rotation.fromDegrees(Limelight.getInstance().getYawToTarget());
        Rotation llPitch = Rotation.fromDegrees(Limelight.getInstance().getPitchToTarget());
        //Calculate distance to target
        double distance = calculateDistanceToTarget(llPitch);
        //Update latest vision target
        latestTarget = new VisionTarget(llYaw, llPitch, distance);
    }

    private double calculateDistanceToTarget(Rotation pitch){
        return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                Math.tan(Math.toRadians(pitch.getDegrees() + AutonConstants.LIMELIGHT_PITCH));
    }

    public VisionTarget getLatestTarget() {
        return latestTarget;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("vision-turret-yaw", latestTarget.yaw.getDegrees());
        SmartDashboard.putNumber("vision-turret-pitch", latestTarget.pitch.getDegrees());
    }
}
