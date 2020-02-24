package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.shooter.Shooter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UnloadConveyor extends CommandBase {
    public UnloadConveyor() {
        addRequirements(Conveyor.getInstance(), Buffer.getInstance(), Shooter.getInstance());
    }

    @Override
    public void execute() {
        Conveyor.getInstance().manualSetConveyorSpeed(.4);
        Buffer.getInstance().manualBufferSpeed(.4);
        Shooter.getInstance().setShooterPercent(.25);
//        Intake.getInstance().intakeBall();
    }

    @Override
    public boolean isFinished() {
        return DriverStation.getInstance().isDisabled();
    }
}
