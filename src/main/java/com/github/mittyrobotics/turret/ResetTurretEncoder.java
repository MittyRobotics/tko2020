package com.github.mittyrobotics.turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetTurretEncoder extends CommandBase {
    private double position;
    private boolean isDone = false;
    public ResetTurretEncoder(double position){
        super();
        this.position = position;
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void execute() {
        if (!TurretSubsystem.getInstance().getLimitSwitch().get()) {
            TurretSubsystem.getInstance().getTalon().set(ControlMode.PercentOutput, -.2);
        } else {
            TurretSubsystem.getInstance().getTalon().set(ControlMode.PercentOutput, 0);
            TurretSubsystem.getInstance().getTalon().setSelectedSensorPosition(0);
            isDone = true;

        }
    }
    @Override
    public boolean isFinished(){
        return isDone;
    }



}
