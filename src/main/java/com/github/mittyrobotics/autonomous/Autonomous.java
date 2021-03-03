package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Autonomous implements IDashboard {
    private static Autonomous instance;
    public static Autonomous getInstance(){
        if(instance == null){
            instance = new Autonomous();
        }
        return instance;
    }

    private DrivetrainState currentDriveCommand;
    private double autoTurretOutput = 0;
    private double autoShooterRPM = 0;
    private double lastTime;

    private double lastVisionAngle;

    private final MedianFilter visionAngleFilter = new MedianFilter(10);
    private final MedianFilter visionDistanceFilter = new MedianFilter(10);
    private final MedianFilter fieldVelocityYFilter = new MedianFilter(10);
    private final MedianFilter fieldVelocityXFilter = new MedianFilter(10);
    private final MedianFilter fieldVelocityRotFilter = new MedianFilter(10);
    private Autonomous(){

    }

    private final double VISION_P = .5;
    private final double VISION_D = .07;
    private final double LINEAR_VELOCITY_X_GAIN = .3/40.0;
    private final double LINEAR_VELOCITY_Y_GAIN = 14;
    private final double LINEAR_MOVEMENT_ROTATION_VELOCITY_GAIN = 1.1;
    private final double TURRET_VELOCITY_F = (12.0)/70;
    private final double TURRET_VELOCITY_P = .02;

    public void run(){
        //Update delta time
        double time = Timer.getFPGATimestamp();
        double dt = lastTime == 0? 0.02:time-lastTime;
        lastTime = time;

        //Get vision angle and vision angle velocity from Vision
        double visionAngle = visionAngleFilter.calculate(Vision.getInstance().getLatestTarget().yaw.getDegrees());
        double visionAngleVelocity = (visionAngle-lastVisionAngle)/dt;
        lastVisionAngle = visionAngle;

        //Get vision distance from Vision
        double visionDistance = visionDistanceFilter.calculate(Vision.getInstance().getLatestTarget().distance);

        //Get the robot's current velocity transform relative to the field and current velocity state
        Transform fieldVelocity = RobotPositionTracker.getInstance().getFilterState().getRotatedVelocityTransform(
                RobotPositionTracker.getInstance().getFilterTransform().getRotation());
        //Filter field velocity
        fieldVelocity = new Transform(
                fieldVelocityYFilter.calculate(fieldVelocity.getPosition().getY()),
                fieldVelocityXFilter.calculate(fieldVelocity.getPosition().getX()),
                fieldVelocityRotFilter.calculate(fieldVelocity.getRotation().getRadians())
        );

        //Get current robot transform
        Transform currentRobotTransform = RobotPositionTracker.getInstance().getFilterTransform();
        //Predict next robot transform given current transform and velocity
        Transform nextRobotTransform = fieldVelocity.multiply(dt).add(currentRobotTransform);

        //Calculate current field rotation from robot to target
        double currentFieldRotation = AutonCoordinates.SCORING_TARGET.angleTo(currentRobotTransform.getPosition()).getDegrees();
        //Calculate next field rotation from robot to target
        double nextFieldRotation = AutonCoordinates.SCORING_TARGET.angleTo(nextRobotTransform.getPosition()).getDegrees();
        //Calculate change in field rotation over change in time for linear movement
        double linearRotationVelocity = -(nextFieldRotation-currentFieldRotation)/dt;

        //Calculate shooter RPM and turret output
        autoShooterRPM = calculateShooter(visionDistance, fieldVelocity);
        autoTurretOutput = calculateTurret(visionAngle, visionAngleVelocity, linearRotationVelocity, fieldVelocity, dt);
    }

    private double calculateShooter(double visionDistance, Transform fieldVelocity){
        //Shooter calculations
        double visionRPM = getRPMFromTable(visionDistance);
        double fieldVelocityRPM = Math.abs(fieldVelocity.getPosition().getY())* LINEAR_VELOCITY_Y_GAIN;
        return visionRPM + fieldVelocityRPM;
    }

    private double calculateTurret(double visionAngle, double visionAngleVelocity, double linearRotationVelocity, Transform fieldVelocity, double dt){
        //Calculate X motion offset
        double xVelocityOffset = -fieldVelocity.getPosition().getX()* LINEAR_VELOCITY_X_GAIN;
        //Add x motion offset to vision angle
        double offsetVision = visionAngle + xVelocityOffset;
        //Calculate vision angle PID
        double pidVoltage = offsetVision* VISION_P + visionAngleVelocity* VISION_D;

        //Counteract the current field rotation velocity
        double counteractFieldRotationVelocity = -fieldVelocity.getRotation().getDegrees();

        //Counteract the linear movement velocity
        double counteractLinearMovementVelocity = linearRotationVelocity* LINEAR_MOVEMENT_ROTATION_VELOCITY_GAIN;

        //Calculate final counteraction velocity with countracted field rotation and counteracted linear movement
        double desiredVelocity = counteractFieldRotationVelocity+counteractLinearMovementVelocity;

        //Turret PF loop
        double velVoltage = desiredVelocity* TURRET_VELOCITY_F +
                (desiredVelocity-TurretSubsystem.getInstance().getVelocity()) * TURRET_VELOCITY_P;

        //Combine position and velocity loop outputs
        double turretVoltage = pidVoltage + velVoltage;

        //return percent output
        return turretVoltage/12.0;
    }

    public void updateDrive(){
        DrivetrainSubsystem.getInstance().setVelocity(currentDriveCommand.getLeft(), currentDriveCommand.getRight());
    }

    public Rotation getTurretRotationFromFieldRotation(Rotation fieldRotation, Rotation robotRotation){
        return fieldRotation.subtract(robotRotation);
    }

    public Rotation getFieldRotationToTarget(Position robotPosition, Position target){
        return robotPosition.angleTo(target);
    }

    public void setDriveCommand(DrivetrainState state){
        currentDriveCommand = state;
    }

    private double getRPMFromTable(double distance){
        distance /= 12.0;
        double closest = AutonConstants.SHOOTER_RPM_TABLE[0][0];
        double closestVal = AutonConstants.SHOOTER_RPM_TABLE[0][1];
        for(int i = 1; i < AutonConstants.SHOOTER_RPM_TABLE.length; i++){
            if(Math.abs(distance-AutonConstants.SHOOTER_RPM_TABLE[i][0]) < closest){
                closest = AutonConstants.SHOOTER_RPM_TABLE[i][0];
                closestVal = AutonConstants.SHOOTER_RPM_TABLE[i][1];
            }
        }
        return closestVal;
    }

    public double getAutoTurretOutput() {
        return autoTurretOutput;
    }

    public double getAutoShooterRPM() {
        return autoShooterRPM;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("odometry-robot-x", RobotPositionTracker.getInstance().getOdometryTransform().getPosition().getX());
        SmartDashboard.putNumber("odometry-robot-y", RobotPositionTracker.getInstance().getOdometryTransform().getPosition().getY());
        SmartDashboard.putNumber("odometry-robot-theta", RobotPositionTracker.getInstance().getOdometryTransform().getRotation().getDegrees());
        SmartDashboard.putNumber("filter-robot-x", RobotPositionTracker.getInstance().getFilterTransform().getPosition().getX());
        SmartDashboard.putNumber("filter-robot-y", RobotPositionTracker.getInstance().getFilterTransform().getPosition().getY());
        SmartDashboard.putNumber("filter-robot-theta", RobotPositionTracker.getInstance().getFilterTransform().getRotation().getDegrees());
    }
}
