package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.autonomous.FieldRotation;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.datatypes.motion.DrivetrainState;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class AutomatedTurretControlLoop extends CommandBase{

    private double lastTime;

    private MedianFilter visionFilter = new MedianFilter(10);
    private MedianFilter velocityFilter = new MedianFilter(10);

    private double lastVision = 0;



    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link TurretSubsystem}
     *
     */
    public AutomatedTurretControlLoop(){
        addRequirements(TurretSubsystem.getInstance());
    }

    /**
     * Sets the turret setpoint
     */
    @Override
    public void initialize(){
        lastTime = Timer.getFPGATimestamp();
    }

    /**
     * Updates the turret position
     */
    @Override
    public void execute(){
        double time = Timer.getFPGATimestamp();
        double dt = time-lastTime;
        lastTime = time;

        Transform fieldVelocity = RobotPositionTracker.getInstance().getFilterState().getRotatedDeltaTransform(
                RobotPositionTracker.getInstance().getFilterTransform().getRotation(), dt).divide(dt);

        double visionP = .5;
        double visionD = .07;
        double rawVision = visionFilter.calculate(Vision.getInstance().getLatestTarget().yaw.getDegrees());

        double movingOffsetGain = .3/40.0;
        double movingOffset = -fieldVelocity.getPosition().getX()*movingOffsetGain;

        double offsetVision = rawVision + movingOffset;

        double visionVoltage = offsetVision*visionP + ((rawVision-lastVision)/dt)*visionD;
        lastVision = rawVision;


        DrivetrainState state = DrivetrainState.fromWheelSpeeds(DrivetrainSubsystem.getInstance().getLeftVelocity(),
                DrivetrainSubsystem.getInstance().getRightVelocity(),
                AutonConstants.DRIVETRAIN_TRACK_WIDTH);

        double rotationVelocity = -state.getDeltaTransform(dt).getRotation().getDegrees()/dt;

        Transform currentRobotTransform = RobotPositionTracker.getInstance().getFilterTransform();
        Transform nextRobotTransform = RobotPositionTracker.getInstance().getFilterState().getRotatedDeltaTransform(
                RobotPositionTracker.getInstance().getFilterTransform().getRotation(), dt).add(currentRobotTransform);
        double currentFieldRotation = AutonCoordinates.SCORING_TARGET.angleTo(currentRobotTransform.getPosition()).getDegrees();
        double nextFieldRotation = AutonCoordinates.SCORING_TARGET.angleTo(nextRobotTransform.getPosition()).getDegrees();


        double fieldRotationGain = 1.1;

        double fieldRotationVelocity = -(nextFieldRotation-currentFieldRotation)/dt;

        fieldRotationVelocity*=fieldRotationGain;

        double desiredVelocity = rotationVelocity+fieldRotationVelocity;

        double vF = (12.0)/70; // 3.5
        double vP = .02;
        double maxVelVoltage = 12;
        double velVoltage = desiredVelocity*vF + (desiredVelocity-TurretSubsystem.getInstance().getVelocity())*vP;
        velVoltage = MathUtil.clamp(velVoltage, -maxVelVoltage, maxVelVoltage);

//        System.out.println(state + " " + state.getDeltaTransform(dt) + " " +desiredVelocity + " " + velVoltage);

        double outputVoltage = visionVoltage + velVoltage;
        double output = outputVoltage/12.0;

        TurretSubsystem.getInstance().setMotor(output);

    }

    /**
     * Stops the turret from moving
     */
    @Override
    public void end(boolean interrupted){
        TurretSubsystem.getInstance().stopMotor();
    }

    /**
     * Returns if the command should end
     *
     * @return the error is within the threshold
     */
    @Override
    public boolean isFinished(){
        return false;
    }
}
