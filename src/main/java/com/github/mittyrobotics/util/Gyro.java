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

package com.github.mittyrobotics.util;

import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.util.interfaces.IHardware;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;

/**
 * Gyro class in the form of a singleton
 */
public class Gyro extends ADXRS450_Gyro implements IHardware {

    /**
     * {@link Gyro} instance
     */
    private static Gyro instance = new Gyro();

    /**
     * Calls {@link ADXRS450_Gyro} constructor
     */
    private Gyro() {
        super();
    }

    /**
     * Returns the {@link Gyro} instance
     *
     * @return {@link Gyro} instance
     */
    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }

    /**
     * Gets the angle of the {@link ADXRS450_Gyro}
     *
     * @return {@link ADXRS450_Gyro} angle
     */
    @Override
    public double getAngle() {
        return -super.getAngle();
    }

    /**
     * Returns the angle in the form of a {@link Rotation}
     *
     * @return {@link Rotation} angle
     */
    public Rotation getRotation() {
        return new Rotation(degrees(getAngle360()));
    }

    /**
     * Gets the gyro angle between 0-360
     *
     * @return angle between 0-360
     */
    public double getAngle360() {
        double angle = getAngle();
        angle %= 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Initializes the gyro with correct settings
     */
    @Override
    public void initHardware() {
        reset();
        calibrate();
    }
}