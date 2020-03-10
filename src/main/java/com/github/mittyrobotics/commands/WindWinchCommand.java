package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.ClimberConstants;

public class WindWinchCommand extends MoveWinchCommand {
    public WindWinchCommand() {
        super(ClimberConstants.CLIMBER_DOWN_POSITION, ClimberConstants.CLIMBER_DOWN_POSITION);
    }
}
