package com.github.mittyrobotics.conveyor2;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoConveyorIndexCommand extends CommandBase {
    private State state;
    private double setpoint;
    public AutoConveyorIndexCommand(){
        addRequirements(Conveyor2Subsystem.getInstance());
    }

    @Override
    public void initialize() {
        state = State.STOPPING;
        setpoint = Conveyor2Subsystem.getInstance().getPosition();
    }

    @Override
    public void execute() {
        if(Conveyor2Subsystem.getInstance().getSwitch()){
            state = State.SENSING;
        }
        if(state == State.SENSING){
            Conveyor2Subsystem.getInstance().setMotor(1);
            if(!Conveyor2Subsystem.getInstance().getSwitch()){
                state = State.INDEXING;
                setpoint += 3;
            }
        } else if(state == State.INDEXING){
            Conveyor2Subsystem.getInstance().setMotor(1);
            if(setpoint < Conveyor2Subsystem.getInstance().getPosition()){
                state = State.STOPPING;
            }
        } else {
            Conveyor2Subsystem.getInstance().stopMotor();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private enum State{
        SENSING, INDEXING, STOPPING
    }
}
