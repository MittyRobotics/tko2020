package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.ExtendIntake;
import com.github.mittyrobotics.conveyor.commands.WaitExtendIntake;
import com.github.mittyrobotics.drivetrain.DriveConstants;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Ball3Auton extends SequentialCommandGroup {
    public Ball3Auton() {
        addCommands(
                parallel(
//                        new ExtendIntake(),
                        parallel(
                                new VisionTurretAim(true),
                                sequence(
                                        new WaitCommand(1),
                                        new InstantCommand(()-> DrivetrainSubsystem.getInstance().overrideSetMotor(-.2, -.2)),
                                        new WaitCommand(1),
                                        new InstantCommand(()-> DrivetrainSubsystem.getInstance().stopMotor()),
                                        new WaitCommand(2),
                                        new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                                        new WaitCommand(4),
                                        new InstantCommand(() -> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance())
                                )

                        )
                )
        );
    }
}
