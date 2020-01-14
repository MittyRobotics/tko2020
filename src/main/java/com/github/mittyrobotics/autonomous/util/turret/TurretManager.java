package com.github.mittyrobotics.autonomous.util.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretManager extends CommandBase {

    private TurretManager instance;

    private double gyroAngle;
    private double fieldRelativeAngle;
    private double robotRelativeTurretAngle;

    private TurretManager() {

    }

    public TurretManager getInstance() {
        if(instance == null) {
            instance = new TurretManager();
        }
        return instance;
    }

}
