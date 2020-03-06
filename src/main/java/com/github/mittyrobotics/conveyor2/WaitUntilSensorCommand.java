package com.github.mittyrobotics.conveyor2;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class WaitUntilSensorCommand extends WaitUntilCommand {
    public WaitUntilSensorCommand() {
        super(()->!Conveyor2Subsystem.getInstance().getSwitch());
        addRequirements(Conveyor2Subsystem.getInstance());
    }

    @Override
    public void initialize(){
        Conveyor2Subsystem.getInstance().setMotor(1);
    }

    @Override
    public void end(boolean interrupted) {
        new IndexPositionCommand(4).schedule();
    }
}
