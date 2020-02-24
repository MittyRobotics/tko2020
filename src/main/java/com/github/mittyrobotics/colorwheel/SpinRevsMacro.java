package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.drive.ColorWheelDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class SpinRevsMacro extends SequentialCommandGroup {
    public SpinRevsMacro(){
        super();
        addCommands(new SpinnerUp(), new WaitCommand(.5), new ColorWheelDrive(), new SpinRevs());
    }
}