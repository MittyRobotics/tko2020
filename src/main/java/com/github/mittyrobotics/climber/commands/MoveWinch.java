package com.GitHub.mittyrobotics.climber.commands;

import com.GitHub.mittyrobotics.climber.RobotSide;
import com.GitHub.mittyrobotics.climber.Winch;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;


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
    public void initialize(){
        Winch.getInstance().initHardware(side);
    }

    @Override
    public void execute(){
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
        tempPos = Winch.getInstance().getEncoder().getPosition() + RAMP_RATE;
        if (RAMP_RATE > (pos - Winch.getInstance().getEncoder().getPosition())) {
            tempPos = pos;
        }
        Winch.getInstance().setReference(tempPos);
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
    public boolean isFinished(){
        return Math.abs(pos - Winch.getInstance().getEncoder().getPosition()) < 1;
    }
}
