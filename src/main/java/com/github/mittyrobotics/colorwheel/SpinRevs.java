package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinRevs extends CommandBase {
    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance(), ColorPiston.getInstance());
    }

    @Override
    public void initialize() {
        //sets motor to fast velocity
        ColorPiston.getInstance().up();
        Spinner.getInstance().zeroEncoder();
        Spinner.getInstance().setMotorFast();


    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        //turns off motor, updates status
        Spinner.getInstance().setMotorOff();
        ColorPiston.getInstance().down();
        OI.getInstance().passedStage2();
    }

    @Override
    public boolean isFinished() {
        //returns 3 revs completed (one inch times 100 inches for each revolution times 3 revolutions)
        return Spinner.getInstance().getRevolutions() > 3.5;
    }
}
