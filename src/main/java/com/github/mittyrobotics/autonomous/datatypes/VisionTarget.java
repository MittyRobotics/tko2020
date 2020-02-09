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

package com.github.mittyrobotics.autonomous.datatypes;

import com.github.mittyrobotics.datatypes.positioning.Rotation;

/**
 * Represents a detected vision target containing a turret-relative yaw, a field-relative yaw, and a distance
 */
public class VisionTarget {
    private Rotation turretRelativeYaw;
    private Rotation fieldRelativeYaw;
    private double distance;

    public VisionTarget() {
        this(new Rotation(), new Rotation(), 0);
    }

    public VisionTarget(Rotation turretRelativeYaw, Rotation fieldRelativeYaw, double distance) {
        this.turretRelativeYaw = turretRelativeYaw;
        this.fieldRelativeYaw = fieldRelativeYaw;
        this.distance = distance;
    }

    public Rotation getTurretRelativeYaw() {
        return turretRelativeYaw;
    }

    public void setTurretRelativeYaw(Rotation turretRelativeYaw) {
        this.turretRelativeYaw = turretRelativeYaw;
    }

    public Rotation getFieldRelativeYaw() {
        return fieldRelativeYaw;
    }

    public void setFieldRelativeYaw(Rotation fieldRelativeYaw) {
        this.fieldRelativeYaw = fieldRelativeYaw;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "VisionTarget{" +
                "turretRelativeYaw=" + turretRelativeYaw +
                ", fieldRelativeYaw=" + fieldRelativeYaw +
                ", distance=" + distance +
                '}';
    }
}
