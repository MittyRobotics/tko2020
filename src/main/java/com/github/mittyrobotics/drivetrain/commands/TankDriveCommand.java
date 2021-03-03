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

import com.github.mittyrobotics.drivetrain.DriveConstants;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Tank Drive Command
 *
 * Uses the {@link XboxController}
 */
public class TankDriveCommand extends CommandBase {
    private SlewRateLimiter leftLimiter;
    private SlewRateLimiter rightLimiter;
    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link DrivetrainSubsystem}
     */
    public TankDriveCommand() {
        addRequirements(DrivetrainSubsystem.getInstance());
        leftLimiter = new SlewRateLimiter(80);
        rightLimiter = new SlewRateLimiter(80);
    }

    /**
     * Reads controller input and moves the robot based on the inputs
     */
    @Override
    public void execute() {
        if (OI.getInstance().getXboxController().getStickButton(GenericHID.Hand.kLeft)) {
            DrivetrainSubsystem.getInstance().brake();
        } else {
            double left = -OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft)* DriveConstants.DRIVE_FALCON_MAX_VELOCITY * .5;
            double right = -OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight)* DriveConstants.DRIVE_FALCON_MAX_VELOCITY * .5;

            if(Math.abs(OI.getInstance().getXboxController().getY(GenericHID.Hand.kLeft)) < 0.1){
                left = 0;
            }
            if(Math.abs(OI.getInstance().getXboxController().getY(GenericHID.Hand.kRight)) < 0.1){
                right = 0;
            }

            left = leftLimiter.calculate(left);
            right = rightLimiter.calculate(right);

            DrivetrainSubsystem.getInstance().setVelocity(left, right);
        }
    }

    /**
     * Sets the drivetrain to stop
     */
    @Override
    public void end(boolean interrupted) {
        DrivetrainSubsystem.getInstance().setTankDrive(0, 0);
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