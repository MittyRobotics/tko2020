package com.github.mittyrobotics.autonomous;


import com.github.mittyrobotics.Robot;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.kinematics.DifferentialDriveState;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.interfaces.IDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.github.mittyrobotics.autonomous.RobotPositionTracker.getRotatedVelocityTransform;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

public class Autonomous implements IDashboard {
    private static Autonomous instance;
    public static Autonomous getInstance(){
        if(instance == null){
            instance = new Autonomous();
        }
        return instance;
    }

    private DifferentialDriveState currentDriveCommand;
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

    private final double VISION_P = .6;
    private final double VISION_D = .0; //0.07
    private final double LINEAR_VELOCITY_Y_GAIN = .3/100.0;
    private final double LINEAR_VELOCITY_X_BACKWARD_GAIN = 27;
    private final double LINEAR_VELOCITY_X_FORWARD_GAIN = 1.5;
    private final double LINEAR_MOVEMENT_ROTATION_VELOCITY_GAIN = 0;
    private final double COUNTERACT_VELOCITY_FUDGE_GAIN = 1.1;
    private double TURRET_VELOCITY_F = (12.0)/500; // TODO: Tune!!
    private final double TURRET_VELOCITY_P = 0;

    public void run(){
        Vision.getInstance().run();
        //Update delta time
        double time = Timer.getFPGATimestamp();
        double dt = lastTime == 0? 0.02:time-lastTime;
        lastTime = time;

        //Get vision angle and vision angle velocity from Vision
        double visionAngle = -Limelight.getInstance().getYawToTarget();
        double visionAngleVelocity = (visionAngle-lastVisionAngle)/dt;
        lastVisionAngle = visionAngle;

        //Get vision distance from Vision
        double visionDistance = visionDistanceFilter.calculate(Vision.getInstance().getLatestTarget().distance);

        //Get the robot's current velocity transform relative to the field and current velocity state
        Transform fieldVelocity =
//                new Transform(
//                RobotPositionTracker.getInstance().getFilterState().calculateVector(Gyro.getInstance().getRotation()),
//                new Rotation(RobotPositionTracker.getInstance().getFilterState().getTheta()*dt));


                getRotatedVelocityTransform(RobotPositionTracker.getInstance().getFilterState(),
                RobotPositionTracker.getInstance().getFilterTransform().getRotation());
        //Filter field velocity
//        fieldVelocity = new Transform(
//                fieldVelocityYFilter.calculate(fieldVelocity.getPosition().getY()),
//                fieldVelocityXFilter.calculate(fieldVelocity.getPosition().getX()),
//                fieldVelocityRotFilter.calculate(fieldVelocity.getRotation().getRadians())
//        );

        //Get current robot transform
        Transform currentRobotTransform = RobotPositionTracker.getInstance().getFilterTransform();
        //Predict next robot transform given current transform and velocity
        Transform nextRobotTransform = fieldVelocity.times(new Transform(new Vector2D(dt, dt), new Rotation(dt))).plus(currentRobotTransform);

        //Calculate current field rotation from robot to target
        double currentFieldRotation = Math.toDegrees(AutonCoordinates.SCORING_TARGET.angleTo(currentRobotTransform.getVector()).getRadians());
        //Calculate next field rotation from robot to target
        double nextFieldRotation = Math.toDegrees(AutonCoordinates.SCORING_TARGET.angleTo(nextRobotTransform.getVector()).getRadians());
        //Calculate change in field rotation over change in time for linear movement
        double linearRotationVelocity = -(nextFieldRotation-currentFieldRotation)/dt;
        //Calculate shooter RPM and turret output
        autoShooterRPM = calculateShooter(visionDistance, fieldVelocity);
        autoTurretOutput = calculateTurret(visionAngle, visionAngleVelocity, linearRotationVelocity, fieldVelocity, visionDistance, dt);
    }

    private double calculateShooter(double visionDistance, Transform fieldVelocity){
        //Shooter calculations
        double visionRPM = getRPMFromTable(visionDistance);
        double fieldVelocityRPM = -fieldVelocity.getVector().getX() *
                ((fieldVelocity.getVector().getX()>0)?
                        LINEAR_VELOCITY_X_FORWARD_GAIN:LINEAR_VELOCITY_X_BACKWARD_GAIN);
        return visionRPM + fieldVelocityRPM;
    }

    private double calculateTurret(double visionAngle, double visionAngleVelocity, double linearRotationVelocity, Transform fieldVelocity, double distance, double dt){
        //Calculate Y motion offset
        double yVelocityOffset = -fieldVelocity.getVector().getY()* LINEAR_VELOCITY_Y_GAIN * 0;
        //Add x motion offset to vision angle
        double offsetVision =  -Limelight.getInstance().getYawToTarget();
//        double offsetVision = -Limelight.getInstance().getYawToTarget();
        System.out.println(" va: " + offsetVision);
        System.out.println(" fv: " + fieldVelocity.getVector());
        //Calculate vision angle PID
//        double pidVoltage = offsetVision * VISION_P + visionAngleVelocity * VISION_D;

        double posVoltage = TurretSubsystem.getInstance().turretPID(offsetVision + TurretSubsystem.getInstance().getAngle());

        //Counteract the current field rotation velocity
        double counteractFieldRotationVelocity = Math.toDegrees(fieldVelocity.getRotation().getRadians())*COUNTERACT_VELOCITY_FUDGE_GAIN;

        SmartDashboard.putNumber("frv", counteractFieldRotationVelocity);
        SmartDashboard.putNumber("trv", TurretSubsystem.getInstance().getVelocity());
        //Counteract the linear movement velocity
        double counteractLinearMovementVelocity = linearRotationVelocity * LINEAR_MOVEMENT_ROTATION_VELOCITY_GAIN;
        System.out.println(" lrv: " + linearRotationVelocity);

//        if(DriverStation.getInstance().isEnabled()){
//            System.out.println(linearRotationVelocity + " " + counteractLinearMovementVelocity + " " + counteractFieldRotationVelocity + " " + fieldVelocity ) ;
//        }

        //Calculate final counteraction velocity with countracted field rotation and counteracted linear movement
//        double desiredVelocity = counteractFieldRotationVelocity+counteractLinearMovementVelocity;

        double desiredVelocity = counteractFieldRotationVelocity +
                (((Math.abs(fieldVelocity.getX())>inches(40)?
                        (fieldVelocity.getX()*17) * Math.pow(((200-(distance-120))/200), 1.0/3.0):
                        (0))));
        double velVoltage = TurretSubsystem.getInstance().turretVelocity(desiredVelocity);
//        //Turret PF loop
//        double velVoltage = desiredVelocity* TURRET_VELOCITY_F +
//                (desiredVelocity- TurretSubsystem.getInstance().getVelocity()) * TURRET_VELOCITY_P;

        //Combine position and velocity loop outputs
        double turretVoltage = velVoltage + posVoltage;

        //return percent output
        return turretVoltage;
    }

    public Rotation getTurretRotationFromFieldRotation(Rotation fieldRotation, Rotation robotRotation){
        return fieldRotation.minus(robotRotation);
    }

    public Rotation getFieldRotationToTarget(Vector2D robotPosition, Vector2D target){
        return robotPosition.angleTo(target);
    }

    public void setDriveCommand(DifferentialDriveState state){
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
        SmartDashboard.putNumber("odometry-robot-x", RobotPositionTracker.getInstance().getOdometryTransform().getVector().getX());
        SmartDashboard.putNumber("odometry-robot-y", RobotPositionTracker.getInstance().getOdometryTransform().getVector().getY());
        SmartDashboard.putNumber("odometry-robot-theta", RobotPositionTracker.getInstance().getOdometryTransform().getRotation().getRadians());
        SmartDashboard.putNumber("filter-robot-x", RobotPositionTracker.getInstance().getFilterTransform().getVector().getX());
        SmartDashboard.putNumber("filter-robot-y", RobotPositionTracker.getInstance().getFilterTransform().getVector().getY());
        SmartDashboard.putNumber("filter-robot-theta", RobotPositionTracker.getInstance().getFilterTransform().getRotation().getRadians());
        SmartDashboard.putNumber("vision-distance", Vision.getInstance().getLatestTarget().distance);
        SmartDashboard.putNumber("vision-transform-x", Vision.getInstance().getLatestRobotTransformEstimate().getVector().getX());
        SmartDashboard.putNumber("vision-transform-y", Vision.getInstance().getLatestRobotTransformEstimate().getVector().getY());
    }
}