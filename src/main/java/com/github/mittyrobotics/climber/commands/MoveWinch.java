package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.RobotSide;
import com.github.mittyrobotics.climber.Winch;
import com.revrobotics.CANPIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class MoveWinch extends CommandBase {
    private CANPIDController controller;
    private double pos;
    private RobotSide side;

    public MoveWinch(double pos, RobotSide side) {
        this.pos = pos;
        this.side = side;
        addRequirements(Winch.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
//        double tempPos;
//        final double RAMP_RATE = 10;
//        if (side == RobotSide.LEFT) {
//            tempPos = Math.min(pos - Winch.getInstance().getLeftEncoder().getPosition(),
//                    Winch.getInstance().getLeftEncoder().getPosition() + RAMP_RATE);
//        } else {
//            tempPos = Math.min(pos - Winch.getInstance().getRightEncoder().getPosition(),
//                    Winch.getInstance().getRightEncoder().getPosition() + RAMP_RATE);
//        }
//            controller.setReference(tempPos, ControlType.kPosition);
        double tempPos;
        final double RAMP_RATE = 10;
        tempPos = Winch.getInstance().getEncoder(side).getPosition() + RAMP_RATE;
        if (RAMP_RATE > (pos - Winch.getInstance().getEncoder(side).getPosition())) {
            tempPos = pos;
        }
        Winch.getInstance().setReference(tempPos, side);
//        if (side == RobotSide.LEFT) {
//            tempPos = Winch.getInstance().getLeftEncoder().getPosition() + RAMP_RATE;
//            if (RAMP_RATE > (pos - Winch.getInstance().getLeftEncoder().getPosition())) {
//                tempPos = pos;
//            }
//            controller.setReference(tempPos, ControlType.kPosition);
//        }
//        else {
//            tempPos = Winch.getInstance().getRightEncoder().getPosition() + RAMP_RATE;
//            if (RAMP_RATE > (pos - Winch.getInstance().getRightEncoder().getPosition())) {
//                tempPos = pos;
//            }
//            Winch.getInstance().setReference(tempPos);
//        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(pos - Winch.getInstance().getEncoder(side).getPosition()) < 1;
    }
}
