package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Winch;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;


public class MoveWinch extends CommandBase {    //TODO

    private CANPIDController controller;
    private double pos;
    private Enum side;

    public MoveWinch(double pos, Enum side) {
        this.pos = pos;
        this.side = side;
        addRequirements(Winch.getInstance());
    }

    @Override
    public void initialize(){

    }
}
