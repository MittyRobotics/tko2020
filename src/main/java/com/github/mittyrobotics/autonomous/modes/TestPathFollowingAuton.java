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

import com.github.mittyrobotics.autonomous.commands.InitNewPathFollowerCommand;
import com.github.mittyrobotics.autonomous.commands.PathFollowerCommand;
import com.github.mittyrobotics.autonomous.commands.TestPrintCommand;
import com.github.mittyrobotics.autonomous.commands.WaitUntilPathFollowerWithinDistanceCommand;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.controllers.PurePursuitController;
import com.github.mittyrobotics.path.following.controllers.RamseteController;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestPathFollowingAuton extends SequentialCommandGroup {
    public TestPathFollowingAuton(){
        double maxAcceleration = 20;
        double maxDeceleration = 20;
        double maxVelocity = 50;
        double startVelocity = 0;
        double endVelocity = 0;
        boolean extremeTakeoff = false;
        double extremeTakeoffMultiplier = 2;
        boolean continuouslyAdaptivePath = false;
        double aggressiveGain = 2.0;
        double dampingGain = .7;

        double lookahead = 20;

        double curvatureSlowdownGain = PurePursuitController.DEFAULT_CURVATURE_SLOWDOWN_GAIN;
        double minSlowdownVelocity = 30;
        VelocityConstraints velocityConstraints = new VelocityConstraints(maxAcceleration,maxDeceleration,maxVelocity);
        PathVelocityController velocityController = new PathVelocityController(velocityConstraints,startVelocity,
                endVelocity,extremeTakeoff,extremeTakeoffMultiplier);
        PathFollowerProperties properties = new PathFollowerProperties(velocityController,false,
                continuouslyAdaptivePath);

        PathFollowerProperties propertiesReversed = new PathFollowerProperties(velocityController,true,
                continuouslyAdaptivePath);

        PathFollowerProperties.RamseteProperties ramseteProperties =
                new PathFollowerProperties.RamseteProperties(aggressiveGain,dampingGain);

        PathFollowerProperties.PurePursuitProperties purePursuitProperties =
                new PathFollowerProperties.PurePursuitProperties(lookahead,
                curvatureSlowdownGain,
                minSlowdownVelocity);
        PathFollower follower = new PathFollower(properties,purePursuitProperties);
        PathFollower followerReversed = new PathFollower(propertiesReversed,ramseteProperties);

        Path path1 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{new Transform(0,0, 0),
                        new Transform(100,48, 0)}));

        Path path2 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{new Transform(100,48, 180),
                        new Transform(0,0, 180)}));

        addCommands(
                //Init path follower
                new InitNewPathFollowerCommand(follower),
                //Drive first path
                new TestPrintCommand("STARTING PATH FOLLOWER"),
                new PathFollowerCommand(path1),
                new TestPrintCommand("FINISHED PATH FOLLOWER"),

                new InitNewPathFollowerCommand(followerReversed),

                //Drive second path
                new TestPrintCommand("STARTING PATH FOLLOWER"),
                new PathFollowerCommand(path2),
                new TestPrintCommand("FINISHED PATH FOLLOWER")
        );
    }
}
