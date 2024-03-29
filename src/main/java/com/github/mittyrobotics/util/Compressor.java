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


import com.github.mittyrobotics.util.interfaces.IHardware;

/**
 * Compressor Class in the form of a singleton
 */
public class Compressor extends edu.wpi.first.wpilibj.Compressor implements IHardware {

    /**
     * {@link Compressor} instance
     */
    private static Compressor instance;

    /**
     * Calls {@link edu.wpi.first.wpilibj.Compressor} constructor
     */
    private Compressor() {
        super();
    }

    /**
     * Returns the {@link Compressor} instance
     * @return the {@link Compressor} instance
     */
    public static Compressor getInstance() {
        if (instance == null) {
            instance = new Compressor();
        }
        return instance;
    }

    /**
     * Initializes the compressor with the correct settings
     * Starts the compressor
     */
    @Override
    public void initHardware() {
        start();
        setClosedLoopControl(true);
    }
}