package com.github.mittyrobotics.autonomous;

import com.github.mittyrobotics.core.math.geometry.Rotation;

public class VisionTarget {
    public final Rotation yaw;
    public final Rotation pitch;
    public final double distance;

    public VisionTarget(Rotation yaw, Rotation pitch, double distance){
        this.yaw = yaw;
        this.pitch = pitch;
        this.distance = distance;
    }
}