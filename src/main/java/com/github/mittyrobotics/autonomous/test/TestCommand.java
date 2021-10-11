package com.github.mittyrobotics.autonomous.test;

import com.github.mittyrobotics.autonomous.commands.VisionTurretAim;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestCommand extends SequentialCommandGroup {
    public TestCommand() {
        addCommands(
            sequence(
                    new WaitCommand(1),
                    new InstantCommand(()-> DrivetrainSubsystem.getInstance().overrideSetMotor(.3, .3), DrivetrainSubsystem.getInstance()),
                    new WaitCommand(1),
                    new InstantCommand(()-> DrivetrainSubsystem.getInstance().overrideSetMotor(-.3, -.3), DrivetrainSubsystem.getInstance()),
                    new WaitCommand(2),
                    new InstantCommand(()-> DrivetrainSubsystem.getInstance().stopMotor(), DrivetrainSubsystem.getInstance()),
                    new InstantCommand(()-> IntakePistonSubsystem.getInstance().extendPiston(), IntakePistonSubsystem.getInstance()),
                    new WaitCommand(2),
                    new InstantCommand(()-> IntakePistonSubsystem.getInstance().retractPiston(), IntakePistonSubsystem.getInstance()),
                    new WaitCommand(2),
                    new InstantCommand(() -> ConveyorSubsystem.getInstance().shootBall(), ConveyorSubsystem.getInstance()),
                    new WaitCommand(2),
                    new InstantCommand(() -> ConveyorSubsystem.getInstance().outtakeBall(), ConveyorSubsystem.getInstance()),
                    new WaitCommand(2),
                    new InstantCommand(() -> ConveyorSubsystem.getInstance().stopMotor(), ConveyorSubsystem.getInstance()),
                    new TurretTest(0.2),
                    new TurretTest(-0.2),
                    new VisionTurretAim(true)
            )
        );
    }
}
