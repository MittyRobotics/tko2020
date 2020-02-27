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

import com.github.mittyrobotics.subsystems.ColorPistonSubsystem;
import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import com.github.mittyrobotics.subsystems.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.github.mittyrobotics.constants.ColorWheelConstants.REVS;

public class SpinRevsCommand extends CommandBase {

    boolean done;
    private double initPos;

    public SpinRevsCommand() {
        super();
        addRequirements(SpinnerSubsystem.getInstance(), DriveTrainSubsystem.getInstance(),
                ColorPistonSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        //sets motor to fast velocity
        DriveTrainSubsystem.getInstance().tankDrive(0.1, 0.1, 0, 1);
        System.out.println("Starting");
//        Spinner.getInstance().zeroEncoder();
        done = false;
        initPos = SpinnerSubsystem.getInstance().getRevolutions();

    }

    @Override
    public void execute() {
        //System.out.println(Spinner.getInstance().getEncoder());
        SpinnerSubsystem.getInstance().setMotorFast();

        if (SpinnerSubsystem.getInstance().getRevolutions() - initPos > REVS) {
            done = true;
            SpinnerSubsystem.getInstance().setMotorOff();
        }
    }

    @Override
    public void end(boolean interrupted) {
        //turns off motor, updates status
        SpinnerSubsystem.getInstance().setMotorOff();
        DriveTrainSubsystem.getInstance().tankDrive(0, 0);
        ColorPistonSubsystem.getInstance().down();
        System.out.println("END");
        //OI.getInstance().passedStage2();
    }

    @Override
    public boolean isFinished() {
        return done && !SpinnerSubsystem.getInstance().isSpinnerMoving();
    }
}