package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.OI.OI;
import com.github.mittyrobotics.Robot;
import com.github.mittyrobotics.subsystems.Slider;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LogCommand extends CommandBase {
    private long startTime;
    public LogCommand() {
        addRequirements(Slider.getInstance());
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        if(Slider.getInstance().getSwitch(1)) {
            Slider.getInstance().setMotorsTicks(0.5);
        } else if (Slider.getInstance().getSwitch(0)) {
            Slider.getInstance().setMotorsTicks(-0.5);
        }
//        Robot.timeArray.add((double) System.currentTimeMillis() - startTime);
        SmartDashboard.putNumber("position", (double) Slider.getInstance().getMotors()[0].getSelectedSensorPosition());
//        Robot.posArray.add((double) Slider.getInstance().getMotors()[0].getSelectedSensorPosition());
//        Robot.voltArray.add((double) Slider.getInstance().getMotors()[0].getMotorOutputVoltage());
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
