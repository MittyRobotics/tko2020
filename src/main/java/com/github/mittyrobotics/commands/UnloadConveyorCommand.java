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

package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.BufferSubsystem;
import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UnloadConveyorCommand extends CommandBase {
    public UnloadConveyorCommand() {
        addRequirements(ConveyorSubsystem.getInstance(), BufferSubsystem.getInstance(), ShooterSubsystem.getInstance());
    }

    @Override
    public void execute() {
        ConveyorSubsystem.getInstance().manualSetConveyorSpeed(.4);
        BufferSubsystem.getInstance().manualBufferSpeed(.4);
        ShooterSubsystem.getInstance().setShooterPercent(.25);
//        Intake.getInstance().intakeBall();
    }

    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().manualSetConveyorSpeed(0);
        BufferSubsystem.getInstance().manualBufferSpeed(0);
        ShooterSubsystem.getInstance().setShooterPercent(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
