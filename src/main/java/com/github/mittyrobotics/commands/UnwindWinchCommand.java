package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.ClimberConstants;
import com.github.mittyrobotics.subsystems.WinchSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UnwindWinchCommand extends MoveWinchCommand {
    public UnwindWinchCommand(double leftPosition, double rightPosition){
        super(leftPosition, rightPosition);
    }
    public UnwindWinchCommand(){
        super(ClimberConstants.MIDDLE_UP_POSITION_LEFT, ClimberConstants.MIDDLE_UP_POSITION_RIGHT);
    }
}
