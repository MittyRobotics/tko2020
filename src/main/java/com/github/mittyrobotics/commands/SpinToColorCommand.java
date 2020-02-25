package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.ColorPistonSubsystem;
import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import com.github.mittyrobotics.constants.WheelColor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColorCommand extends CommandBase {
    private double prevPosition;
    private boolean onColor = false;
    private boolean finished = false;
    private int green = 0;
    private WheelColor color;


    public SpinToColorCommand(WheelColor color) {
        super();
        this.color = color;

        addRequirements(SpinnerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        System.out.println("Starting");
        ColorPistonSubsystem.getInstance().up();
        SpinnerSubsystem.getInstance().zeroEncoder();

        WheelColor cur = SpinnerSubsystem.getInstance().getColor();

        if ((cur == WheelColor.Green && color == WheelColor.Blue) ||
                (cur == WheelColor.Blue && color == WheelColor.Yellow) ||
                (cur == WheelColor.Yellow && color == WheelColor.Red) ||
                (cur == WheelColor.Red && color == WheelColor.Green)) {
            SpinnerSubsystem.getInstance().setMotorSlow(true);
        } else {
            SpinnerSubsystem.getInstance().setMotorSlow(false);
        }
    }

    @Override
    public void execute() {
        if (SpinnerSubsystem.getInstance().getColor() == WheelColor.Green) {
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
    public void end(boolean interrupted) {
        //turn off motor
        SpinnerSubsystem.getInstance().setMotorOff();

    }

    @Override
    public boolean isFinished() {
        if (color == WheelColor.Green) {
            if (green > 3) {
                return true;
            }
        } else {
            return SpinnerSubsystem.getInstance().getColor() == color;
        }
        return false;
    }
}