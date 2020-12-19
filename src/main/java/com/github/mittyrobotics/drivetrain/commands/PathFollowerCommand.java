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

import com.github.mittyrobotics.autonomous.AutonDriver;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.drivetrain.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PathFollowerCommand extends CommandBase {
    private Path path;
    private PathFollower pathFollower;

    public PathFollowerCommand(PathFollower pathFollower, Path path) {
        super();
        this.pathFollower = pathFollower;
        addRequirements(DriveTrainSubsystem.getInstance());
        this.path = path;
    }

    @Override
    public void initialize() {
        AutonDriver.getInstance().initNewPathFollower(pathFollower);
        AutonDriver.getInstance().setPath(path);
        AutonDriver.getInstance().initAutonDriver();
    }

    @Override
    public void execute() {
        AutonDriver.getInstance().run();
    }

    @Override
    public void end(boolean interrupted) {
        AutonDriver.getInstance().disableAutonDriver();
    }

    @Override
    public boolean isFinished() {
        return AutonDriver.getInstance().isFinishedPath();
    }

}
