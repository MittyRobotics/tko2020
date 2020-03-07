package com.github.mittyrobotics.conveyor2;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IndexPositionCommand extends CommandBase {
    private double setpoint, initPos;
    public IndexPositionCommand(double setpoint){
        addRequirements(Conveyor2Subsystem.getInstance());
        this.setpoint = setpoint;
    }

    @Override
    public void initialize(){
        initPos = Conveyor2Subsystem.getInstance().getPosition();
        Conveyor2Subsystem.getInstance().setMotor(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        Conveyor2Subsystem.getInstance().stopMotor();
    }

    @Override
    public boolean isFinished() {
        return Conveyor2Subsystem.getInstance().getPosition() - initPos > setpoint;
    }
}
