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

package com.github.mittyrobotics.conveyor.commands.intake;

import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class IntakeBallCommand extends RunCommand {
    private boolean auton = false;
    public IntakeBallCommand() {
        this(false, IntakeConstants.INTAKE_SPEED_FAST);

    }

    public IntakeBallCommand(boolean auton) {
        this(auton, IntakeConstants.INTAKE_SPEED_FAST);
    }
    public IntakeBallCommand(boolean auton, double speed) {
        super(() -> IntakeSubsystem.getInstance().overrideSetMotor(speed));
        this.auton = auton;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeSubsystem.getInstance().overrideSetMotor(0);
    }

    @Override
    public boolean isFinished() {
        return !auton && !OI.getInstance().getXboxController2().getBumper(GenericHID.Hand.kRight);
    }
}
