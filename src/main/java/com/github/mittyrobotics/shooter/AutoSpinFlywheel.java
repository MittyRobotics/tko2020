package com.github.mittyrobotics.shooter;

import com.github.mittyrobotics.autonomous.AutomatedTurretSuperstructure;
import com.github.mittyrobotics.autonomous.Vision;
import com.github.mittyrobotics.autonomous.VisionTarget;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoSpinFlywheel extends CommandBase {
    public AutoSpinFlywheel(){
        addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(Vision.getInstance().isSafeToUseVision()){
            VisionTarget target = Vision.getInstance().getLatestVisionTarget();

            Shooter.getInstance().setShooterSpeed(
                    AutomatedTurretSuperstructure.getInstance().computeShooterRPMFromDistance(target.getDistance()));
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
