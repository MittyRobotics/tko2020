package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.AutoConveyorCommand;
import com.github.mittyrobotics.conveyor.commands.ExtendIntake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Ball3Auton extends SequentialCommandGroup {
    public Ball3Auton() {
        sequence(
                new ExtendIntake(),
                parallel(
                        new VisionTurretAim(true),
                        sequence(
                                new WaitCommand(1),
                                new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                                new WaitCommand(3),
                                new InstantCommand(() -> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance())
                        )
                )
        );
    }
}
