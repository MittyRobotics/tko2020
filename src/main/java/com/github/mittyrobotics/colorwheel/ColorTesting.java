package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ColorTesting extends CommandBase {
    int c;
    double red;
    double blue;
    double green;
    public ColorTesting() {
        super();
        addRequirements(Spinner.getInstance());
    }
    @Override
    public void initialize(){
        c = 0;
        red = 0;
        blue = 0;
        green = 0;
    }
    @Override
    public void execute(){
        double[] colors = Spinner.getInstance().getRGB();
        red += colors[0];
        blue += colors[1];
        green += colors[2];
        c++;
    }
    @Override
    public void end(boolean interrupted){
        System.out.println(Spinner.getInstance().getColor());
        System.out.println("Red: "  + red/20);
        System.out.println("Blue: " + blue/20);
        System.out.println("Green: " + green/20);
    }
    @Override
    public boolean isFinished(){
        return c > 18;
    }
}
