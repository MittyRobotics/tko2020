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

package com.github.mittyrobotics.interfaces;

/**
 * Interface for all motor based subsystem classes
 */
public interface IMotorSubsystem extends ISubsystem {
    /**
     * Stops the motor from moving
     */
    default void stopMotor() {
        setMotor(0);
    }

    /**
     * Sets the motor to run at a certain percent (using duty cylce)
     *
     * @param percent the percentage to run the motor at
     */
    void setMotor(double percent);

    /**
     * Resets an encoder position
     */
    default void resetEncoder() {

    }

    /**
     * Gets an encoder position
     *
     * @return an encoder position
     */
    default double getPosition() {
        return 0;
    }

    /**
     * Gets an encoder velocity
     *
     * @return an encoder velocity
     */
    default double getVelocity() {
        return 0;
    }

    /**
     * Gets if a limit switch
     *
     * @return if a limit switch is being pressed
     */
    default boolean getSwitch() {
        return false;
    }

}