package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BackJustifiedConveyor extends CommandBase {
    private double startPos;
    private double setpoint;
    private boolean isDone;
    public BackJustifiedConveyor(){
        super();
        addRequirements(Conveyor.getInstance(), Buffer.getInstance());
    }

    @Override
    public void initialize() {
        startPos = Conveyor.getInstance().getPosition();
        switch (Conveyor.getInstance().getTotalBallCount()){
            case 1:
                setpoint = 4;
                break;
            case 2:
                setpoint = 3;
                break;
            case 3:
                setpoint = 2;
                break;
            case 4:
                setpoint = 1;
                break;
            case 5:
                setpoint = 0;
                break;
        }
        Conveyor.getInstance().setConveyorSpeed(1);
        Buffer.getInstance().manualBufferSpeed(1);
    }

    @Override
    public void execute(){
        if(Conveyor.getInstance().getPosition() - startPos > setpoint){
            isDone = true;
        }
    }

    @Override
    public void end(boolean interrupted){
        Conveyor.getInstance().setConveyorSpeed(0);
        Buffer.getInstance().manualBufferSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }
}