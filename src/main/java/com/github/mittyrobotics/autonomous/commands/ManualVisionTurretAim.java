package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Limelight;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualVisionTurretAim extends CommandBase {


    public ManualVisionTurretAim() {
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
        double yaw = Limelight.getInstance().getYawToTarget();
        double pitch = Limelight.getInstance().getPitchToTarget();
//        System.out.println("yaw:" + yaw);
        double p = 0.03;
        double percent = p*yaw;

        double distance = calculateDistance(pitch);
//        double rpm = getRPMFromTable(distance);
//        System.out.println("Distance: " + distance);
//        System.out.println("RPM: " + ShooterSubsystem.getInstance().getManualRPMSetpoint());

        SmartDashboard.putNumber("Distance", distance);

        TurretSubsystem.getInstance().setMotor(percent);
//        ShooterSubsystem.getInstance().setShooterRpm(ShooterSubsystem.getInstance().getManualRPMSetpoint());
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
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stopMotor();
    }

}
