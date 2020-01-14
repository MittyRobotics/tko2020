package com.github.mittyrobotics.shooter;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.shooter.Constants.*;
//TODO why does this exist?
public class AdvancedSpin extends CommandBase {
    private CANPIDController controller1;
    private double speed;
    public AdvancedSpin(double speed){
        super();
        this.speed = speed;
        addRequirements(Shooter.getInstance());
    }
    @Override
    public void initialize(){
        controller1 = new CANPIDController(Shooter.getInstance().getSpark1());
        controller1.setP(ControllerP);
        controller1.setI(ControllerI);
        controller1.setD(ControllerD);
        controller1.setOutputRange(ControllerMinRange, ControllerMaxRange);
        controller1.setReference(speed, ControlType.kVelocity);
    }
}
