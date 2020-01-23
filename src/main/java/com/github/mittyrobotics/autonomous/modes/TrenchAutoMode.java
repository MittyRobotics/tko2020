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

package com.github.mittyrobotics.autonomous.modes;

import com.github.mittyrobotics.Gyro;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.drive.DriveTrainTalon;
import com.github.mittyrobotics.path.following.util.Odometry;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class TrenchAutoMode extends SequentialCommandGroup {
    public TrenchAutoMode(Transform robotStartTransform) {
        Odometry.getInstance().calibrateRobotTransform(robotStartTransform,
                DriveTrainTalon.getInstance().getLeftEncoder(), DriveTrainTalon.getInstance().getRightEncoder(),
                Gyro.getInstance().getAngle());
        addCommands(
                //TODO: Set shooter setpoint command to general speed around 20 feet
                //TODO: Drive to trench starting position
                //TODO: Vision AimBot (wait until turret positioned and sped up)
                //TODO: Shoot 3 balls into high goal (shoot, wait until sped up, repeat)
                //TODO: Set intake to intake
                //TODO: Drive to position in trench zone while intaking balls
                //TODO: Vision AimBot (wait until turret positioned and sped up)
                //TODO: Shoot 3 balls into high goal (shoot, wait until sped up, repeat)
        );
    }
}