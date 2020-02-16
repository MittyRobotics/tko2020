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

package com.github.mittyrobotics.testing;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.modes.TenBallAuton;
import com.github.mittyrobotics.autonomous.util.OdometryManager;
import com.github.mittyrobotics.datatypes.motion.DifferentialDriveKinematics;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.drive.TempTankDrive;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.util.Gyro;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class AutonTesting extends TimedRobot {

    @Override
    public void robotInit() {
        OI.getInstance().digitalInputControls();

        //Init hardware
        DriveTrainFalcon.getInstance().initHardware();

        //Setup DifferentialDriveKinematics track width (essential for path following)
        DifferentialDriveKinematics.getInstance().setTrackWidth(AutonConstants.DRIVETRAIN_TRACK_WIDTH);

        //Set Odometry position to robot starting position and calibrate Odometry
        Gyro.getInstance().calibrate();
        Gyro.getInstance().reset();
        Odometry.getInstance().calibrateRobotTransform(new Transform(), DriveTrainFalcon.getInstance().getLeftEncoder(),
                DriveTrainFalcon.getInstance().getRightEncoder(), Gyro.getInstance().getAngle());
    }

    @Override
    public void robotPeriodic() {
        OdometryManager.getInstance().run();
        CommandScheduler.getInstance().run();

        DriveTrainFalcon.getInstance().updateDashboard();
        OdometryManager.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        new TenBallAuton().schedule();
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        new TempTankDrive().schedule();
    }

    @Override
    public void teleopPeriodic() {
        //DriveTrainFalcon.getInstance().customTankVelocity(20,20);
        //OI.getInstance().shooterDebugControl();
    }

    @Override
    public void testInit() {
        Odometry.getInstance().calibrateTransformToZero(DriveTrainFalcon.getInstance().getLeftEncoder(),
                DriveTrainFalcon.getInstance().getRightEncoder(), Gyro.getInstance().getAngle());
    }
}