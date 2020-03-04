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

package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.ColorWheelConstants;
import com.github.mittyrobotics.constants.WheelColor;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;


public class SpinnerSubsystem extends SubsystemBase implements IMotorSubsystem {
    //spinner singleton
    private static SpinnerSubsystem instance;
    //initialize color sensor
    private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private final ColorMatch colorMatcher = new ColorMatch();
    //calibrated
    private final Color blue =
            ColorMatch.makeColor(ColorWheelConstants.BLUE_R, ColorWheelConstants.BLUE_G, ColorWheelConstants.BLUE_B);
    private final Color green =
            ColorMatch.makeColor(ColorWheelConstants.GREEN_R, ColorWheelConstants.GREEN_G, ColorWheelConstants.GREEN_B);
    private final Color red =
            ColorMatch.makeColor(ColorWheelConstants.RED_R, ColorWheelConstants.RED_G, ColorWheelConstants.RED_B);
    private final Color yellow = ColorMatch
            .makeColor(ColorWheelConstants.YELLOW_R, ColorWheelConstants.YELLOW_G, ColorWheelConstants.YELLOW_B);
    private final Color nullTarget =
            ColorMatch.makeColor(ColorWheelConstants.NULL_R, ColorWheelConstants.NULL_G, ColorWheelConstants.NULL_B);
    private final Color alsoNullTarget =
            ColorMatch.makeColor(ColorWheelConstants.ALSO_NULL_R, ColorWheelConstants.ALSO_NULL_G,
                    ColorWheelConstants.ALSO_NULL_B);
    //talon for spinner
    private WPI_TalonSRX spinnerTalon;
    private HashMap<WheelColor, WheelColor> map;

    private SpinnerSubsystem() {
        super();
        setName("Spinner");
    }

    public static SpinnerSubsystem getInstance() {
        if (instance == null) {
            instance = new SpinnerSubsystem();
        }
        return instance;
    }

    @Override
    public void periodic() {

    }

    @Override
    public void initHardware() {
        //initialize talon
        spinnerTalon = new WPI_TalonSRX(ColorWheelConstants.SPINNER_TALON_ID);
        spinnerTalon.configFactoryDefault();
        spinnerTalon.setInverted(ColorWheelConstants.SPINNER_TALON_INVERSION);
        spinnerTalon.setSensorPhase(ColorWheelConstants.SPINNER_ENCODER_INVERSION);
        spinnerTalon.setNeutralMode(NeutralMode.Brake);
        colorMatcher.addColorMatch(blue);
        colorMatcher.addColorMatch(green);
        colorMatcher.addColorMatch(red);
        colorMatcher.addColorMatch(yellow);
        colorMatcher.addColorMatch(nullTarget);
        colorMatcher.addColorMatch(alsoNullTarget);
        map = new HashMap<>();
        map.put(WheelColor.Blue, WheelColor.Red);
        map.put(WheelColor.Red, WheelColor.Blue);
        map.put(WheelColor.Green, WheelColor.Yellow);
        map.put(WheelColor.Yellow, WheelColor.Green);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putString("Color", getColor().toString());
        SmartDashboard.putString("Color Target",
                getGameMessage() == WheelColor.None ? "Spin 3 - 5 times" : getGameMessage().toString());
        SmartDashboard.putNumber("Spinner RPM", getVelocity());
    }

    @Override
    public void resetEncoder() {
        spinnerTalon.setSelectedSensorPosition(0);
    }

    public void setMotorFast() {
        setMotorPID(ColorWheelConstants.FAST_VELOCITY);
    }

    private void setMotorPID(double rpm) {
        double setpoint = (rpm * (4 * Math.PI)) * ColorWheelConstants.TICKS_PER_INCH / 600.0; //Ticks per 100ms
        PIDController controller = new PIDController(ColorWheelConstants.SPINNER_P, ColorWheelConstants.SPINNER_I,
                ColorWheelConstants.SPINNER_D);
        controller.setSetpoint(setpoint);
        setMotor(ColorWheelConstants.SPINNER_FF * setpoint
                + controller.calculate(spinnerTalon.getSelectedSensorVelocity())
        );
    }

    public void setMotorSlow(boolean isReversed) {
        //sets motor to slow velocity
        if (isReversed) {
            setMotorPID(-ColorWheelConstants.SLOW_VELOCITY);
        } else {
            setMotorPID(ColorWheelConstants.SLOW_VELOCITY);
        }
    }

    public WheelColor getColor() {
        //gets current rgb
        Color detectedColor = colorSensor.getColor();

        //matches rgb to color targets
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        if (match.color == blue) {
            return WheelColor.Blue;
        } else if (match.color == red) {
            return WheelColor.Red;
        } else if (match.color == green) {
            return WheelColor.Green;
        } else if (match.color == yellow) {
            return WheelColor.Yellow;
        } else {
            return WheelColor.None;
        }
    }

    public double[] getRGB() {
        //returns array of rgb values
        Color detectedColor = colorSensor.getColor();
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
                    return map.get(WheelColor.Blue);
                case ('r'):
                    return map.get(WheelColor.Red);
                case ('g'):
                    return map.get(WheelColor.Green);
                case ('y'):
                    return map.get(WheelColor.Yellow);
            }
        }
        return WheelColor.None;
    }

    public double getPosition() {
        return spinnerTalon.getSelectedSensorPosition() / (32 * Math.PI * ColorWheelConstants.TICKS_PER_INCH);
    }

    public void setMotor(double percent) {
        if (ColorPistonSubsystem.getInstance().isPistonExtended()) {
            overrideSetMotor(percent);
        } else {
            overrideSetMotor(0);
        }
    }

    @Override
    public void overrideSetMotor(double percent) {
        spinnerTalon.set(percent);
    }

    @Override
    public double getVelocity() {
        return spinnerTalon.getSelectedSensorVelocity() / (32 * Math.PI * ColorWheelConstants.TICKS_PER_INCH) * 600;
    }
}