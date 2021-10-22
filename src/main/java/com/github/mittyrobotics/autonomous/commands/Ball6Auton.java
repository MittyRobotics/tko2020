package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Odometry;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.UnloadConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.intake.ExtendIntake;
import com.github.mittyrobotics.conveyor.commands.intake.IntakeBallCommand;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.spline.Path;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.motion.profiles.PathTrajectory;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingShooterControlLoop;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingTurretControlLoop;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

public class Ball6Auton extends SequentialCommandGroup {
    public Ball6Auton() {
        Path path1 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(180))),
                new Transform(new Vector2D(inches(-170), inches(0)), new Rotation(degrees(180))),
        });
        Path path2 = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(inches(0), inches(0)), new Rotation(degrees(0))),
                new Transform(new Vector2D(inches(100), inches(50)), new Rotation(degrees(45))),
        });
        PathTrajectory trajectory1 = new PathTrajectory(path1, inches(50), inches(60),
                0.5, 0, 0, 0, inches(60));
        PathTrajectory trajectory2 = new PathTrajectory(path2, inches(80), inches(80),
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
                                sequence(
                                        new WaitCommand(2),
                                        new UnloadConveyorCommand(true),
                                        new WaitCommand(2),
                                        new InstantCommand(()->ConveyorSubsystem.getInstance().overrideSetMotor(-0.3)),
                                        new WaitCommand(1.5),
                                        new InstantCommand(()->ConveyorSubsystem.getInstance().overrideSetMotor(0)),
                                        new WaitCommand(1),
                                        new InstantCommand(()->ConveyorSubsystem.getInstance().overrideSetMotor(-0.8))
                                ),
                                sequence(
                                        new WaitCommand(1),
                                        new InstantCommand(() -> {
                                            Odometry.getInstance().zeroEncoders(
                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                            Odometry.getInstance().zeroPosition();
                                        }),
                                        command1,
                                        new InstantCommand(() -> {
                                            Odometry.getInstance().zeroEncoders(
                                                    DrivetrainSubsystem.getInstance().getLeftPosition(),
                                                    DrivetrainSubsystem.getInstance().getRightPosition());
                                            Odometry.getInstance().zeroPosition();
                                        }),
                                        command2
                                )
                        ))
        );
    }
}
