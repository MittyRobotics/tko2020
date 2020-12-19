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

package com.github.mittyrobotics.colorwheel.commands;

import com.github.mittyrobotics.colorwheel.SpinnerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Command to get the RGB Values detected by the {@link com.revrobotics.ColorSensorV3}
 *
 * Used to determine RGB Values for the {@link com.revrobotics.ColorMatch}
 */
public class ColorScannerCommand extends CommandBase {
    /**
     * Counter that keeps track of the # of readings
     */
    private int count;

    /**
     * The RGB values
     */
    private double red, green, blue;

    /**
     * The number of cycles to find the RGB values ofr
     */
    private final int cycles;

    /**
     * Calls the constructor of {@link CommandBase}
     *
     * Requires the {@link SpinnerSubsystem}
     *
     * Cancels the command if cycles <= 0
     *
     * @param cycles number of cycles to check for rgb values (must be > 0)
     */
    public ColorScannerCommand(int cycles) {
        super();
        if(cycles <= 0){
            cancel();
        }
        this.cycles = cycles;
        addRequirements(SpinnerSubsystem.getInstance());
    }

    /**
     * Sets the counter and all the rgb values to 0
     */
    @Override
    public void initialize() {
        count = 0;
        red = 0;
        green = 0;
        blue = 0;
    }

    /**
     * Updates the counter and each of the RBG values every cycle
     */
    @Override
    public void execute() {
        double[] colors = SpinnerSubsystem.getInstance().getRGB();

        red += colors[0];
        green += colors[1];
        blue += colors[2];

        count++;
    }

    /**
     * Calculates the final RGB values and displays them
     */
    @Override
    public void end(boolean interrupted) {
        if(interrupted){
            System.out.println("Command was interrupted");
        } else {
            System.out.println("Red: " + red / count);
            System.out.println("Green: " + green / count);
            System.out.println("Blue: " + blue / count);
        }
    }

    /**
     * Returns if the command should end
     * @return the number of cycles have been completed
     */
    @Override
    public boolean isFinished() {
        return count >= cycles;
    }
}

