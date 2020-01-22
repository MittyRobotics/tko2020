/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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

package com.github.mittyrobotics.autonomous.vision;

import com.github.mittyrobotics.Gyro;
import com.github.mittyrobotics.autonomous.util.VisionTarget;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.turret.TurretSubsystem;

public class TurretSuperstructure {
    public static TurretSuperstructure instance = new TurretSuperstructure();
    public static TurretSuperstructure getInstance(){
        return instance;
    }

    private Rotation robotRelativeRotation;
    private Rotation fieldRelativeRotation;

    public void run(){
        this.robotRelativeRotation = new Rotation(TurretSubsystem.getInstance().getAngle());
        this.fieldRelativeRotation = computeFieldRelativeRotation(Gyro.getInstance().getRotation(),
                robotRelativeRotation);
    }

    /**
     * Maintains the field-relative angle of the turret based on an angle <code>setpoint</code>.
     *
     * @param setpoint the field-relative angle setpoint of the turret
     */
    public void maintainFieldRelativeRotation(Rotation setpoint){
        TurretSubsystem.getInstance().setAngle(computeRobotRelativeRotation(Gyro.getInstance().getRotation(),
                setpoint).getHeading());
    }

    /**
     * Aims the turret towards a {@link VisionTarget}.
     *
     * This sets the field-relative angle of the turret to the field-relative angle from the turret to the vision
     * target. This results in the turret using the gyro to maintain field-relative positioning while also aiming at
     * the vision target.
     *
     * @param target the {@link VisionTarget} to take aim at
     */
    public void visionAim(VisionTarget target){
        maintainFieldRelativeRotation(target.getFieldRelativeYaw());
    }

    private Rotation computeFieldRelativeRotation(Rotation gyro, Rotation robotRelativeRotation){
        return gyro.subtract(robotRelativeRotation);
    }

    private Rotation computeRobotRelativeRotation(Rotation gyro, Rotation fieldRelativeRotation){
        return gyro.subtract(fieldRelativeRotation);
    }

    public Rotation getRobotRelativeRotation() {
        return robotRelativeRotation;
    }

    public Rotation getFieldRelativeRotation() {
        return fieldRelativeRotation;
    }
}
