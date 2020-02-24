package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.ArrayList;

import static com.github.mittyrobotics.colorwheel.Constants.TICKS_PER_INCH;

public class SpinToColor extends CommandBase {
    private double prevPosition;
    private boolean onColor = false;
    private boolean finished = false;
    private int green = 0;
    private WheelColor color;


    public SpinToColor(WheelColor color) {
        super();
        this.color = color;

        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        System.out.println("Starting");
        ColorPiston.getInstance().up();
        Spinner.getInstance().zeroEncoder();

        WheelColor cur = Spinner.getInstance().getColor();

        if((cur == WheelColor.Green && color == WheelColor.Blue)||(cur == WheelColor.Blue && color == WheelColor.Yellow) || (cur == WheelColor.Yellow && color == WheelColor.Red) || (cur == WheelColor.Red && color == WheelColor.Green)) {
            Spinner.getInstance().setMotorSlow(true);
        } else {
            Spinner.getInstance().setMotorSlow(false);
        }
    }
    @Override
    public void execute(){
        if(Spinner.getInstance().getColor() == WheelColor.Green){
            green += 1;
        } else {
            green = 0;
        }
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



    }
    @Override
    public void end(boolean interrupted){
        //turn off motor
        Spinner.getInstance().setMotorOff();

    }
    @Override
    public boolean isFinished(){
        if(color == WheelColor.Green) {
            if(green > 3) {
                return true;
            }
        } else {
            return Spinner.getInstance().getColor() == color;
        }
        return false;
    }
}