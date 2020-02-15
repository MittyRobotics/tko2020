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

package com.github.mittyrobotics.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;


public class ColorTesting extends CommandBase {
    //counter
    private int count;

    //rgb values
    private double red;
    private double green;
    private double blue;

    public ColorTesting() {
        super();
        addRequirements(Spinner.getInstance());
    }

    @Override
    public void initialize() {
        //reset
        count = 0;
        red = 0;
        green = 0;
        blue = 0;
    }

    @Override
    public void execute() {
        //gets current rgb values
        double[] colors = Spinner.getInstance().getRGB();
        //updates variable


        red += colors[0];
        green += colors[1];
        blue += colors[2];

        count++;
    }

    @Override
    public void end(boolean interrupted) {
        //print current recognized color
        System.out.println(Spinner.getInstance().getColor());

        //print rgb averages
        System.out.println("Red: " + red / 20);
        System.out.println("Green: " + green / 20);
        System.out.println("Blue: " + blue / 20);
        System.out.println();
    }

    @Override
    public boolean isFinished() {
        //20 cycles
        return count > 19;
    }
}

