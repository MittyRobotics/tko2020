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
