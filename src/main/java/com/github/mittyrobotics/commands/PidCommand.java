package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.OI.OI;
import com.github.mittyrobotics.subsystems.Slider;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PidCommand extends CommandBase {
    private PIDController controller;
    public PidCommand(double setpoint, double threshold) {
        addRequirements(Slider.getInstance());
        this.setpoint = setpoint;
        this.threshold = threshold;
    }
    private double setpoint;
    private double threshold;

    @Override
    public void initialize() {
        super.initialize();
        controller = new PIDController(1.0, 0.0, 0.0);
        controller.setSetpoint(setpoint);

    }

    @Override
    public void execute() {
        double val = controller.calculate(Slider.getInstance().getPosition());
        Slider.getInstance().motorsWithLimitSwitch(val);
//        System.out.println(Slider.getInstance().getMotors()[1].getSelectedSensorPosition());


    }

    @Override
    public void end(boolean interrupted) {
        Slider.getInstance().setMotors(0);

    }

    @Override
    public boolean isFinished() {
        return Math.abs(controller.getPositionError()) < threshold;

    }

}
