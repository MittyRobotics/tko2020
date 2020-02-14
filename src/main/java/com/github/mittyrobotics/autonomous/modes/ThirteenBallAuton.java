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

package com.github.mittyrobotics.autonomous.modes;

import com.github.mittyrobotics.autonomous.AutonDriver;
import com.github.mittyrobotics.autonomous.commands.*;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ThirteenBallAuton extends SequentialCommandGroup {
    public ThirteenBallAuton() {
        AutonDriver.getInstance().initNewPathFollower(null);

        Path path1 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{new Transform(AutonCoordinates.TRENCH_STARTING_POINT, 180),
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180)}));

        Path path2 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180),
                        new Transform(AutonCoordinates.PICKUP_LAST_TRENCH, 180)}));

        Path path3 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{new Transform(AutonCoordinates.PICKUP_LAST_TRENCH, 0),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 45),
                }));

        Path path4 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 180 + 45),
                        new Transform(AutonCoordinates.PICKUP_2_PARTY, 90),
                        new Transform(AutonCoordinates.BALL_5, 135)}));

        Path path5 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.BALL_5, 180 + 135),
                        new Transform(AutonCoordinates.PICKUP_2_PARTY, 180 + 90),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 180 + 180 + 45)
                }));

        addCommands(
                //Start first path goal
                new SetAutonDriverPathCommand(path1),

                //Wait until conditions to shoot are met
                new WaitUntilPathFollowerFinishedCommand(),
                new WaitUntilVisionDetectedCommand(1), new WaitUntilShooterSpeedCommand(50),
                new WaitUntilVisionLockedCommand(1),

                //Set second path goal
                new SetAutonDriverPathCommand(path2),

                //Set third path goal
                new SetAutonDriverPathCommand(path3),

                //Wait until conditions to shoot are met
                new WaitUntilPathFollowerFinishedCommand(),
                new WaitUntilVisionDetectedCommand(1),
                new WaitUntilShooterSpeedCommand(50),
                new WaitUntilVisionLockedCommand(1),

                //Set fourth path goal
                new SetAutonDriverPathCommand(path4),

                //Set fifth path goal
                new SetAutonDriverPathCommand(path5),

                //Wait until conditions to shoot are met
                new WaitUntilPathFollowerFinishedCommand(),
                new WaitUntilVisionDetectedCommand(1),
                new WaitUntilShooterSpeedCommand(50),
                new WaitUntilVisionLockedCommand(1)
                );
    }
}
