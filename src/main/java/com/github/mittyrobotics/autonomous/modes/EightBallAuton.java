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

import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorIndexCommand;
import com.github.mittyrobotics.conveyor.commands.IntakeBallCommand;
import com.github.mittyrobotics.conveyor.commands.SetIntakeStopCommand;
import com.github.mittyrobotics.conveyor.commands.StopConveyorCommand;
import com.github.mittyrobotics.datatypes.motion.VelocityConstraints;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drivetrain.commands.PathFollowerCommand;
import com.github.mittyrobotics.motionprofile.PathVelocityController;
import com.github.mittyrobotics.path.following.PathFollower;
import com.github.mittyrobotics.path.following.util.Odometry;
import com.github.mittyrobotics.path.following.util.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.shooter.commands.SetShooterRpmCommand;
import com.github.mittyrobotics.shooter.commands.SetTurretMotorCommand;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Safe six ball autonomous mode. Shoots slower to ensure maximum probability of inner goal shots. Also ensures all
 * balls enter the intake straight on, in case the intake's vectoring capabilities break.
 */
public class EightBallAuton extends SequentialCommandGroup {
    public EightBallAuton() {
        //Properties
        double defaultMaxAcceleration = 50;
        double defaultMaxDeceleration = 40;
        double defaultMaxVelocity = 150;
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
                        new Transform(
                                AutonCoordinates.TRENCH_STARTING_POINT.add(AutonCoordinates.ROBOT_FRONT_TO_CENTER),
                                180),
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180)
                })
        );

        Path path2 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, 180),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 180), new Transform(AutonCoordinates.BALL_BACK_TRENCH_INNER
                        .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(-10))), 180 - 10),
                })
        );


        Path path3 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.BALL_BACK_TRENCH_INNER
                                .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(-10))), -10),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 0),
                })
        );


        Path path4 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 180),
                        new Transform(AutonCoordinates.BALL_BACK_TRENCH_OUTER
                                .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(10))), 180 + 10)
                })
        );


        Path path5 = new Path(PathGenerator.getInstance().generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.BALL_BACK_TRENCH_OUTER
                                .add(AutonCoordinates.ROBOT_BACK_TO_CENTER.rotateBy(new Rotation(10))), 10),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 0),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, 45)
                })
        );
        //Set odometry
        Odometry.getInstance().setTransform(new Transform(AutonCoordinates.TRENCH_STARTING_POINT, 0),
                Gyro.getInstance().getAngle());

        addCommands(
                new ParallelDeadlineGroup(
                        sequence(
                                new PathFollowerCommand(followerReversed, path1),
                                //new WaitUntilReadyToShootMacro(),
//                                new ShootForTimeMacro(3)
                                new WaitCommand(1.5)
                        )

//                        new MinimalVisionCommand()
                ),

                new SetShooterRpmCommand(0),
                new SetTurretMotorCommand(0),

                new ParallelDeadlineGroup(
                        sequence(
                                new PathFollowerCommand(followerReversed, path2),
                                new WaitCommand(.1),
                                new PathFollowerCommand(follower, path3),
                                new PathFollowerCommand(followerReversed, path4),
                                new WaitCommand(.1)
                        ),

                        sequence(
//                                new ParallelDeadlineGroup(
//                                        new WaitUntilPathFollowerWithinDistanceCommand(10),
//
//                                )
                                new IntakeBallCommand(.85, .25)
                                //new IntakeBallCommand(.6, .25)
                        ),
                        new AutoConveyorIndexCommand()
                ),

                new SetIntakeStopCommand(),
                new StopConveyorCommand(),

                new ParallelDeadlineGroup(
                        sequence(
                                new PathFollowerCommand(follower, path5),
                                sequence(
                                        //new WaitUntilReadyToShootMacro(),
//                                        new ShootForTimeMacro(3)
                                        new WaitCommand(3)
                                )
                        ),

                        sequence(
                                new WaitCommand(5)
//                                new MinimalVisionCommand()
                        ),
                        new IntakeBallCommand(.85, .25),
                        new AutoConveyorIndexCommand()
                ),

                new SetShooterRpmCommand(0),
                new SetTurretMotorCommand(0)
        );
    }
}