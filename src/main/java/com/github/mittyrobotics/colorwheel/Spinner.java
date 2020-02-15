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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.interfaces.ISubsystem;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;

import static com.github.mittyrobotics.colorwheel.Constants.*;


public class Spinner extends SubsystemBase implements ISubsystem {
    //spinner singleton
    private static Spinner instance;
    //I2C port for color sensor
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    //initialize color sensor
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    //calibrated
    private final Color kBlueTarget = ColorMatch.makeColor(BLUE_R, BLUE_G, BLUE_B);
    private final Color kGreenTarget = ColorMatch.makeColor(GREEN_R, GREEN_G, GREEN_B);
    private final Color kRedTarget = ColorMatch.makeColor(RED_R, RED_G, RED_B);
    private final Color kYellowTarget = ColorMatch.makeColor(YELLOW_R, YELLOW_G, YELLOW_B);
    //talon for spinner
    private WPI_TalonSRX talon1;
    private HashMap<WheelColor, WheelColor> map = new HashMap<>();

    private Spinner() {
        super();
        setName("Spinner");
    }

    public static Spinner getInstance() {
        if (instance == null) {
            instance = new Spinner();
        }
        return instance;
    }

    @Override
    public void periodic() {

    }

    @Override
    public void initHardware() {
        //initialize talon
        talon1 = new WPI_TalonSRX(TALON_DEVICE_NUMBER);

        //TODO setup encoder & PID
        //sets color match
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
        map.put(WheelColor.Blue, WheelColor.Red);
        map.put(WheelColor.Red, WheelColor.Blue);
        map.put(WheelColor.Green, WheelColor.Yellow);
        map.put(WheelColor.Yellow, WheelColor.Green);
    }

    @Override
    public void updateDashboard() {

    }

    public void setMotorFast() {
        //sets motor to fast velocity
        talon1.set(ControlMode.Velocity, FAST_VELOCITY);

    }

    public void setMotorSlow() {
        //sets motor to slow velocity
        talon1.set(ControlMode.Velocity, SLOW_VELOCITY);

    }

    public void setMotorOff() {
        //turn off motor
        talon1.set(ControlMode.PercentOutput, 0);
    }

    public WheelColor getColor() {
        //gets current rgb
        Color detectedColor = m_colorSensor.getColor();

        //matches rgb to color targets
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            return WheelColor.Blue;
        } else if (match.color == kRedTarget) {
            return WheelColor.Red;
        } else if (match.color == kGreenTarget) {
            return WheelColor.Green;
        } else if (match.color == kYellowTarget) {
            return WheelColor.Yellow;
        } else {
            return WheelColor.None;
        }
    }

    public double[] getRGB() {
        //returns array of rgb values
        Color detectedColor = m_colorSensor.getColor();
        double[] colors = new double[3];
        colors[0] = detectedColor.red;
        colors[1] = detectedColor.green;
        colors[2] = detectedColor.blue;
        return colors;
    }

    public WheelColor getGameMessage() {
        //return target color
        String s = DriverStation.getInstance().getGameSpecificMessage();
        if (s.length() > 0) {
            switch (s.toLowerCase().charAt(0)) {
                case ('b'):
                    return WheelColor.Blue;
                case ('r'):
                    return WheelColor.Red;
                case ('g'):
                    return WheelColor.Green;
                case ('y'):
                    return WheelColor.Yellow;
            }
        }
        return WheelColor.None;
    }

    public double getRevolutions() {
        return talon1.getSelectedSensorPosition() / (100 * TICKS_PER_INCH);
    }

    public void zeroEncoder() {
        talon1.setSelectedSensorPosition(0);
    }

    public boolean matching() {
        //return target color equals current color
        return getGameMessage() == map.get(getColor());
    }
}