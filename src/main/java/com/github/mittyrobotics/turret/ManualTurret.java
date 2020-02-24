package com.github.mittyrobotics.turret;

import com.github.mittyrobotics.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualTurret extends CommandBase {
    public ManualTurret(){
        addRequirements(Turret.getInstance());
    }

    @Override
    public void execute(){
        Turret.getInstance().setTurretPercent(OI.getInstance().getXboxController().getX(GenericHID.Hand.kRight));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(OI.getInstance().getXboxController().getX(GenericHID.Hand.kRight)) < 0.1;
    }
}
