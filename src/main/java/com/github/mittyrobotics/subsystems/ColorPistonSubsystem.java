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

package com.github.mittyrobotics.subsystems;

import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.github.mittyrobotics.constants.ColorWheelConstants.SOLENOID_FOWARD_CHANNEL;
import static com.github.mittyrobotics.constants.ColorWheelConstants.SOLENOID_REVERSE_CHANNEL;

public class ColorPistonSubsystem extends SubsystemBase implements ISubsystem {

    private static ColorPistonSubsystem instance;
    private DoubleSolenoid piston;
    private boolean isPistonUp;

    private ColorPistonSubsystem() {
        super();
        setName("Color Piston");
    }

    public static ColorPistonSubsystem getInstance() {
        if (instance == null) {
            instance = new ColorPistonSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        piston = new DoubleSolenoid(SOLENOID_FOWARD_CHANNEL, SOLENOID_REVERSE_CHANNEL);
        isPistonUp = false;
    }

    @Override
    public void updateDashboard() {

    }

    public void up() {
        piston.set(DoubleSolenoid.Value.kForward);
        isPistonUp = true;
    }

    public void down() {
        piston.set(DoubleSolenoid.Value.kReverse);
        isPistonUp = false;
    }

    public boolean isPistonUp() {
        return isPistonUp;
    }
}