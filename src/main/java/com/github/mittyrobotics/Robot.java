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

import com.github.mittyrobotics.autonomous.enums.TurretAutomationMode;
import com.github.mittyrobotics.conveyor2.Conveyor2Subsystem;
import com.github.mittyrobotics.conveyor2.IndexPositionCommand;
import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import com.github.mittyrobotics.subsystems.TurretSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    public Robot() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        Conveyor2Subsystem.getInstance().initHardware();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        Conveyor2Subsystem.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {
        System.out.println("SENSOR: " + Conveyor2Subsystem.getInstance().getSwitch());
//        Conveyor2Subsystem.getInstance().overrideSetMotor(OI.getInstance().getJoystick1().getZ());
//        TurretSubsystem.getInstance().manualTurret(OI.getInstance().getJoystick1().getX());
//        if (OI.getInstance().getJoystick1().getY() > 0.8) {
//            ShooterSubsystem.getInstance().overrideSetMotor(0.5);
//        } else {
//            ShooterSubsystem.getInstance().overrideSetMotor(0);
//        }

    }

    @Override
    public void teleopInit() {
//
    }

    @Override
    public void teleopPeriodic() {
        if (OI.getInstance().getJoystick1().getRawButtonPressed(3)) {
            CommandScheduler.getInstance().schedule(new IndexPositionCommand(4));
        }
    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
        Conveyor2Subsystem.getInstance().overrideSetMotor(OI.getInstance().getJoystick1().getY());
        System.out.println("Encoder position: " + Conveyor2Subsystem.getInstance().getPosition());

    }

}