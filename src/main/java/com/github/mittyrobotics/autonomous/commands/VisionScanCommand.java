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

package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.turret.Turret;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class VisionScanCommand extends SequentialCommandGroup {
    private static double VISION_SCAN_PERCENT_OUTPUT = .6;
    public VisionScanCommand() {
        addRequirements(Turret.getInstance());
        addCommands(
                new ParallelRaceGroup(
                        new WaitUntilVisionDetectedCommand(),
                        sequence(
                                new WaitCommand(0.1),
                                new SetTurretControlLoopMaxPercentCommand(1),
                                new SetAutomatedTurretRobotRelativeAngleCommand(new Rotation(-90)),
                                new WaitUntilTurretReachedSetpointCommand(1),
                                new SetTurretControlLoopMaxPercentCommand(VISION_SCAN_PERCENT_OUTPUT),
                                new SetAutomatedTurretRobotRelativeAngleCommand(new Rotation(90)),
                                new WaitUntilTurretReachedSetpointCommand(1)
                        )
                ),
                new SetTurretControlLoopMaxPercentCommand(1)
        );
    }
}
