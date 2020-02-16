package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColor extends CommandBase {
    private int green = 0;
    private WheelColor color;


    public SpinToColor() {
        super();
        this.color = Spinner.getInstance().getGameMessage();

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
    }

    @Override
    public void end(boolean interrupted){
        //turn off motor
        Spinner.getInstance().setMotorOff();

        ColorPiston.getInstance().down();
    }

    @Override
    public boolean isFinished(){
        if(color == WheelColor.Green) {
            return green > 3;
        } else {
            return Spinner.getInstance().getColor() == color || color == WheelColor.None;
        }
    }
}