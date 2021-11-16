package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Odometry;
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
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;
import static com.github.mittyrobotics.core.math.units.ConversionsKt.inches;

public class ShootingMovingTest extends SequentialCommandGroup {
    public ShootingMovingTest() {
        Odometry.getInstance().zeroEncoders(DrivetrainSubsystem.getInstance().getLeftPosition(), DrivetrainSubsystem.getInstance().getRightPosition());
        Odometry.getInstance().zeroHeading(Gyro.getInstance().getAngle360());
        Odometry.getInstance().zeroPosition();
        Path path = Path.Companion.quinticHermitePath(new Transform[]{
                new Transform(new Vector2D(0.0, 0.0), new Rotation(0)),
                new Transform(new Vector2D(inches(50), inches(50)), new Rotation(degrees(45))),
        });

        PathTrajectory trajectory = new PathTrajectory(path, inches(100), inches(100),
                0.5, 0, 0, 0, inches(50));

        addCommands(
                parallel(
                        new ShootingWhileMovingTurretControlLoop(),
                        new ShootingWhileMovingShooterControlLoop(),
                        new PathFollowingCommand(trajectory, true),
                        sequence(
                                new WaitCommand(1),
                                parallel(
                                        new UnloadConveyorCommand(true),
                                        new IntakeBallCommand(true)
                                )
                        )


                )
        );
    }
}
