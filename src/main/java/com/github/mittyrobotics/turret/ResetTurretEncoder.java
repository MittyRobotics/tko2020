package com.github.mittyrobotics.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetTurretEncoder extends CommandBase {
    private boolean isDone = false;

    public ResetTurretEncoder() {
        super();
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void execute() {

        TurretSubsystem.getInstance().zeroEncoder();
        isDone = true;
//        if (!TurretSubsystem.getInstance().limitSwitchValue()) {
//            TurretSubsystem.getInstance().setTurretSpeed(-.2);
//        } else {
//            TurretSubsystem.getInstance().setTurretSpeed(0);
//            TurretSubsystem.getInstance().setAngle(0);
//            isDone = true;
//        }
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }


}
