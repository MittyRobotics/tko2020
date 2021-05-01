package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.util.RobotPositionTracker;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.ManualSetConveyorCommand;
import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.datatypes.units.Conversions;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.controllers.PathVelocityController;
import com.github.mittyrobotics.motion.controllers.PurePursuitController;
import com.github.mittyrobotics.motion.pathfollowing.PathFollowerProperties;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.shooter.commands.AutomatedShooterControlLoop;
import com.github.mittyrobotics.shooter.commands.AutomatedTurretControlLoop;
import edu.wpi.first.wpilibj2.command.*;

public class SixBallAuton extends ParallelCommandGroup {
    public SixBallAuton(){
        TurretSubsystem.getInstance().setControlLoopMaxPercent(.5);

        PurePursuitController follower = new PurePursuitController(
                new PathFollowerProperties(
                        new PathVelocityController(
                                80* Conversions.IN_TO_M,
                                80* Conversions.IN_TO_M,
                                100* Conversions.IN_TO_M,
                                0* Conversions.IN_TO_M,
                                0* Conversions.IN_TO_M,
                                .4,
                                40* Conversions.IN_TO_M
                        ), AutonConstants.DRIVETRAIN_TRACK_WIDTH* Conversions.IN_TO_M,
                        true,
                        false
                ),
                new PathFollowerProperties.PurePursuitProperties(100*Conversions.IN_TO_M)
        );

        PurePursuitController follower1 = new PurePursuitController(
                new PathFollowerProperties(
                        new PathVelocityController(
                                80* Conversions.IN_TO_M,
                                80* Conversions.IN_TO_M,
                                100* Conversions.IN_TO_M,
                                0* Conversions.IN_TO_M,
                                0* Conversions.IN_TO_M,
                                .4,
                                40* Conversions.IN_TO_M
                        ), AutonConstants.DRIVETRAIN_TRACK_WIDTH* Conversions.IN_TO_M,
                        false,
                        false
                ),
                new PathFollowerProperties.PurePursuitProperties(100*Conversions.IN_TO_M)
        );

        Transform currentTransform = RobotPositionTracker.getInstance().getFilterTransform();
        Path pathBack = new Path(PathGenerator.generateQuinticHermiteSplinePath(new Transform[]{
                new Transform(currentTransform.getPosition(), Math.toRadians(180)).inToM(),
                new Transform(currentTransform.getPosition().add(new Position(-115, 0)),  Math.toRadians(180)).inToM()
        }));
        Path pathForward = new Path(PathGenerator.generateQuinticHermiteSplinePath(new Transform[]{
                new Transform(currentTransform.getPosition().add(new Position(-115, 0)),  Math.toRadians(0)).inToM(),
                new Transform(currentTransform.getPosition().add(new Position(-30, 50)),  Math.toRadians(45)).inToM()
        }));

        addCommands(
                parallel(
                        new InstantCommand(()-> ShooterSubsystem.getInstance().setRampRate(.1)),
                        new AutomatedTurretControlLoop(),
                        new AutomatedShooterControlLoop()
                ),
                sequence(
                        new WaitCommand(1),
                        new InstantCommand(()-> ShooterSubsystem.getInstance().setRampRate(0.01)),
                        new ManualSetConveyorCommand(1, .5),
                        new FollowPath(follower, pathBack),
                        new ManualSetConveyorCommand(0, .3),
                        parallel(
                                new FollowPath(follower1, pathForward),
                                sequence(
                                        new WaitCommand(.5),
                                        new ManualSetConveyorCommand(1, .5)
                                )
                        )
                )
        );
    }

}
