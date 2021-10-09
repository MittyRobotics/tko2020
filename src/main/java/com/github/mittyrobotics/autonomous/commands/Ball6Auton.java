package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.intake.ExtendIntake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Ball6Auton extends SequentialCommandGroup {
    public Ball6Auton() {
        sequence(
                new ExtendIntake(),
                parallel(
                        new VisionTurretAim(true),
                        sequence(
                                new WaitCommand(1),
                                new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                                new WaitCommand(3),
                                new InstantCommand(() -> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance()),
                                parallel(
                                        new AutoConveyorCommand(),
                                        new PIDDrive(265.0), // roughly past the 3rd ball in trench
                                        new InstantCommand(() -> IntakeSubsystem.getInstance().overrideSetMotor(IntakeConstants.INTAKE_SPEED_FAST), IntakeSubsystem.getInstance())
                                ),
                                new PIDDrive(-60.0), // backwards a tad
                                new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                                new WaitCommand(3),
                                new InstantCommand(() -> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance()),
                                new InstantCommand(() -> IntakeSubsystem.getInstance().stopMotor(), IntakeSubsystem.getInstance())
                        )
                )
        );
    }
}
