package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ManualTurretButtonCommand extends InstantCommand {
   public ManualTurretButtonCommand(double speed){
       super(()-> TurretSubsystem.getInstance().overrideSetTurretPercent(speed, true), TurretSubsystem.getInstance());
   }
}
