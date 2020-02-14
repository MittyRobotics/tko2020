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
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ThirteenBallAuton extends ParallelCommandGroup {
    public ThirteenBallAuton() {
        double maxAcceleration = 20;
        double maxDeceleration = 20;
        double maxVelocity = 50;
        double startVelocity = 0;
        double endVelocity = 0;
        boolean extremeTakeoff = false;
        double extremeTakeoffMultiplier = 1.2;
        boolean continuouslyAdaptivePath = false;
        double aggressiveGain = 0;
        double dampingGain = 0;

        VelocityConstraints velocityConstraints = new VelocityConstraints(maxAcceleration,maxDeceleration,maxVelocity);
        PathVelocityController velocityController = new PathVelocityController(velocityConstraints,startVelocity,
                endVelocity,extremeTakeoff,extremeTakeoffMultiplier);
        PathFollowerProperties properties = new PathFollowerProperties(velocityController,false,
                continuouslyAdaptivePath);
        PathFollowerProperties.RamseteProperties ramseteProperties =
                new PathFollowerProperties.RamseteProperties(aggressiveGain,dampingGain);
        PathFollower follower = new PathFollower(properties,ramseteProperties);

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
                //Start turret aimbot command
                new TurretAimbotCommand(),
                //Start auton sequence in parallel
                sequence(
                        //Init path follower
                        new InitNewPathFollowerCommand(follower),
                        //Drive first path
                        new PathFollowerCommand(path1,true),

                        //Wait until conditions to shoot are met
                        new WaitUntilShooterSpeedCommand(50),
                        new WaitUntilVisionDetectedCommand(1),
                        new WaitUntilVisionLockedCommand(1),

                        //Drive second path
                        new PathFollowerCommand(path2,true),

                        //Drive third path
                        new PathFollowerCommand(path3,false),

                        //Wait until conditions to shoot are met
                        new WaitUntilShooterSpeedCommand(50),
                        new WaitUntilVisionDetectedCommand(1),
                        new WaitUntilVisionLockedCommand(1),

                        //Drive fourth path
                        new PathFollowerCommand(path4,true),

                        //Drive fifth path
                        new PathFollowerCommand(path5,false),

                        //Wait until conditions to shoot are met
                        new WaitUntilShooterSpeedCommand(50),
                        new WaitUntilVisionDetectedCommand(1),
                        new WaitUntilVisionLockedCommand(1)
                )
        );
    }
}
