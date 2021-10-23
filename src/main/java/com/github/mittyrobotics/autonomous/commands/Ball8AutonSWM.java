package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.UnloadConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.intake.IntakeBallCommand;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.spline.Path;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.profiles.PathTrajectory;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingShooterControlLoop;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingTurretControlLoop;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

public class Ball8AutonSWM extends SequentialCommandGroup {
    public Ball8AutonSWM() {
        Path path1r = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-170), inches(0)), new Rotation(degrees(180))),
        });
        Path path2r = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-65), inches(-12)), new Rotation(degrees(180))),
        });
        Path path3f = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(0))),
                new Transform(new Vector2D(inches(65), inches(12)), new Rotation(degrees(0))),
        });
        Path path4r = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-65), inches(8)), new Rotation(degrees(180))),
        });
        Path path5f = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(0))),
                new Transform(new Vector2D(inches(80), inches(-8)), new Rotation(degrees(0))),
        });
        PathTrajectory trajectory1 = new PathTrajectory(path1r, inches(70), inches(80),
                Double.POSITIVE_INFINITY, 0, 0, 0, inches(100));
        PathTrajectory trajectory2 = new PathTrajectory(path2r, inches(70), inches(80),
                Double.POSITIVE_INFINITY, 0, 0, 0, inches(100));
        PathTrajectory trajectory3 = new PathTrajectory(path3f, inches(70), inches(80),
                Double.POSITIVE_INFINITY, 0, 0, 0, inches(100));
        PathTrajectory trajectory4 = new PathTrajectory(path4r, inches(70), inches(80),
                Double.POSITIVE_INFINITY, 0, 0, 0, inches(100));
        PathTrajectory trajectory5 = new PathTrajectory(path5f, inches(70), inches(80),
                Double.POSITIVE_INFINITY, 0, 0, 0, inches(100));
        PathFollowingCommand command1 = new PathFollowingCommand(trajectory1, true);
        PathFollowingCommand command2 = new PathFollowingCommand(trajectory2, true);
        PathFollowingCommand command3 = new PathFollowingCommand(trajectory3, false);
        PathFollowingCommand command4 = new PathFollowingCommand(trajectory4, true);
        PathFollowingCommand command5 = new PathFollowingCommand(trajectory5, false);
        addCommands(
                sequence(
//                        new ExtendIntake(),
                        parallel(
                                new ShootingWhileMovingTurretControlLoop(),
                                new ShootingWhileMovingShooterControlLoop(),
                                sequence(
                                        race(
                                                parallel(
                                                        new IntakeBallCommand(true, -0.7),
                                                        sequence(
                                                                new InstantCommand(() -> ConveyorSubsystem.getInstance().overrideSetMotor(0)),
                                                                new WaitCommand(2.0),
                                                                new UnloadConveyorCommand(true)
                                                        )
                                                ),
                                                sequence(
                                                        sequence(
                                                                new WaitCommand(0.5),
                                                                new InstantCommand(() -> {
                                                                    Odometry.getInstance().zeroEncoders(
                                                                            DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                                            DrivetrainSubsystem.getInstance().getRightPosition());
                                                                    Odometry.getInstance().zeroPosition();
                                                                }),
                                                                command1
                                                        ),
                                                        new WaitCommand(1.5)
                                                )
                                        ),
                                        race(
                                                new AutoConveyorCommand(),
                                                new IntakeBallCommand(true, -0.6),
                                                sequence(
                                                        new InstantCommand(() -> {
                                                            Odometry.getInstance().zeroEncoders(
                                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                                            Odometry.getInstance().zeroPosition();
                                                        }),
                                                        command2,
                                                        new InstantCommand(() -> {
                                                            Odometry.getInstance().zeroEncoders(
                                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                                            Odometry.getInstance().zeroPosition();
                                                        }),
                                                        command3,
                                                        new InstantCommand(() -> {
                                                            Odometry.getInstance().zeroEncoders(
                                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                                            Odometry.getInstance().zeroPosition();
                                                        }),
                                                        command4,
                                                        new InstantCommand(() -> {
                                                            Odometry.getInstance().zeroEncoders(
                                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                                            Odometry.getInstance().zeroPosition();
                                                        }),
                                                        command5
                                                )
                                        ),
                                        new UnloadConveyorCommand(true)
                                )

                        )
                )

        );
    }
}
