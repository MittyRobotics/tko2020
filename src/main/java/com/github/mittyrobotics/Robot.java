/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.autonomous.commands.Translate2dTrajectory;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.OdometryRunnable;
import com.github.mittyrobotics.autonomous.util.TurretFieldManager;
import com.github.mittyrobotics.autonomous.vision.VisionManager;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.vision.Limelight;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Robot extends TimedRobot {
    WPI_TalonSRX talon = new WPI_TalonSRX(23);
    public Robot() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        OI.getInstance().digitalInputControls();

        Gyro.getInstance().calibrate();

        DriveTrainTalon.getInstance().initHardware();

        //Setup track width for DifferentialDriveKinematics
        DifferentialDriveKinematics.getInstance().setTrackWidth(AutonConstants.DRIVETRAIN_TRACK_WIDTH);

        Odometry.getInstance().calibrateToZero(DriveTrainTalon.getInstance().getLeftEncoder(),
                DriveTrainTalon.getInstance().getRightEncoder(), Gyro.getInstance().getAngle());

        talon.setInverted(false);
        talon.configFactoryDefault();
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        talon.setSelectedSensorPosition(0);
        SmartDashboard.putNumber("turret_encoder",0);

        SmartDashboard.putNumber("robot_x", 0);
        SmartDashboard.putNumber("robot_y", 0);
        SmartDashboard.putNumber("robot_heading", 0);

        SmartDashboard.putNumber("path_velocity_left", 0);
        SmartDashboard.putNumber("path_velocity_right", 0);

        System.out.println("Robot Init");
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
//        OdometryRunnable.getInstance().run();
//        TurretFieldManager.getInstance().run();
//        VisionManager.getInstance().run();
        SmartDashboard.putNumber("turret_encoder",talon.getSelectedSensorPosition());
    }

    @Override
    public void teleopInit() {
        Odometry.getInstance().calibrateToZero(DriveTrainTalon.getInstance().getLeftEncoder(),
                DriveTrainTalon.getInstance().getRightEncoder(), Gyro.getInstance().getAngle());
        CommandScheduler.getInstance().schedule(new RunCommand(
                () -> DriveTrainTalon.getInstance().tankDrive(-OI.getInstance().getXboxController().getY(GenericHID.Hand
                        .kLeft), -OI.getInstance().getXboxController().getY(
                        GenericHID.Hand.kRight)), DriveTrainTalon.getInstance()));
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {
        double yaw = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        setMotor(.05*yaw);
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        DriveTrainTalon.getInstance().resetEncoder();
        CommandScheduler.getInstance().schedule(new RunCommand(
                () -> DriveTrainTalon.getInstance().tankDrive(-OI.getInstance().getXboxController().getY(GenericHID.Hand
                        .kLeft), -OI.getInstance().getXboxController().getY(
                        GenericHID.Hand.kRight)), DriveTrainTalon.getInstance()));
    }

    @Override
    public void testPeriodic() {
        double yaw = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        setMotor(.06*yaw);
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }


    private void setMotor(double speed){
        speed = Math.min(.7,Math.max(-.7,speed));
        talon.set(ControlMode.PercentOutput,speed);
    }

}


