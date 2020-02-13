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

package com.github.mittyrobotics.autonomous.modes;

import com.github.mittyrobotics.autonomous.AutonDriver;
import com.github.mittyrobotics.autonomous.commands.SetAutonDriverGoalCommand;
import com.github.mittyrobotics.autonomous.commands.TurretAimbotCommand;
import com.github.mittyrobotics.autonomous.commands.WaitUntilShooterSpeedCommand;
import com.github.mittyrobotics.autonomous.commands.WaitUntilVisionLockedCommand;
import com.github.mittyrobotics.autonomous.constants.AutonConstants;
import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TenBallAuton extends SequentialCommandGroup {
    public TenBallAuton(){
        AutonDriver.getInstance().initNewPathFollower(null);
        addCommands(
                new TurretAimbotCommand(),
                new SetAutonDriverGoalCommand(AutonCoordinates.A_TRENCH_FRONT_CENTER),
                new WaitUntilShooterSpeedCommand(50),
                new WaitUntilVisionLockedCommand(2),
                //TODO: Shoot 5 balls
                //TODO: Run intake in
                new SetAutonDriverGoalCommand(AutonCoordinates.A_TRENCH_BACK_CENTER),
                //TODO: Stop intake
                new SetAutonDriverGoalCommand(AutonCoordinates.A_TRENCH_FRONT_CENTER),
                new WaitUntilShooterSpeedCommand(50),
                new WaitUntilVisionLockedCommand(2)
                //TODO: Shoot 5 balls
        );
    }
}
