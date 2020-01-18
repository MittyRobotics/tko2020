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
        if (!previousLimitSwitchValue1 && ConveyorSwitches.getInstance().getSwitch1()) { //no ball before and now detect ball before conveyor
            ++ConveyorSubsystem.getInstance().totalBallCount;
        }
        if(previousLimitSwitchValue2 && !ConveyorSwitches.getInstance().getSwitch2()){ //detect ball before and now no ball in buffer zone
            --ConveyorSubsystem.getInstance().totalBallCount;
        }
        previousLimitSwitchValue1 = ConveyorSwitches.getInstance().getSwitch1();
        previousLimitSwitchValue2 = ConveyorSwitches.getInstance().getSwitch2();

    }



    @Override
    public boolean isFinished(){
        return false;
    }


}
