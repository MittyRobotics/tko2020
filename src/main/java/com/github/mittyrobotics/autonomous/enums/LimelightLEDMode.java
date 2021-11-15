package com.github.mittyrobotics.autonomous.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The LED mode for the Limelight camera
 */
public enum LimelightLEDMode {
    Pipeline(0),
    Off(1),
    Blinking(2),
    On(3);

    private static Map map = new HashMap<>();

    static {
        for (LimelightLEDMode pageType : LimelightLEDMode.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public int value;

    LimelightLEDMode(int i) {
        value = i;
    }

    public static LimelightLEDMode valueOf(int i) {
        return (LimelightLEDMode) map.get(i);
    }
}