package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import com.github.mittyrobotics.climber.Winch;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;


public class MoveWinch extends CommandBase {    //TODO

    private CANPIDController controller;
    private double pos;
    private RobotSide side;

    public MoveWinch(double pos, RobotSide side) {
        this.pos = pos;
        this.side = side;
        addRequirements(Winch.getInstance());
    }

    @Override
    public void initialize(){
        if (side == RobotSide.LEFT) {
            controller = new CANPIDController(Winch.getInstance().getLeftSpark());       //TODO move to a command
            controller.setP(0.5);
            controller.setI(0);
            controller.setD(0);
            controller.setOutputRange(-.5, .5);
        }
    }
}
