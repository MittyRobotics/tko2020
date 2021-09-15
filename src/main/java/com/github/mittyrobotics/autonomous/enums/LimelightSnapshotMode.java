package com.github.mittyrobotics.autonomous.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The snapshot mode for the Limelight camera
 */
public enum LimelightSnapshotMode {
    Off(0),
    On(1);

    private static Map map = new HashMap<>();

    static {
        for (LimelightSnapshotMode pageType : LimelightSnapshotMode.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public int value;

    LimelightSnapshotMode(int value) {
        value = value;
    }

    public static LimelightSnapshotMode valueOf(int i) {
        return (LimelightSnapshotMode) map.get(i);
    }
}