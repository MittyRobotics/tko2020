package com.github.mittyrobotics.climber.commands;

import com.github.mittyrobotics.climber.Hooks;
import com.github.mittyrobotics.climber.RobotSide;
import com.github.mittyrobotics.climber.Winch;
import com.github.mittyrobotics.climber.WinchLock;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveWinch extends CommandBase {

    private double setpoint;
    private double difference;
    private PIDController left, right, aux;


    public MoveWinch(double setpoint, double difference) {
        this.setpoint = setpoint;
        this.difference = difference;
        addRequirements(Winch.getInstance(), Hooks.getInstance(), WinchLock.getInstance());
    }

    @Override
    public void initialize() {
        left = new PIDController(0, 0, 0);
        left.setSetpoint(setpoint);
        right = new PIDController(0, 0, 0);
        right.setSetpoint(setpoint);
        aux = new PIDController(0, 0, 0);
        aux.setSetpoint(difference);
        WinchLock.getInstance().unlockWinch();
        Hooks.getInstance().pushHooks();
    }

    @Override
    public void execute() {
        double lSpeed = left.calculate(Winch.getInstance().getEncoderTicks(RobotSide.LEFT));
        double rSpeed = right.calculate(Winch.getInstance().getEncoderTicks(RobotSide.RIGHT));
        double auxSpeed = aux.calculate(Winch.getInstance().getEncoderTicks(RobotSide.LEFT) -
                Winch.getInstance().getEncoderTicks(RobotSide.RIGHT));
        Winch.getInstance().setSpeed(lSpeed + auxSpeed, RobotSide.LEFT);
        Winch.getInstance().setSpeed(rSpeed - auxSpeed, RobotSide.RIGHT);
    }

    @Override
    public void end(boolean interrupted) {
        Winch.getInstance().setSpeed(0, RobotSide.LEFT);
        Winch.getInstance().setSpeed(0, RobotSide.RIGHT);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

/*
setpoint, difference
    Pid Controller left = new controller;
    right
    both
    left setpoint = setpoint
    right setpoint = setpoint
    both setpoint = difference
    lspeed = left.calculate(leftEnc);
    rspeed  = right. "     "(rightEnc);
    bothspeed - both.calculate(leftEnc-rightEnc);
    leftSide.set(lSpeed + bothSpeed);
    rightSide.set(rSpeed - bothSpeed);
 */
