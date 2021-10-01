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
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * Reverses the {@link ConveyorSubsystem} for outtaking balls
 */
public class ReverseConveyor extends RunCommand {

    /**
     * Reverses the {@link ConveyorSubsystem} at the designated speed to outtake balls from the conveyor
     *
     * Requires the {@link ConveyorSubsystem}
     */
    public ReverseConveyor() {
        super(() -> ConveyorSubsystem.getInstance().outtakeBall(), ConveyorSubsystem.getInstance());
    }

    @Override
    public boolean isFinished() {
        return OI.getInstance().getJoystick1().getTopPressed();
    }

    /**
     * Stops the conveyor from moving when the command ends
     */
    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().stopMotor();
    }
}
