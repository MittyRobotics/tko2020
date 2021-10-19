package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Limelight;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class VisionTurretAim extends CommandBase {

    private boolean auton;

    public VisionTurretAim() {
        this(false);
    }


    public VisionTurretAim(boolean auton) {
        this.auton = auton;
        addRequirements(TurretSubsystem.getInstance());
    }

    /**
     * Initializes the starting states and setpoints
     */
    @Override
    public void initialize() {

    }

    /**
     * Runs state machine, setting motor speeds and updating ball counts depending on sensor values and assigned states
     */
    @Override
    public void execute() {
        Limelight.getInstance().updateLimelightValues();
        double yaw = Limelight.getInstance().getYawToTarget() + AutonConstants.LIMELIGHT_HORIZONTAL_PID_CORRECTION * Math.signum(Limelight.getInstance().getYawToTarget());
        double pitch = Limelight.getInstance().getPitchToTarget();
        System.out.println("yaw:" + yaw);
        double p = -0.04;
        double percent = p*yaw;

        double distance = calculateDistance(pitch);
//        double rpm = getRPMFromTable(distance);
        System.out.println("Distance: " + distance);
        TurretSubsystem.getInstance().setMotor(percent);
//        ShooterSubsystem.getInstance().setShooterRpm(rpm);
    }


    private double calculateDistance(double pitch){
            return (AutonConstants.HIGH_TARGET_HEIGHT - AutonConstants.LIMELIGHT_HEIGHT) /
                    Math.tan(Math.toRadians(pitch + AutonConstants.LIMELIGHT_PITCH));
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

    /**
     * Returns if the command should end
     *
     * @return false because this is a default command
     */
    @Override
    public boolean isFinished() {
        return !auton && !(OI.getInstance().getXboxController2().getTriggerAxis(GenericHID.Hand.kLeft) > 0.1);
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stopMotor();
//        ShooterSubsystem.getInstance().setShooterRpm(0);
//        ShooterSubsystem.getInstance().stopMotor();
    }

}
