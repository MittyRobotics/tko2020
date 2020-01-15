/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.autonomous.util.turret;

import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * TurretManager class. Manages the turret's position relative to the field and relative to vision targets.
 * <p>
 * Definitions:
 * Field-relative turret angle (<code>fieldTurretAngle</code>): the angle of the turret relative to the field. Does not care
 * about
 * the angle of the robot.
 * Robot-relative turret angle (<code>robotTurretAngle</code>): the angle of the turret relative to the robot. In other words,
 * the rotation of the turret offset from the turret's default location.
 */
public class TurretManager extends CommandBase {

    private TurretManager instance;

    private TurretManager() {

    }

    public TurretManager getInstance() {
        if (instance == null) {
            instance = new TurretManager();
        }
        return instance;
    }

    /**
     * Computes the {@link Transform} of the turret given parameters from the vision system.
     *
     * @param distanceToTarget the distance from the turret to the vision target (not camera!).
     * @param gyroAngle        the gyro angle of the robot.
     * @param robotTurretAngle the robot-relative turret angle.
     * @return the {@link Transform} that makes up the turret's position relative to the field.
     */
    public Transform computeTurretTransform(double distanceToTarget, double gyroAngle,
                                            double robotTurretAngle) {
        //Find field relative turret angle
        double fieldTurretAngle = gyroAngle - robotTurretAngle;

        //Target relative position
        Transform turretPosition = new Transform(
                -distanceToTarget * Math.cos(Math.toRadians(fieldTurretAngle)),
                -distanceToTarget * Math.sin(Math.toRadians(fieldTurretAngle)),
                fieldTurretAngle);

        //Add scoring zone to get field-relative position
        turretPosition = turretPosition.add(AutonCoordinates.SCORING_TARGET);

        return turretPosition;
    }
}
