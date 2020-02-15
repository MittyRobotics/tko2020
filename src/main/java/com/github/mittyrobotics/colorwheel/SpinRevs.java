package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.TICKS_PER_INCH;

public class SpinRevs extends CommandBase {
    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //sets motor to fast velocity
        System.out.println("Starting");
        ColorPiston.getInstance().up();
        Spinner.getInstance().zeroEncoder();

    }
    @Override
    public void execute(){
        //System.out.println(Spinner.getInstance().getVelocity());
        Spinner.getInstance().setMotorPID(480);
    }
    @Override
    public void end(boolean interrupted){
        //turns off motor, updates status
        Spinner.getInstance().setMotorOff();
        ColorPiston.getInstance().down();
        System.out.println("END");
        //OI.getInstance().passedStage2();
    }
    @Override
    public boolean isFinished(){
        return Spinner.getInstance().getRevolutions() > 3.5;
    }
}
