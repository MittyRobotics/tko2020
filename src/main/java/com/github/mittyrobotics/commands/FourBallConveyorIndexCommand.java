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
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FourBallConveyorIndexCommand extends CommandBase {

    private double distance, initialPosition, currentPosition;
    private boolean isDone;

    public FourBallConveyorIndexCommand(double distance) {
        super();
        this.distance = distance;
        addRequirements(ConveyorSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        System.out.println("Hit command");
        //ConveyorSubsystem.getInstance().moveConveyor(distance);
        initialPosition = ConveyorSubsystem.getInstance().getPosition();
        currentPosition = initialPosition;
        isDone = false;
        ConveyorSubsystem.getInstance().setMotor(1);
    }

    @Override
    public void execute() {
//        if (Conveyor.getInstance().hasBallCountChanged()) {
//            if (Conveyor.getInstance().getTotalBallCount() < 5) {
//                Conveyor.getInstance().moveConveyor(distance);
//            } else {
//                Conveyor.getInstance().setConveyorSpeed(0);
//            }
//        }

        if ((currentPosition - initialPosition) >
                (distance * ConveyorConstants.TICKS_PER_BALL_INCH)) {
            isDone = true;
        }
        currentPosition = ConveyorSubsystem.getInstance().getPosition();


//        if (ConveyorSwitches.getInstance().getSwitch1()) {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(Constants.CONVEYOR_SPEED);
//        } else {
//            ConveyorSubsystem.getInstance().setConveyorSpeed(0);
//            isDone = true;
//        }
        System.out.println(currentPosition);
        System.out.println(initialPosition);
        System.out.println((currentPosition - initialPosition) / ConveyorConstants.TICKS_PER_BALL_INCH);
    }


    @Override
    public void end(boolean interrupted) {
        ConveyorSubsystem.getInstance().stopMotor();
//        Buffer.getInstance().manualBufferSpeed(0);
        System.out.println("end");
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

}
