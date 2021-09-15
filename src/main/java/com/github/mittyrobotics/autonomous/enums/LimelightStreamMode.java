package com.github.mittyrobotics.autonomous.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The stream mode for the Limelight camera
 */
public enum LimelightStreamMode {
    /**
     * Side-by-side streams of vision and secondary camera.
     */
    Standard(0),
    /**
     * Vision camera as the main stream, secondary camera in corner.
     */
    Main(1),
    /**
     * Secondary camera as the main stream, vision camera in corner.
     */
    Secondary(2);

    private static Map map = new HashMap<>();

    static {
        for (LimelightStreamMode pageType : LimelightStreamMode.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public int value;

    LimelightStreamMode(int i) {
        value = i;
    }

    public static LimelightStreamMode valueOf(int i) {
        return (LimelightStreamMode) map.get(i);
    }
}