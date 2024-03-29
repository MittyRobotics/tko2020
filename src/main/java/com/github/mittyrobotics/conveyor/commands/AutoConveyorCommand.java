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
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoConveyorCommand extends CommandBase {

    /**
     * Current and previous state of the state machine
     */
    private State state;

    /**
     * Current target position for the conveyor
     */
    private double counter;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link ConveyorSubsystem}
     */
    public AutoConveyorCommand() {
        this(false);
    }
    private boolean auton = false;

    public AutoConveyorCommand(boolean auton) {
        this.auton = true;
        addRequirements(ConveyorSubsystem.getInstance());
    }

    /**
     * Initializes the starting states and setpoints
     */
    @Override
    public void initialize() {
        state = State.STOPPING;
        counter = 0;
    }

    /**
     * Runs state machine, setting motor speeds and updating ball counts depending on sensor values and assigned states
     */
    @Override
    public void execute() {
        if (IntakePistonSubsystem.getInstance().isPistonExtended()) {
            if(!ConveyorSubsystem.getInstance().isShooterBallDetected()) {
                if (ConveyorSubsystem.getInstance().isBallDetected()) {
                    state = State.SENSING;
                }

                if (state == State.SENSING) {

                    ConveyorSubsystem.getInstance().overrideSetMotor(ConveyorConstants.INDEX_SPEED);
                    if (!ConveyorSubsystem.getInstance().isBallDetected()) {
                        state = State.INDEXING;
                        counter = (ConveyorConstants.BUFFER_TIME) / (0.02);
                    }
                } else if (state == State.INDEXING) {
                    ConveyorSubsystem.getInstance().overrideSetMotor(ConveyorConstants.INDEX_SPEED);
                    counter--;
                    if (counter <= 0) {
                        state = State.STOPPING;
                    }
                } else {
                    ConveyorSubsystem.getInstance().stopMotor();
                }
            } else {
                ConveyorSubsystem.getInstance().stopMotor();
            }
        } else {
            ConveyorSubsystem.getInstance().stopMotor();
        }
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
