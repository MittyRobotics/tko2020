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

import com.github.mittyrobotics.commands.*;
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestConveyorAuto extends SequentialCommandGroup {
    public TestConveyorAuto() {
        //Properties
        double defaultMaxAcceleration = 10;
        double defaultMaxDeceleration = 10;
        double defaultMaxVelocity = 40;
        double defaultStartVelocity = 0;
        double defaultEndVelocity = 0;
        boolean defaultExtremeTakeoff = false;
        double defaultExtremeTakeoffMultiplier = 2;
        boolean defaultContinuouslyAdaptivePath = false;
        //Ramsete properties
        double defaultAggressiveGain = 2.0;
        double defaultDampingGain = .7;
        //Pure pursuit properties
        double defaultLookahead = 30;
        double defaultCurvatureSlowdownGain = 1.2;
        double defaultMinSlowdownVelocity = 50;

        //Initialize followers
        PathFollower follower = new PathFollower(new PathFollowerProperties(
                new PathVelocityController(new VelocityConstraints(
                        defaultMaxAcceleration,
                        defaultMaxDeceleration,
                        defaultMaxVelocity),
                        defaultStartVelocity,
                        defaultEndVelocity,
                        defaultExtremeTakeoff,
                        defaultExtremeTakeoffMultiplier
                ),
                false,
                defaultContinuouslyAdaptivePath
        ),
                new PathFollowerProperties.PurePursuitProperties(
                        defaultLookahead,
                        defaultCurvatureSlowdownGain,
                        defaultMinSlowdownVelocity
                )
        );

        PathFollower followerReversed = new PathFollower(new PathFollowerProperties(
                new PathVelocityController(new VelocityConstraints(
                        defaultMaxAcceleration,
                        defaultMaxDeceleration,
                        defaultMaxVelocity),
                        defaultStartVelocity,
                        defaultEndVelocity,
                        defaultExtremeTakeoff,
                        defaultExtremeTakeoffMultiplier
                ),
                true,
                defaultContinuouslyAdaptivePath
        ),
                new PathFollowerProperties.PurePursuitProperties(
                        defaultLookahead,
                        defaultCurvatureSlowdownGain,
                        defaultMinSlowdownVelocity
                ));

        PathFollower followerRamsete = new PathFollower(new PathFollowerProperties(
                new PathVelocityController(new VelocityConstraints(
                        defaultMaxAcceleration,
                        defaultMaxDeceleration,
                        defaultMaxVelocity),
                        defaultStartVelocity,
                        defaultEndVelocity,
                        defaultExtremeTakeoff,
                        defaultExtremeTakeoffMultiplier
                ),
                false,
                defaultContinuouslyAdaptivePath
        ),
                new PathFollowerProperties.RamseteProperties(
                        defaultAggressiveGain,
                        defaultDampingGain
                )
        );

        PathFollower followerRamseteReversed = new PathFollower(new PathFollowerProperties(
                new PathVelocityController(new VelocityConstraints(
                        defaultMaxAcceleration,
                        defaultMaxDeceleration,
                        defaultMaxVelocity),
                        defaultStartVelocity,
                        defaultEndVelocity,
                        defaultExtremeTakeoff,
                        defaultExtremeTakeoffMultiplier
                ),
                true,
                defaultContinuouslyAdaptivePath
        ),
                new PathFollowerProperties.RamseteProperties(
                        defaultAggressiveGain,
                        defaultDampingGain
                )
        );

        //Initialize paths
        Path path1 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(0, 0, 180),
                        new Transform(100, 0, 180)
                })
        );

        //Set odometry
        Odometry.getInstance().setTransform(new Transform(0, 0, 0),
                Gyro.getInstance().getAngle());

        addCommands(
                new SequentialCommandGroup(
                        new ParallelDeadlineGroup(
                                new SequentialCommandGroup(
                                        new WaitUntilShooterSpeedCommand(200),
                                        new WaitUntilVisionSafeCommand(0),
                                        new WaitUntilVisionAlignedCommand(),
                                        new WaitUntilTurretReachedSetpointCommand(2),
                                        new ParallelRaceGroup(
                                                new AutoShootMacro(),
                                                new WaitCommand(3)
                                        )
                                ),
                                new MinimalVisionCommand()
                        ),
                        new SetShooterRpmCommand(0),
                        new SetTurretMotorCommand(0)
                        ),
                intake(followerReversed, path1)
        );
    }

    private SequentialCommandGroup shoot(PathFollower follower, Path path) {
        return new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new InitNewPathFollowerCommand(follower),
                                new PathFollowerCommand(path),
                                new SequentialCommandGroup(
                                        new WaitUntilShooterSpeedCommand(200),
                                        new WaitUntilVisionSafeCommand(0),
                                        new WaitUntilVisionAlignedCommand(),
                                        new WaitUntilTurretReachedSetpointCommand(2),
                                        new ParallelRaceGroup(
                                                new AutoShootMacro(),
                                                new WaitCommand(3)
                                        )
                                )
                        ),
                        new MinimalVisionCommand()
                ), new SetShooterRpmCommand(0),
                new SetTurretMotorCommand(0)
        );
    }

    private SequentialCommandGroup intake(
            PathFollower follower, Path path) {
        return new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new InitNewPathFollowerCommand(follower),
                                new PathFollowerCommand(path)
                        ),
                        new IntakeBallCommand(), new AutoConveyorIndexCommand()
                ), new SetIntakeStopCommand()
        );
    }
}
