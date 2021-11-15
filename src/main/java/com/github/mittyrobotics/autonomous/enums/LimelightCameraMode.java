package com.github.mittyrobotics.autonomous.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The camera mode for the Limelight camera.
 */
public enum LimelightCameraMode {
    Vision(0),
    Driver(1);

    private static Map map = new HashMap<>();

    static {
        for (LimelightCameraMode pageType : LimelightCameraMode.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public int value;

    LimelightCameraMode(int i) {
        value = i;
    }

    public static LimelightCameraMode valueOf(int i) {
        return (LimelightCameraMode) map.get(i);
    }
}