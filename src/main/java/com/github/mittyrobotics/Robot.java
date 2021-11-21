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

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Robot Class to run the robot code (uses timed robot)
 */
public class Robot extends TimedRobot {
    /**
     * Sets the Robot to loop at 20 ms cycle
     */

    private Orchestra orchestra;
    private final TalonFX[] motors = {
            new TalonFX(1),
            new TalonFX(2),
            new TalonFX(3)
    };

    Joystick j = new Joystick(2);
    int index = 0;

    private final String[] songs = {
            "megalovania.chrp",
            "partyintheusa.chrp",
            "safeandsound2.chrp"
    };

    public Robot() {
        super(0.02);
    }

    /**
     * Initializes all the hardware
     */
    @Override
    public void robotInit() {

        ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();

        Collections.addAll(instruments, motors);

        orchestra = new Orchestra(instruments);

    }

    /**
     * Runs Scheduler for commands and updates the dashboard and OI
     */
    @Override
    public void robotPeriodic() {
    }

    /**
     * Brakes the drivetrain when disabling
     */
    @Override
    public void disabledInit() {
        orchestra.stop();
    }

    /**
     * Initializes and starts autonomous command
     */
    @Override
    public void disabledPeriodic(){

    }

    @Override
    public void autonomousInit() {


    }

    @Override
    public void autonomousPeriodic() {

    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {
        if(j.getTriggerPressed()) {
            orchestra.stop();
            orchestra.loadMusic(songs[index]);
            orchestra.play();
            if(index + 1 == songs.length) {
                index = 0;
            } else {
                index++;
            }
        }
    }

    @Override
    public void teleopPeriodic() {

    }

    /**
     * Function for initializing test code
     */
    @Override
    public void testInit() {

    }

    /**
     * Function for testing code
     */
    @Override
    public void testPeriodic() {

    }

}