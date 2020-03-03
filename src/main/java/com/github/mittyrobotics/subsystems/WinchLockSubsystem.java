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

import com.github.mittyrobotics.constants.ClimberConstants;
import com.github.mittyrobotics.util.interfaces.IPistonSubsystem;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WinchLockSubsystem extends SubsystemBase implements IPistonSubsystem {
    private static WinchLockSubsystem instance;

    private PWM linearActuatorLeft, linearActuatorRight;

    public WinchLockSubsystem() {
        super();
        setName("Winch Lock");
    }

    public static WinchLockSubsystem getInstance() {
        if (instance == null) {
            instance = new WinchLockSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        linearActuatorLeft = new PWM(ClimberConstants.LEFT_ACTUATOR_ID);
        linearActuatorRight = new PWM(ClimberConstants.RIGHT_ACTATOR_ID);
    }


    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Is Winch Locked", isPistonExtended());
    }

    @Override
    public void extendPiston() {
        linearActuatorLeft.setPosition(1);
        linearActuatorRight.setPosition(1);
    }

    @Override
    public void retractPiston() {
        linearActuatorLeft.setPosition(0);
        linearActuatorRight.setPosition(0);
    }

    @Override
    public boolean isPistonExtended() {
        return linearActuatorLeft.getPosition() == 0 && linearActuatorRight.getPosition() == 0;
    }

}
