package com.github.mittyrobotics.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class BallCheck extends CommandBase {

    private boolean previousLimitSwitchValue1 = false;
    private boolean previousLimitSwitchValue2 = false;

    public BallCheck(){
        super();
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if ((previousLimitSwitchValue1==false) && (!ConveyorSwitches.getInstance().getSwitch1())) {
            
        }

    }



    @Override
    public boolean isFinished(){
        return false;
    }


}
