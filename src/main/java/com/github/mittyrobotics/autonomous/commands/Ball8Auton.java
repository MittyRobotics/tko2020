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

public class Ball8Auton extends SequentialCommandGroup {
    public Ball8Auton() {
        Path path1 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-100), inches(0)), new Rotation(degrees(180))),
        });
        Path path2 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-94), inches(0)), new Rotation(degrees(180))),
        });
        Path path3 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-64), inches(-9.5)), new Rotation(degrees(180+25))),
        });
        Path path4 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-70), inches(0)), new Rotation(degrees(180))),
        });

        Path path5 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-70), inches(0)), new Rotation(degrees(180))),
        });


        PathTrajectory trajectory1 = new PathTrajectory(path1, inches(80), inches(50),
                0.5, 0, 0, 0, inches(80));
        PathTrajectory trajectory2 = new PathTrajectory(path2, inches(80), inches(50),
                0.5, 0, 0, 0, inches(80));
        PathFollowingCommand command1 = new PathFollowingCommand(trajectory1, true);
        PathFollowingCommand command2 = new PathFollowingCommand(trajectory2, false);
        addCommands(
                sequence(
//                        new ExtendIntake(),
                        parallel(
                                new ShootingWhileMovingTurretControlLoop(),
                                new ShootingWhileMovingShooterControlLoop(),
                                new IntakeBallCommand(true, -1),
                                new InstantCommand(()->ConveyorSubsystem.getInstance().overrideSetMotor(0)),
                                new AutoConveyorCommand(),
                                sequence(
                                        new WaitCommand(1),
                                        new InstantCommand(() -> {
                                            Odometry.getInstance().zeroEncoders(
                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                            Odometry.getInstance().zeroPosition();
                                        }),
                                        command1,
                                        new UnloadConveyorCommand(true),
                                        new WaitCommand(3),
                                        new AutoConveyorCommand(),
                                        new InstantCommand(() -> {
                                            Odometry.getInstance().zeroEncoders(
                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                            Odometry.getInstance().zeroPosition();
                                        }),
                                        command2,
                                        new UnloadConveyorCommand(true)
                                )
                        ))
        );
    }
}
