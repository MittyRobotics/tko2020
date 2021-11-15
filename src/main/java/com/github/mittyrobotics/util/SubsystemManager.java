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

import com.github.mittyrobotics.util.interfaces.ISubsystem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manages all of the subsystems
 */
public class SubsystemManager implements ISubsystem {
    /**
     * {@link SubsystemManager} instance
     */
    private static SubsystemManager instance;

    /**
     * {@link ArrayList} of subsystems
     */
    ArrayList<ISubsystem> subsystems;

    /**
     * instantiates a {@link SubsystemManager} and the subsystems {@link ArrayList}
     */
    private SubsystemManager() {
        subsystems = new ArrayList<>();
    }

    /**
     * Returns the {@link SubsystemManager} instance
     *
     * @return {@link SubsystemManager} instance
     */
    public static SubsystemManager getInstance() {
        if (instance == null) {
            instance = new SubsystemManager();
        }
        return instance;
    }

    /**
     * Adds {@link ISubsystem} to the {@link SubsystemManager}
     *
     * @param subsystems list of {@link ISubsystem}
     */
    public void addSubsystems(ISubsystem... subsystems) {
        this.subsystems.addAll(Arrays.asList(subsystems));
    }

    /**
     * Initializes hardware for each {@link ISubsystem}
     */
    @Override
    public void initHardware() {
        subsystems.forEach(ISubsystem::initHardware);
    }

    /**
     * Updates the dashboard for each {@link ISubsystem}
     */
    @Override
    public void updateDashboard() {
        subsystems.forEach(ISubsystem::updateDashboard);
    }
}