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

package com.github.mittyrobotics.testing;

import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.conveyor.Conveyor;
import com.github.mittyrobotics.conveyor.MoveConveyorAddBall;
import com.github.mittyrobotics.conveyor.MoveConveyorRemoveBall;
import com.github.mittyrobotics.conveyor.UnloadConveyor;
import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;
import org.ejml.dense.row.CovarianceOps_DDRM;

public class ConveyorTesting extends TimedRobot {
    private double init;
    private boolean alreadyThere1, alreadyThere2, alreadyThere3;
    @Override
    public void robotInit() {
        Conveyor.getInstance().initHardware();
        Buffer.getInstance().initHardware();
        Conveyor.getInstance().resetEncoder();
        Buffer.getInstance().resetEncoder();
        alreadyThere1 = false;
        alreadyThere2 = false;
        alreadyThere3 = false;
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
//        CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(7.6));
        Conveyor.getInstance().resetBallCount();
    }

    @Override
    public void teleopInit() {
//        CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(4));
//        CommandScheduler.getInstance().schedule(new MoveConveyorRemoveBall(3, 4));
    }

    @Override
    public void testInit() {

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousPeriodic() {
        System.out.println(Conveyor.getInstance().getTotalBallCount());
        Conveyor.getInstance().periodic();
        Buffer.getInstance().manualBufferSpeed(-.5);
    }

    @Override
    public void teleopPeriodic() {
        double x = 0.7;
        if(OI.getInstance().getJoystick1().getY() > x && !alreadyThere1){
            CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(6.3));
            alreadyThere1 = true;
        }
        if(OI.getInstance().getJoystick1().getY() < -x && !alreadyThere2){
            CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(3.5));
            alreadyThere2 = true;
        }
        if(OI.getInstance().getJoystick1().getX() > x && !alreadyThere3){
            CommandScheduler.getInstance().schedule(new UnloadConveyor());
            alreadyThere3 = true;
        }
        if(OI.getInstance().getJoystick1().getY() < x){
            alreadyThere1 = false;
        }
        if(OI.getInstance().getJoystick1().getY() > -x){
            alreadyThere2 = false;
        }
        if(OI.getInstance().getJoystick1().getX() < x){
            alreadyThere3 = false;
        }
    }

    @Override
    public void testPeriodic() {
//        System.out.println(Buffer.getInstance().getBufferPosition() - init);
//        System.out.println(Buffer.getInstance().getBufferPosition());
//        Buffer.getInstance().manualBufferSpeed(0.2);
        Conveyor.getInstance().manualSetConveyorSpeed(OI.getInstance().getJoystick1().getY());
        Buffer.getInstance().manualBufferSpeed(OI.getInstance().getJoystick1().getX());
    }

    private void digitalInputControls() {

    }
}