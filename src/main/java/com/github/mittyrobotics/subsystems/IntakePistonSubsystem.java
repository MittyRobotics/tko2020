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

import com.github.mittyrobotics.constants.IntakeConstants;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePistonSubsystem extends SubsystemBase implements ISubsystem {
    private static IntakePistonSubsystem instance;
    private DoubleSolenoid intakePiston;

    private IntakePistonSubsystem() {
        super();
        setName("Intake Piston");
    }

    public static IntakePistonSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakePistonSubsystem();
        }
        return instance;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Intake Extended", isExtended());
    }

    @Override
    public void initHardware() {
        intakePiston =
                new DoubleSolenoid(IntakeConstants.SOLENOID_FORWQRD_CHANNEL, IntakeConstants.SOLENOID_REVERSE_CHANNEL);
    }

    public boolean isExtended() {
        return intakePiston.get() == DoubleSolenoid.Value.kForward;
    }

    public void extendIntake() {
        intakePiston.set(DoubleSolenoid.Value.kForward);
        ConveyorSubsystem.getInstance().resetBallCount();
    }

    public void retractIntake() {
        intakePiston.set(DoubleSolenoid.Value.kReverse);
        ConveyorSubsystem.getInstance().resetBallCount();
    }
}
