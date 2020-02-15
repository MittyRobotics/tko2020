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
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drive.DriveTrainFalcon;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.controllers.PurePursuitController;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ThirteenBallAuton extends SequentialCommandGroup {
    public ThirteenBallAuton() {
        double maxAcceleration = 40;
        double maxDeceleration = 40;
        double maxVelocity = 150;
        double startVelocity = 0;
        double endVelocity = 0;
        boolean extremeTakeoff = false;
        double extremeTakeoffMultiplier = 2;
        boolean continuouslyAdaptivePath = false;
        double aggressiveGain = 2.0;
        double dampingGain = .7;

        double lookahead = 30;

        double curvatureSlowdownGain = 1.2;
        double minSlowdownVelocity = 50;

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
                        new Transform(AutonCoordinates.PICKUP_2_PARTY, 110)}));

        Path path5 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.PICKUP_2_PARTY, 180 + 110),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 180 + 180 + 45)
                }));

        Odometry.getInstance().calibrateRobotTransform(new Transform(AutonCoordinates.TRENCH_STARTING_POINT, 0),
                DriveTrainFalcon.getInstance().getLeftEncoder(),
                DriveTrainFalcon.getInstance().getRightEncoder(), Gyro.getInstance().getAngle());

        addCommands(
                //Init path follower
                new InitNewPathFollowerCommand(followerReversed),
                //Drive first path
                new PathFollowerCommand(path1, false),
                new TestPrintCommand("End"),
                new WaitCommand(1),

//                        //Wait until conditions to shoot are met
//                        new WaitUntilShooterSpeedCommand(50),
//                        new WaitUntilVisionDetectedCommand(1),
//                        new WaitUntilVisionLockedCommand(1),
                new InitNewPathFollowerCommand(followerReversed),



                //Drive second path
                new PathFollowerCommand(path2, false),

                new InitNewPathFollowerCommand(follower),
                //Drive third path
                new PathFollowerCommand(path3, false),

//                        //Wait until conditions to shoot are met
//                        new WaitUntilShooterSpeedCommand(50),
//                        new WaitUntilVisionDetectedCommand(1),
//                        new WaitUntilVisionLockedCommand(1),
                new WaitCommand(1),
                new InitNewPathFollowerCommand(followerReversed),
                //Drive fourth path
                new PathFollowerCommand(path4, false),

                new InitNewPathFollowerCommand(follower),
                //Drive fifth path
                new PathFollowerCommand(path5, false)

//                        //Wait until conditions to shoot are met
//                        new WaitUntilShooterSpeedCommand(50),
//                        new WaitUntilVisionDetectedCommand(1),
//                        new WaitUntilVisionLockedCommand(1)

        );
    }
}
