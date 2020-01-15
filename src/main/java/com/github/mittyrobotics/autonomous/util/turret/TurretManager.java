package com.github.mittyrobotics.autonomous.util.turret;

import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TurretManager extends CommandBase {

    private TurretManager instance;

    private TurretManager() {

    }

    public TurretManager getInstance() {
        if(instance == null) {
            instance = new TurretManager();
        }
        return instance;
    }

    public Transform computeTurretPosition(double distanceToTarget, double gyroAngle, double robotTurretAngle) { // assume target is (0,0)
        double fieldRelativeTurretAngle = gyroAngle - robotTurretAngle;

        Transform turretPosition = new Transform(
                -distanceToTarget * Math.cos(Math.toRadians(fieldRelativeTurretAngle)),
                -distanceToTarget * Math.sin(Math.toRadians(fieldRelativeTurretAngle)),
                fieldRelativeTurretAngle);
        return turretPosition;
    }
}
