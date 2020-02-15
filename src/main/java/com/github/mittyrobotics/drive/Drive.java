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

import com.github.mittyrobotics.controls.TKODifferentialDrive;
import com.github.mittyrobotics.util.OI;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase {
    CANSparkMax left;
    CANSparkMax right;
    TKODifferentialDrive differentialDrive;

    public Drive() {
        super();
        addRequirements(DriveTrainSparks.getInstance());
    }

    @Override
    public void initialize() {
        left = DriveTrainSparks.getInstance().leftSpark1;
        right = DriveTrainSparks.getInstance().rightSpark1;

        differentialDrive = new TKODifferentialDrive(left, right);
    }


    @Override
    public void execute() {
        differentialDrive.joystickCarSteering(OI.getInstance().getXboxWheel().getX() / 3,
                OI.getInstance().getJoystick1().getY() / 3, OI.getInstance().getJoystick1().getTrigger());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
