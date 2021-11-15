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

package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * Moves the {@link ConveyorSubsystem} at the designated speed to shoot balls
 */
public class UnloadConveyorCommand extends RunCommand {
    private boolean auton = false;
    private double timer = 0;
    private double startTime = 0;
    /**
     * Moves the {@link ConveyorSubsystem} at the designated speed to move balls into the {@link ShooterSubsystem}
     * <p>
     * Requires the {@link ConveyorSubsystem}
     */
    public UnloadConveyorCommand() {
        this(false);
    }

    public UnloadConveyorCommand(boolean auton) {
        this(auton, 0);
    }
    public UnloadConveyorCommand(boolean auton, double timer) {
        super(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance());
        this.auton = auton;
        this.timer = timer;
    }

    @Override
    public void initialize() {
        this.startTime = Timer.getFPGATimestamp();
    }

    /**
     * Stops the conveyor from moving when the command ends
     */
    @Override
    public boolean isFinished() {
        return (timer != 0 && (Timer.getFPGATimestamp()-startTime) >= timer) || (!auton && !(OI.getInstance().getXboxController2().getTriggerAxis(GenericHID.Hand.kRight) > 0.1));
    }

    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().stopMotor();
    }
}