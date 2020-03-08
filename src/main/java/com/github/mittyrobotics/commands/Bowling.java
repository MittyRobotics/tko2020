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

package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.constants.ConveyorConstants;
import com.github.mittyrobotics.subsystems.ConveyorSubsystem;
import com.github.mittyrobotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Bowling extends CommandBase {
    private State state;
    private double setpoint;
    private State prevState;
    private double time;

    public Bowling() {
        addRequirements(ConveyorSubsystem.getInstance(),
                ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        prevState = State.STOPPING;
        state = State.STOPPING;
        setpoint = ConveyorSubsystem.getInstance().getPosition();
        time = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        if (ConveyorSubsystem.getInstance().getSwitch()) {
            state = State.SENSING;
            if (state == State.SENSING && prevState != State.SENSING) {
                ConveyorSubsystem.getInstance().updateBallCount(1);
            }
        } else {
            if (ConveyorSubsystem.getInstance().getVelocity() < 0 && prevState == State.SENSING) {
                ConveyorSubsystem.getInstance().updateBallCount(-1);
            }
        }
        if (state == State.SENSING) {
            ConveyorSubsystem.getInstance().setIndexSpeed();
            if (!ConveyorSubsystem.getInstance().getSwitch()) {
                state = State.INDEXING;
                setpoint += 9 * ConveyorConstants.TICKS_PER_BALL_INCH;
//                ConveyorSubsystem.getInstance().updateBallCount(1);
            }
        } else if (state == State.INDEXING) {
            ConveyorSubsystem.getInstance().setIndexSpeed();
            if (setpoint < ConveyorSubsystem.getInstance().getPosition()) {
                state = State.STOPPING;
            }
        } else {
            ConveyorSubsystem.getInstance().stopMotor();
        }
        if (ConveyorSubsystem.getInstance().getTotalBallCount() > 4) {
            time = Timer.getFPGATimestamp();
        }
        if (Timer.getFPGATimestamp() - time < 5) {
            ConveyorSubsystem.getInstance().setIndexSpeed();
            ShooterSubsystem.getInstance().setShooterRpm(3000);
        } else {
            ShooterSubsystem.getInstance().stopMotor();
        }
        prevState = state;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private enum State {
        SENSING, INDEXING, STOPPING
    }
}
