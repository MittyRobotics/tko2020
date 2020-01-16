package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Constants;
import com.github.mittyrobotics.climber.RobotSide;
import com.github.mittyrobotics.climber.Winch;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;


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
        // Initialize the SPARKS for the given side (specified in constructor)
        if (side == RobotSide.LEFT) {
            controller = Winch.getInstance().getLeftController();
        }
        else {
            controller = Winch.getInstance().getRightController();
        }

        // setup PID
        controller.setP(Constants.WINCH_PID_VALUES[0]);
        controller.setI(Constants.WINCH_PID_VALUES[1]);
        controller.setD(Constants.WINCH_PID_VALUES[2]);
        controller.setOutputRange(-1*Constants.PID_OUTPUT_RANGE, Constants.PID_OUTPUT_RANGE);
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
        if (side == RobotSide.LEFT) {
            tempPos = Winch.getInstance().getLeftEncoder().getPosition() + RAMP_RATE;
            if (RAMP_RATE > (pos - Winch.getInstance().getLeftEncoder().getPosition())) {
                tempPos = pos;
            }
            controller.setReference(tempPos, ControlType.kPosition);
        } else {
            tempPos = Winch.getInstance().getRightEncoder().getPosition() + RAMP_RATE;
            if (RAMP_RATE > (pos - Winch.getInstance().getRightEncoder().getPosition())) {
                tempPos = pos;
            }
            controller.setReference(tempPos, ControlType.kPosition);
        }
    }

    @Override
    public boolean isFinished(){
        return Math.abs(pos - Winch.getInstance().getLeftEncoder().getPosition()) < 1;
    }
}
