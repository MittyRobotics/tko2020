package com.github.mittyrobotics.Intake;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.ConveyorSwitches;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeBall extends CommandBase {


    public IntakeBall(){
        super();
        addRequirements(IntakeSubsystem.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if(IntakeSubsystem.getInstance().getintakesensor().get()){
            IntakeSubsystem.getInstance().IntakeBall();
        }


    }



    @Override
    public boolean isFinished(){
        return false;
    }


}
