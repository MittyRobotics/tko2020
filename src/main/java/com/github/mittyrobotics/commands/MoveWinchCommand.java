package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.HooksSubsystem;
import com.github.mittyrobotics.constants.RobotSide;
import com.github.mittyrobotics.subsystems.WinchSubsystem;
import com.github.mittyrobotics.subsystems.WinchLockSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveWinchCommand extends CommandBase {

    private double setpoint;
    private double difference;
    private PIDController left, right, aux;


    public MoveWinchCommand(double setpoint, double difference) {
        this.setpoint = setpoint;
        this.difference = difference;
        addRequirements(WinchSubsystem.getInstance(), HooksSubsystem.getInstance(), WinchLockSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        left = new PIDController(0, 0, 0);
        left.setSetpoint(setpoint);
        right = new PIDController(0, 0, 0);
        right.setSetpoint(setpoint);
        aux = new PIDController(0, 0, 0);
        aux.setSetpoint(difference);
        WinchLockSubsystem.getInstance().unlockWinch();
        HooksSubsystem.getInstance().pushHooks();
    }

    @Override
    public void execute() {
        double lSpeed = left.calculate(WinchSubsystem.getInstance().getEncoderTicks(RobotSide.LEFT));
        double rSpeed = right.calculate(WinchSubsystem.getInstance().getEncoderTicks(RobotSide.RIGHT));
        double auxSpeed = aux.calculate(WinchSubsystem.getInstance().getEncoderTicks(RobotSide.LEFT) -
                WinchSubsystem.getInstance().getEncoderTicks(RobotSide.RIGHT));
        WinchSubsystem.getInstance().setSpeed(lSpeed + auxSpeed, RobotSide.LEFT);
        WinchSubsystem.getInstance().setSpeed(rSpeed - auxSpeed, RobotSide.RIGHT);
    }

    @Override
    public void end(boolean interrupted) {
        WinchSubsystem.getInstance().setSpeed(0, RobotSide.LEFT);
        WinchSubsystem.getInstance().setSpeed(0, RobotSide.RIGHT);
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
