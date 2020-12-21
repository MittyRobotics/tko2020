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

package com.github.mittyrobotics.conveyor.commands;

import com.github.mittyrobotics.conveyor.ConveyorConstants;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Indexes balls entered into the {@link ConveyorSubsystem}
 */
public class AutoConveyorIndexCommand extends CommandBase {

    /**
     * Current and previous state of the state machine
     */
    private State state, prevState;

    /**
     * Current target position for the conveyor
     */
    private double setpoint;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link ConveyorSubsystem}
     */
    public AutoConveyorIndexCommand() {
        addRequirements(ConveyorSubsystem.getInstance());
    }

    /**
     * Initializes the starting states and setpoints
     */
    @Override
    public void initialize() {
        prevState = State.STOPPING;
        state = State.STOPPING;
        setpoint = ConveyorSubsystem.getInstance().getPosition();
    }

    /**
     * Runs state machine, setting motor speeds and updating ball counts depending on sensor values and assigned states
     */
    @Override
    public void execute() {
        if (ConveyorSubsystem.getInstance().isBallDetected()) {
            state = State.SENSING;
            if(prevState != State.SENSING){
                ConveyorSubsystem.getInstance().updateBallCount(1);
            }
        } else {
            if (ConveyorSubsystem.getInstance().getVelocity() < 0 && prevState == State.SENSING) {
                ConveyorSubsystem.getInstance().updateBallCount(-1);
            }
        }
        if(state == State.SENSING){
            ConveyorSubsystem.getInstance().indexBall();
            if(!ConveyorSubsystem.getInstance().isBallDetected()){
                state = State.INDEXING;
                setpoint += ConveyorConstants.INDEXING_SETPOINT;
            }
        } else if(state == State.INDEXING){
            ConveyorSubsystem.getInstance().indexBall();
            if(setpoint < ConveyorSubsystem.getInstance().getPosition()){
                state = State.STOPPING;
            }
        } else {
            ConveyorSubsystem.getInstance().stopMotor();
        }
        prevState = state;
    }

    /**
     * Returns if the command should end
     *
     * @return false because this is a default command
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * State enum used for the state machine
     *
     * Sensing - Activates when ball is detected, transitions to indexing when ball is no longer detected
     *
     * Indexing - Moves ball an extra distance after the sensor stops detecting it, then transitions to Stopping
     *
     * Stopping - the State machine ends, will trigger again if a ball is detected
     */
    private enum State {
        SENSING, INDEXING, STOPPING
    }
}
