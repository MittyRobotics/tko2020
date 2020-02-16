package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.intake.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UnloadConveyor extends CommandBase {
    public UnloadConveyor(){
        addRequirements(Conveyor.getInstance(), Buffer.getInstance(), Intake.getInstance());
    }
    @Override
    public void execute(){
        Conveyor.getInstance().manualSetConveyorSpeed(1);
        Buffer.getInstance().manualBufferSpeed(.4);
        Intake.getInstance().intakeBall();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
