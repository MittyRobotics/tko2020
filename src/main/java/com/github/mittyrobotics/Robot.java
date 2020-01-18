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
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Robot extends TimedRobot {

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
        OdometryRunnable.getInstance().run();
        TurretFieldManager.getInstance().run();
        VisionManager.getInstance().run();
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
        CommandScheduler.getInstance().cancelAll();
        DriveTrainTalon.getInstance().resetEncoder();
        Odometry.getInstance().calibrateToZero(DriveTrainTalon.getInstance().getLeftEncoder(),
                DriveTrainTalon.getInstance().getRightEncoder(), Gyro.getInstance().getAngle());

        Path path =
                new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(new Transform[]
                        {
                                new Transform(), new Transform(48, -24), new Transform(100, -24)
                        }));

        PathVelocityController velocityController =
                new PathVelocityController(new VelocityConstraints(100, 40, 150), 0, 0, true);
        PathFollower follower = new PathFollower(new PathFollowerProperties(velocityController, false, false),
                new PathFollowerProperties.RamseteProperties(2.0,
                        0.7));
        follower.setDrivingGoal(new Transform(100,-24));
        CommandScheduler.getInstance().schedule(new Translate2dTrajectory(follower));
    }

    @Override
    public void autonomousPeriodic() {
//       DriveTrainTalon.getInstance().customTankVelocity(10, 10);
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
        System.out.println(
                DriveTrainTalon.getInstance().getLeftEncoder() + " " + DriveTrainTalon.getInstance().getRightEncoder());
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

}