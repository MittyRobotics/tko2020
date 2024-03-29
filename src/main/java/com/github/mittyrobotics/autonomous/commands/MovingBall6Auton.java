package com.github.mittyrobotics.autonomous.commands;

import com.github.mittyrobotics.autonomous.Autonomous;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.intake.ExtendIntake;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingShooterControlLoop;
import com.github.mittyrobotics.shooter.commands.ShootingWhileMovingTurretControlLoop;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class MovingBall6Auton extends SequentialCommandGroup {
    public MovingBall6Auton() {
        sequence(
                new ExtendIntake(),
                parallel(
                        new RunCommand(()-> Autonomous.getInstance().run()),
                        new ShootingWhileMovingShooterControlLoop(),
                        new ShootingWhileMovingTurretControlLoop(),
                        new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                        new InstantCommand(() -> IntakeSubsystem.getInstance().overrideSetMotor(IntakeConstants.INTAKE_SPEED_FAST), IntakeSubsystem.getInstance()),
                        sequence(
                                new WaitCommand(1),
                                new PIDDrive(265.0), // roughly past the 3rd ball in trench
                                new PIDDrive(-265.0) // backwards a tad
                        )
                )
        );
    }
}
