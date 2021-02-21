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

package com.github.mittyrobotics.autonomous.vision.enums;

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
        for (com.github.mittyrobotics.vision.enums.LimelightLEDMode pageType : com.github.mittyrobotics.vision.enums.LimelightLEDMode.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public int value;

    LimelightLEDMode(int i) {
        value = i;
    }

    public static com.github.mittyrobotics.vision.enums.LimelightLEDMode valueOf(int i) {
        return (com.github.mittyrobotics.vision.enums.LimelightLEDMode) map.get(i);
    }
}
