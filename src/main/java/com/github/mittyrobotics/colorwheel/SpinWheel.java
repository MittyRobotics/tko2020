package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpinWheel extends SequentialCommandGroup {
    public SpinWheel(){
        super();
        if(Spinner.getInstance().getGameMessage() == WheelColor.None){
            addCommands(new SpinRevsMacro());
        } else {
            addCommands(new SpinToColor(Spinner.getInstance().getGameMessage()));
        }
    }
}