package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import com.github.mittyrobotics.climber.Winch;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;


public class MoveWinch extends CommandBase {    //TODO check

    private CANPIDController controller;
    private double pos;
    private double tempPos;
    private RobotSide side;
    private double rampRate = 10;
    public MoveWinch(double pos, RobotSide side) {
        this.pos = pos;
        this.side = side;
        addRequirements(Winch.getInstance());
    }

    @Override
    public void initialize(){
        if (side == RobotSide.LEFT) {
            controller = new CANPIDController(Winch.getInstance().getLeftSpark());
        } else {
            controller = new CANPIDController(Winch.getInstance().getRightSpark());
        }
        controller.setP(0.5);
        controller.setI(0);
        controller.setD(0);
        controller.setOutputRange(-.5, .5);
    }

    @Override
    public void execute(){
        if (side == RobotSide.LEFT) {
            tempPos = Math.min(pos - Winch.getInstance().getLeftEncoder().getPosition(),
                    Winch.getInstance().getLeftEncoder().getPosition() + rampRate);
        } else {
            tempPos = Math.min(pos - Winch.getInstance().getRightEncoder().getPosition(),
                    Winch.getInstance().getRightEncoder().getPosition() + rampRate);
        }
            controller.setReference(tempPos, ControlType.kPosition);
    }

    @Override
    public boolean isFinished(){
        return Math.abs(pos - Winch.getInstance().getLeftEncoder().getPosition()) < 1;
    }
}
