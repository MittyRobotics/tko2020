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

package com.github.mittyrobotics.autonomous.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public class AutonSelector {
    private static AutonSelector instance;

    public static AutonSelector getInstance() {
        if (instance == null) {
            instance = new AutonSelector();
        }
        return instance;
    }

    /**
     * Returns the autonomous command group to be run during the autonomous period of the match.
     * <p>
     * This pulls the autonomous selection from SmartDashboard, set by the driver before the match starts.
     *
     * @return the autonomous command group to be run during the autonomous period of the match.
     */
    public CommandGroupBase getSelectedAutonomousMode() {
        return (CommandGroupBase) SmartDashboard.getData("Auton Mode");
    }

    public void setupAutonChooser() {
        SendableChooser<CommandGroupBase> chooser = new SendableChooser<>();
//        chooser.setDefaultOption("Shoot 3 Balls", new ShootAuto());
//        chooser.addOption("Drive off line", new DriveBackOffLineAuto());
//        chooser.addOption("Drive and Shoot", new DriveBackAndShootAuto());
//        chooser.addOption("Six Ball Auton", new SixBallAuton());
        SmartDashboard.putData("Auton Mode", chooser);
    }
}