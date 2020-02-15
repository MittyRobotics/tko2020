package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveConveyorRemoveBall extends CommandBase {

    private double d1, d2 , conveyorInitPos, bufferInitPos;
    private boolean isDone;

    public MoveConveyorRemoveBall(double d1, double d2){
        super();
        this.d1 = d1;
        this.d2 = d2;
        addRequirements(Conveyor.getInstance(), Buffer.getInstance());
    }

    @Override
    public void initialize() {
        conveyorInitPos = Conveyor.getInstance().getPosition();
        bufferInitPos = Buffer.getInstance().getBufferPosition();
        isDone = false;
    }

    @Override
    public void execute() {
        double bufferDiff = Buffer.getInstance().getBufferPosition() - bufferInitPos;
        double conveyorDiff = Conveyor.getInstance().getPosition() - conveyorInitPos;
        if(bufferDiff < d1){
            Buffer.getInstance().manualBufferSpeed(-0.2);
            Conveyor.getInstance().manualSetConveyorSpeed(0);
        } else if(conveyorDiff < d2){
            Conveyor.getInstance().manualSetConveyorSpeed(0.2);
            Buffer.getInstance().manualBufferSpeed(0);
        } else {
            isDone = true;
        }
    }


    @Override
    public void end(boolean interrupted) {
        Conveyor.getInstance().setConveyorSpeed(0);
        Buffer.getInstance().manualBufferSpeed(0);
    }

    @Override
    public boolean isFinished(){
        return isDone;
    }

}
