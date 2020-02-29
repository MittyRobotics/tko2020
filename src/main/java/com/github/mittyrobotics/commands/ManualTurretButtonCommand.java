package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ManualTurretButtonCommand extends CommandBase {
    double speed;
   public ManualTurretButtonCommand(double speed){
       this.speed = speed;
       addRequirements(TurretSubsystem.getInstance());
   }

   @Override
    public void initialize(){
       TurretSubsystem.getInstance().overrideSetTurretPercent(speed, true);
   }

   @Override
    public void end(boolean initialize){
       TurretSubsystem.getInstance().overrideSetTurretPercent(0, false);
   }

   @Override
   public boolean isFinished(){
       return true;
   }

}
