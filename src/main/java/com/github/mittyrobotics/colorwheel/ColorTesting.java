package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class ColorTesting extends CommandBase {
    //counter
    private int count;

    //rgb values
    private double red;
    private double green;
    private double blue;
    public ColorTesting() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        //reset
        count = 0;
        red = 0;
        green = 0;
        blue = 0;
    }
    @Override
    public void execute(){
        //gets current rgb values
        double[] colors = Spinner.getInstance().getRGB();

        //updates variables
        red += colors[0];
        green += colors[1];
        blue += colors[2];

        count++;
    }
    @Override
    public void end(boolean interrupted){
        //print current recognized color
        System.out.println(Spinner.getInstance().getColor());

        //print rgb averages
        System.out.println("Red: "  + red/20);
        System.out.println("Green: " + green/20);
        System.out.println("Blue: " + blue/20);
        System.out.println();
    }
    @Override
    public boolean isFinished(){
        //20 cycles
        return count > 18;
    }
}
