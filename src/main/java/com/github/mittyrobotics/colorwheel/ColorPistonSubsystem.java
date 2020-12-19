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

package com.github.mittyrobotics.colorwheel;

import com.github.mittyrobotics.util.interfaces.IPistonSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Color Piston Subsystem to extend the {@link SpinnerSubsystem}
 */
public class ColorPistonSubsystem extends SubsystemBase implements IPistonSubsystem {

    /**
     * {@link ColorPistonSubsystem} instance
     */
    private static ColorPistonSubsystem instance;

    /**
     * Piston's {@link DoubleSolenoid}
     */
    private DoubleSolenoid piston;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Color Piston"
     */
    private ColorPistonSubsystem() {
        super();
        setName("Color Piston");
    }

    /**
     * Returns the {@link ColorPistonSubsystem} instance.
     *
     * @return the {@link ColorPistonSubsystem} instance.
     */
    public static ColorPistonSubsystem getInstance() {
        if (instance == null) {
            instance = new ColorPistonSubsystem();
        }
        return instance;
    }

    /**
     * Initializes the piston's hardware
     */
    @Override
    public void initHardware() {
        piston = new DoubleSolenoid(ColorWheelConstants.SOLENOID_FOWARD_CHANNEL,
                ColorWheelConstants.SOLENOID_REVERSE_CHANNEL);
    }

    /**
     * Updates the piston's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Color Spinner Up", isPistonExtended());
    }

    /**
     * Extends the piston
     */
    @Override
    public void extendPiston() {
        piston.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Retracts the piston
     */
    @Override
    public void retractPiston() {
        piston.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Returns if the piston is extended
     *
     * @return if the piston is extended
     */
    @Override
    public boolean isPistonExtended() {
        return piston.get() != DoubleSolenoid.Value.kForward;
    }
}
