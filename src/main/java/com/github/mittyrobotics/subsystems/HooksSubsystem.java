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
import com.github.mittyrobotics.constants.RobotSide;
import com.github.mittyrobotics.util.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HooksSubsystem extends SubsystemBase implements ISubsystem {
    private static HooksSubsystem ourInstance = new HooksSubsystem();
    private DoubleSolenoid leftPiston, rightPiston;

    private HooksSubsystem() {
        super();
    }

    public static HooksSubsystem getInstance() {
        return ourInstance;
    }

    @Override
    public void initHardware() {
        leftPiston =
                new DoubleSolenoid(ClimberConstants.LEFT_PISTON_FORWARD_ID, ClimberConstants.LEFT_PISTON_REVERSE_ID);
        rightPiston =
                new DoubleSolenoid(ClimberConstants.RIGHT_PISTON_FORWARD_ID, ClimberConstants.RIGHT_PISTON_REVERSE_ID);
    }

    @Override
    public void updateDashboard() {

    }

    public void pushHooks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftPiston.set(DoubleSolenoid.Value.kForward);
        } else {
            rightPiston.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void pushHooks() {
        pushHooks(RobotSide.LEFT);
        pushHooks(RobotSide.RIGHT);
    }

    public void pullHooks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftPiston.set(DoubleSolenoid.Value.kReverse);
        } else {
            rightPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void pullHooks() {
        pullHooks(RobotSide.LEFT);
        pullHooks(RobotSide.RIGHT);
    }

    public DoubleSolenoid.Value getSolenoidValue() {
        return leftPiston.get();
    }


}
