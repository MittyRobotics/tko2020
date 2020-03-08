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

import com.github.mittyrobotics.commands.AutoConveyorIndexCommand;
import com.github.mittyrobotics.commands.InitNewPathFollowerCommand;
import com.github.mittyrobotics.commands.IntakeBallCommand;
import com.github.mittyrobotics.commands.PathFollowerCommand;
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestTwoBallPickupAuto extends SequentialCommandGroup {
    public TestTwoBallPickupAuto() {
        double maxAcceleration = 40;
        double maxDeceleration = 40;
        double maxVelocity = 100;
        double startVelocity = 0;
        double endVelocity = 0;
        boolean extremeTakeoff = false;
        double extremeTakeoffMultiplier = 2;
        boolean continuouslyAdaptivePath = false;
        double aggressiveGain = 2.0;
        double dampingGain = .7;

        double lookahead = 20;

        double curvatureSlowdownGain = 5;
        double minSlowdownVelocity = 20;
        VelocityConstraints velocityConstraints =
                new VelocityConstraints(maxAcceleration, maxDeceleration, maxVelocity);
        PathVelocityController velocityController = new PathVelocityController(velocityConstraints, startVelocity,
                endVelocity, extremeTakeoff, extremeTakeoffMultiplier);
        PathFollowerProperties properties = new PathFollowerProperties(velocityController, false,
                continuouslyAdaptivePath);

        PathFollowerProperties propertiesReversed = new PathFollowerProperties(velocityController, true,
                continuouslyAdaptivePath);

        PathFollowerProperties.RamseteProperties ramseteProperties =
                new PathFollowerProperties.RamseteProperties(aggressiveGain, dampingGain);

        PathFollowerProperties.PurePursuitProperties purePursuitProperties =
                new PathFollowerProperties.PurePursuitProperties(lookahead,
                        curvatureSlowdownGain,
                        minSlowdownVelocity);
        PathFollower follower = new PathFollower(properties, purePursuitProperties);
        PathFollower followerReversed = new PathFollower(propertiesReversed, purePursuitProperties);

        PathFollower followerRamsete = new PathFollower(properties, ramseteProperties);
        PathFollower followerReversedRamsete = new PathFollower(propertiesReversed, ramseteProperties);


        Path path1 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(new Position(0, 0), new Rotation(180)),
                        new Transform(new Position(-64.17, 10), new Rotation(180 - 10))
                }
        ));

        Path path2 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(new Position(-64.17, 10), new Rotation(-10)),
                        new Transform(new Position(-20, 0), new Rotation(0))
                }
        ));

        Path path3 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(new Position(-20, 0), new Rotation(180)),
                        new Transform(new Position(-64.17, -10), new Rotation(180 + 10))
                }
        ));

        Path path4 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(new Position(-64.17, -10), new Rotation(10)),
                        new Transform(new Position(0, 0), new Rotation(0))
                }
        ));

        Odometry.getInstance().setTransform(new Transform(), Gyro.getInstance().getAngle());


        addCommands(
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new ParallelDeadlineGroup(
                                        new SequentialCommandGroup(
                                                new InitNewPathFollowerCommand(followerReversed),
                                                new PathFollowerCommand(followerReversed, path1)
                                        )
                                ),
                                new InitNewPathFollowerCommand(follower),
                                new PathFollowerCommand(follower, path2),
                                new ParallelDeadlineGroup(
                                        new SequentialCommandGroup(
                                                new InitNewPathFollowerCommand(followerReversed),
                                                new PathFollowerCommand(followerReversed, path3)
                                        )
                                ),
                                new InitNewPathFollowerCommand(follower),
                                new PathFollowerCommand(follower, path4)
                        ),
                        new AutoConveyorIndexCommand(),
                        new IntakeBallCommand()
                )

        );
    }
}
