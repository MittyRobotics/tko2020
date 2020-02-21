package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.REVS;
import static com.github.mittyrobotics.colorwheel.Constants.TICKS_PER_INCH;

public class SpinRevs extends CommandBase {

    boolean done;
    private double initPos;
    public SpinRevs() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //sets motor to fast velocity
        DriveTrainTalon.getInstance().setMotor(0.1, 0.1);
        System.out.println("Starting");
//        Spinner.getInstance().zeroEncoder();
        done = false;
        initPos = Spinner.getInstance().getRevolutions();

    }
    @Override
    public void execute(){
        //System.out.println(Spinner.getInstance().getEncoder());
        Spinner.getInstance().setMotorPID(480);

        if(Spinner.getInstance().getRevolutions() - initPos > REVS) {
            done = true;
            Spinner.getInstance().setMotor(0);
        }
    }
    @Override
    public void end(boolean interrupted){
        //turns off motor, updates status
        Spinner.getInstance().setMotor(0);
        DriveTrainTalon.getInstance().setMotor(0,0);
        ColorPiston.getInstance().down();
        System.out.println("END");
        //OI.getInstance().passedStage2();
    }
    @Override
    public boolean isFinished(){
        return done && Spinner.getInstance().getVelocity() == 0;
    }
}