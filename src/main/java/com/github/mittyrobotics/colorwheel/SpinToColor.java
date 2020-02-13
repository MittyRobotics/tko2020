
package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.colorwheel.Constants.TICKS_PER_INCH;

public class SpinToColor extends CommandBase {
    private double prevPosition;
    private boolean onColor = false;
    private boolean finished = false;
    private int green = 0;

    public SpinToColor() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //ColorPiston.getInstance().up();
        Spinner.getInstance().zeroEncoder();
        Spinner.getInstance().setMotorPID(30*8);
    }
    @Override
    public void execute(){
        /*if(Spinner.getInstance().matching()){
            //prevPosition = Spinner.getInstance().getRevolutions();
            //onColor = true;
            finished = true;
        }
/*        if(onColor){
            if(Spinner.getInstance().getRevolutions() - prevPosition > 1.0/16.0) {
                finished = true;
            }
        }*/

        System.out.println(Spinner.getInstance().getColor());


    }
    @Override
    public void end(boolean interrupted){
        //turn off motor
        //ColorPiston.getInstance().down();
        Spinner.getInstance().setMotorOff();
    }
    @Override
    public boolean isFinished(){
        return Spinner.getInstance().getColor() == WheelColor.Blue;
        //return false;
    }
}
