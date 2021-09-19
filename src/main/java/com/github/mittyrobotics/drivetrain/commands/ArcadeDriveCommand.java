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

package com.github.mittyrobotics.drivetrain.commands;

import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Drives the robot using arcade drive
 *
 * Uses the {@link XboxWheel} and the {@link Joystick}
 */
public class ArcadeDriveCommand extends CommandBase {

    /**
     * If the front and back should be reversed
     */
    private boolean isReversed;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link DrivetrainSubsystem}
     */
    public ArcadeDriveCommand() {
        addRequirements(DrivetrainSubsystem.getInstance());
    }

    /**
     * Initializes starting variables
     */
    @Override
    public void initialize() {
        isReversed = true;
    }

    /**
     * Reads controller input and moves the robot based on the inputs
     */
    @Override
    public void execute() {
        if (OI.getInstance().getJoystick2().getRawButtonPressed(3)) {
            isReversed = true;
        } else if (OI.getInstance().getJoystick2().getRawButtonPressed(2)) {
            isReversed = false;
        }
        double turn = OI.getInstance().getXboxWheel().getX() * 450 / 120;

        double speed = -OI.getInstance().getJoystick2().getY();

        if (Math.abs(speed) < 0.1) {
            speed = 0;
        }
        boolean brake = OI.getInstance().getJoystick2().getTrigger();

        if (Math.abs(turn) > 1) {
            turn = Math.signum(turn);
        }
        double e = 1 - turn;



        if (brake) {
            DrivetrainSubsystem.getInstance().brake();
        } else {
            if (isReversed) {
                speed = -speed;
            }
            double newSpeed = (speed * e);
            double newTurn = turn;

            SmartDashboard.putNumber("Turn", newTurn);
            SmartDashboard.putNumber("Speed", newSpeed);

            if (Math.abs(newSpeed) < 0.1) {
                DrivetrainSubsystem.getInstance().tankDrive(newTurn, -newTurn);
            } else if (newSpeed >= 0) {
                if (isReversed) {
                    newTurn = -newTurn;
                }
                DrivetrainSubsystem.getInstance().tankDrive(newSpeed + newTurn, newSpeed - newTurn);
            } else {
                if (isReversed) {
                    newTurn = -newTurn;
                }
                DrivetrainSubsystem.getInstance().tankDrive(newSpeed - newTurn, newSpeed + newTurn);
            }
        }
    }

    /**
     * Sets the drivetrain to stop
     */
    @Override
    public void end(boolean interrupted) {
        DrivetrainSubsystem.getInstance().tankDrive(0, 0);
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
}