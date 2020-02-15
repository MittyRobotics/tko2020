package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UnloadConveyor extends CommandBase {
    public UnloadConveyor(){
        addRequirements(Conveyor.getInstance(), Buffer.getInstance());
    }
    @Override
    public void execute(){
        Conveyor.getInstance().manualSetConveyorSpeed(1);
        Buffer.getInstance().manualBufferSpeed(.4);
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
