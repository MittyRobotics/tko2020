package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.TurretSubsystem;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualTurretCommand extends CommandBase {
    public ManualTurretCommand() {
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void execute() {
        TurretSubsystem.getInstance().setTurretPercent(OI.getInstance().getXboxController().getX(GenericHID.Hand.kRight));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(OI.getInstance().getXboxController().getX(GenericHID.Hand.kRight)) < 0.1;
    }
}
