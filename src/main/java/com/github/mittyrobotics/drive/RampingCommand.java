/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
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

package com.github.mittyrobotics.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RampingCommand extends CommandBase {
    private double pos;

    public RampingCommand(double pos) {
        addRequirements(DriveTrainTalon.getInstance());
        this.pos = pos;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double tempPosLeft;
        double tempPosRight;
        final double RAMP_RATE = 15;

        tempPosLeft = DriveTrainTalon.getInstance().getLeftEncoder() + RAMP_RATE;
        tempPosRight = DriveTrainTalon.getInstance().getRightEncoder() + RAMP_RATE;
        if (RAMP_RATE > (pos - DriveTrainTalon.getInstance().getLeftEncoder())) {
            tempPosLeft = pos;
        }
        if (RAMP_RATE > (pos - DriveTrainTalon.getInstance().getRightEncoder())) {
            tempPosRight = pos;
        }
//        if(DriveTrainTalon.getInstance().getLeftTalon().getClosedLoopTarget() != pos * Constants.TICKS_PER_INCH && DriveTrainTalon.getInstance().getRightTalon().getClosedLoopTarget() != pos * Constants.TICKS_PER_INCH){
        DriveTrainTalon.getInstance().movePos(tempPosLeft, tempPosRight);
//        }
        System.out.println("target: " +
                DriveTrainTalon.getInstance().getLeftTalon().getClosedLoopTarget() / Constants.TICKS_PER_INCH);


    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("end");
        DriveTrainTalon.getInstance().tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return ((Math.abs(pos - DriveTrainTalon.getInstance().getLeftEncoder()) < .5) &&
                (Math.abs(pos - DriveTrainTalon.getInstance().getLeftEncoder()) < .5));
//        return false;
    }
}