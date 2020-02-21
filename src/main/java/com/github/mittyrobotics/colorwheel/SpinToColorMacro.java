package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.drive.ColorWheelDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class SpinToColorMacro extends SequentialCommandGroup {
    public SpinToColorMacro(WheelColor color){
        addCommands(new ColorUp(), new WaitCommand(.5), new ColorWheelDrive(), new SpinToColor(color));
    }
}
