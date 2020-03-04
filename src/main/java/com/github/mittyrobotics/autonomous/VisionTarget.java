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

package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;

public class VisionTarget {
    private Transform observerTransform;
    private Rotation observerYawToTarget;
    private double observerDistanceToTarget;

    public VisionTarget(Transform observerTransform, Rotation observerYawToTarget, double observerDistanceToTarget) {
        this.observerTransform = observerTransform;
        this.observerYawToTarget = observerYawToTarget;
        this.observerDistanceToTarget = observerDistanceToTarget;
    }

    public Transform getObserverTransform() {
        return observerTransform;
    }

    public void setObserverTransform(Transform observerTransform) {
        this.observerTransform = observerTransform;
    }

    public Rotation getObserverYawToTarget() {
        return observerYawToTarget;
    }

    public void setObserverYawToTarget(Rotation observerYawToTarget) {
        this.observerYawToTarget = observerYawToTarget;
    }

    public double getObserverDistanceToTarget() {
        return observerDistanceToTarget;
    }

    public void setObserverDistanceToTarget(double observerDistanceToTarget) {
        this.observerDistanceToTarget = observerDistanceToTarget;
    }
}
