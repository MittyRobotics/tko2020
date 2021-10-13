package com.github.mittyrobotics.autonomous.test;

import com.github.mittyrobotics.autonomous.commands.VisionTurretAim;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakeConstants;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.conveyor.commands.intake.IntakeBallCommand;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestCommand extends SequentialCommandGroup {
    public TestCommand(boolean drive, boolean vision) {
        if(drive) {
            addCommands(
                    sequence(
                            new WaitCommand(1),
                            new InstantCommand(()-> DrivetrainSubsystem.getInstance().overrideSetMotor(.3, .3), DrivetrainSubsystem.getInstance()),
                            new WaitCommand(1),
                            new InstantCommand(()-> DrivetrainSubsystem.getInstance().overrideSetMotor(-.3, -.3), DrivetrainSubsystem.getInstance()),
                            new WaitCommand(2),
                            new InstantCommand(()-> DrivetrainSubsystem.getInstance().stopMotor(), DrivetrainSubsystem.getInstance())
                    )
            );
        }
        addCommands(
                sequence(
                        new WaitCommand(1),
                        new InstantCommand(()-> IntakePistonSubsystem.getInstance().extendPiston(), IntakePistonSubsystem.getInstance()),
                        new WaitCommand(2),
                        new InstantCommand(()-> IntakeSubsystem.getInstance().overrideSetMotor(IntakeConstants.INTAKE_SPEED_FAST), IntakeSubsystem.getInstance()),
                        new WaitCommand(3),
                        new InstantCommand(()-> IntakeSubsystem.getInstance().overrideSetMotor(IntakeConstants.OUTTAKE_SPEED), IntakeSubsystem.getInstance()),
                        new WaitCommand(3),
                        new InstantCommand(()-> IntakeSubsystem.getInstance().stopMotor(), IntakeSubsystem.getInstance()),
                        new WaitCommand(1),
                        new InstantCommand(()-> IntakePistonSubsystem.getInstance().retractPiston(), IntakePistonSubsystem.getInstance()),
                        new WaitCommand(2),
                        new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                        new WaitCommand(3),
                        new InstantCommand(() -> ConveyorSubsystem.getInstance().outtakeBall(), ConveyorSubsystem.getInstance()),
                        new WaitCommand(3),
                        new InstantCommand(() -> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance()),
                        new TurretTest(0.2),
                        new TurretTest(-0.2)
                )
        );
        if(vision) {
            addCommands(
                    sequence(
                        new VisionTurretAim(true)
                    )
            );
        }
    }
}
