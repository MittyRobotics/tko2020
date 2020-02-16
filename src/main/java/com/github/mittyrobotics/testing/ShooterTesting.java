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

import com.github.mittyrobotics.autonomous.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.commands.EasyVisionCommand;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.shooter.Shooter;
import com.github.mittyrobotics.turret.Turret;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class ShooterTesting extends TimedRobot {
    @Override
    public void robotInit() {
        Shooter.getInstance().initHardware();
        Turret.getInstance().initHardware();
        DriveTrainTalon.getInstance().initHardware();
    }

    @Override
    public void robotPeriodic() {
        Vision.getInstance().run();
        AutomatedTurretSuperstructure.getInstance().run();
        CommandScheduler.getInstance().run();

        Shooter.getInstance().updateDashboard();
        Turret.getInstance().updateDashboard();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
        System.out.println("Init");
        new EasyVisionCommand().schedule();
    }

    @Override
    public void testInit() {
    }

    private void shooterDebugControl() {
        if (OI.getInstance().getXboxController().getYButtonPressed()) {
            Shooter.getInstance().setShooterSpeed(Shooter.getInstance().getCurrentSetpoint() + 50);
        }
        if (OI.getInstance().getXboxController().getAButtonPressed()) {
            Shooter.getInstance().setShooterSpeed(Shooter.getInstance().getCurrentSetpoint() - 50);
        }
        if (OI.getInstance().getXboxController().getBButtonPressed()) {
            Shooter.getInstance().setShooterSpeed(Shooter.getInstance().getCurrentSetpoint() + 10);
        }
        if (OI.getInstance().getXboxController().getXButtonPressed()) {
            Shooter.getInstance().setShooterSpeed(Shooter.getInstance().getCurrentSetpoint() - 10);
        }
    }


    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {
        //shooterDebugControl();

    }

    @Override
    public void testPeriodic() {
    }
}