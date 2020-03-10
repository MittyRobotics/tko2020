package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.ClimberConstants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberArmsUpMacro extends SequentialCommandGroup {
    public ClimberArmsUpMacro(double leftSetpoint, double rightSetpoint){
        addCommands(new ClimberArmsUpCommand(), new UnlockWinchCommand(), new UnwindWinchCommand(leftSetpoint, rightSetpoint));
    }
    public ClimberArmsUpMacro(){
        this(ClimberConstants.MIDDLE_UP_POSITION_LEFT, ClimberConstants.MIDDLE_UP_POSITION_RIGHT);
    }
}
