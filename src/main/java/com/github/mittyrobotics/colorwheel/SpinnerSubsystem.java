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

package com.github.mittyrobotics.colorwheel;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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

/**
 * Spinner subsystem to spin the control panel
 */
public class SpinnerSubsystem extends SubsystemBase implements IMotorSubsystem {
    /**
     * {@link SpinnerSubsystem} instance
     */
    private static SpinnerSubsystem instance;

    /**
     * Spinner's {@link ColorSensorV3}
     */
    private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    /**
     * Spinner's {@link ColorMatch} for sensor calibration
     */
    private final ColorMatch colorMatcher = new ColorMatch();

    /**
     * Spinner's {@link WPI_TalonSRX}
     */
    private WPI_TalonSRX spinnerTalon;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Spinner"
     */
    private SpinnerSubsystem() {
        super();
        setName("Spinner");
    }

    /**
     * Returns the {@link SpinnerSubsystem} instance.
     *
     * @return the {@link SpinnerSubsystem} instance.
     */
    public static SpinnerSubsystem getInstance() {
        if (instance == null) {
            instance = new SpinnerSubsystem();
        }
        return instance;
    }

    /**
     * Initializes the spinner's hardware
     */
    @Override
    public void initHardware() {
        spinnerTalon = new WPI_TalonSRX(ColorWheelConstants.SPINNER_TALON_ID);
        spinnerTalon.configFactoryDefault();
        spinnerTalon.setInverted(ColorWheelConstants.SPINNER_TALON_INVERSION);
        spinnerTalon.setSensorPhase(ColorWheelConstants.SPINNER_ENCODER_INVERSION);
        spinnerTalon.setNeutralMode(ColorWheelConstants.SPINNER_NEUTRAL_MODE);

        colorMatcher.addColorMatch(ColorWheelConstants.BLUE_COLOR);
        colorMatcher.addColorMatch(ColorWheelConstants.GREEN_COLOR);
        colorMatcher.addColorMatch(ColorWheelConstants.RED_COLOR);
        colorMatcher.addColorMatch(ColorWheelConstants.YELLOW_COLOR);
        colorMatcher.addColorMatch(ColorWheelConstants.NULL_COLOR);
        colorMatcher.addColorMatch(ColorWheelConstants.NULL_COLOR_2);
    }

    /**
     * Updates the spinner's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putString("Color", getColor().toString());
        SmartDashboard.putString("Color Target",
                getGameMessage() == WheelColor.None ? "Spin 3 - 5 times" : getGameMessage().toString());
        SmartDashboard.putNumber("Spinner RPM", getVelocity());
    }

    /**
     * Resets the spinner's encoder to 0
     */
    @Override
    public void resetEncoder() {
        spinnerTalon.setSelectedSensorPosition(0);
    }

    /**
     * Sets the spinner to move at fast velocity
     *
     * Used for spinning the wheel 3-5 times
     */
    public void setMotorFast() {
        setMotorPID(ColorWheelConstants.FAST_VELOCITY);
    }

    /**
     * Sets the motor to spin at a certian RPM using Velocity PID
     *
     * @param rpm target rpm
     */
    private void setMotorPID(double rpm) {
        double setpoint = (rpm * (4 * Math.PI)) * ColorWheelConstants.TICKS_PER_INCH / 600.0; //Ticks per 100ms
        PIDController controller = new PIDController(ColorWheelConstants.SPINNER_P, ColorWheelConstants.SPINNER_I,
                ColorWheelConstants.SPINNER_D);
        controller.setSetpoint(setpoint);
        setMotor(ColorWheelConstants.SPINNER_FF * setpoint
                + controller.calculate(spinnerTalon.getSelectedSensorVelocity())
        );
    }

    /**
     * Sets the motor to spin at slow velocity
     *
     * Used for spinning the wheel to a certain color
     */
    public void setMotorSlow(WheelColor color) {
        WheelColor cur = getColor();
        //sets motor to slow velocity
        if ((cur == WheelColor.Green && color == WheelColor.Blue) ||
                (cur == WheelColor.Blue && color == WheelColor.Yellow) ||
                (cur == WheelColor.Yellow && color == WheelColor.Red) ||
                (cur == WheelColor.Red && color == WheelColor.Green)) {
            setMotorPID(-ColorWheelConstants.SLOW_VELOCITY);
        } else {
            setMotorPID(ColorWheelConstants.SLOW_VELOCITY);
        }
    }

    /**
     * Returns the current {@link WheelColor}
     *
     * @return the current {@link WheelColor}
     */
    public WheelColor getColor() {
        //gets current rgb
        Color detectedColor = colorSensor.getColor();

        //matches rgb to color targets
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        if (match.color == ColorWheelConstants.BLUE_COLOR) {
            return WheelColor.Blue;
        } else if (match.color == ColorWheelConstants.RED_COLOR) {
            return WheelColor.Red;
        } else if (match.color == ColorWheelConstants.GREEN_COLOR) {
            return WheelColor.Green;
        } else if (match.color == ColorWheelConstants.YELLOW_COLOR) {
            return WheelColor.Yellow;
        } else {
            return WheelColor.None;
        }
    }

    /**
     * Returns the rgb values of the {@link ColorSensorV3}
     *
     * @return the rgb values of the {@link ColorSensorV3}
     */
    public double[] getRGB() {
        //returns array of rgb values
        Color detectedColor = colorSensor.getColor();
        double[] colors = new double[3];
        colors[0] = detectedColor.red;
        colors[1] = detectedColor.green;
        colors[2] = detectedColor.blue;
        return colors;
    }

    /**
     * Returns the color to spin to in the form of a {@link WheelColor}
     *
     * @return the color to spin to in the form of a {@link WheelColor}
     */
    public WheelColor getGameMessage() {
        HashMap <WheelColor, WheelColor> map = new HashMap<>();
        map.put(WheelColor.Blue, WheelColor.Red);
        map.put(WheelColor.Red, WheelColor.Blue);
        map.put(WheelColor.Green, WheelColor.Yellow);
        map.put(WheelColor.Yellow, WheelColor.Green);
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

    /**
     * Returns the current position of the spinner in terms of revolutions
     *
     * @return the current position of the spinner in terms of revolutions
     */
    @Override
    public double getPosition() {
        return spinnerTalon.getSelectedSensorPosition() / (32 * Math.PI * ColorWheelConstants.TICKS_PER_INCH);
    }

    /**
     * Runs the motor only if the piston is currently extended
     *
     * @param percent the percentage to run the motor at
     */
    @Override
    public void setMotor(double percent) {
        setMotor(percent, 0);
    }

    /**
     * Runs the motor only if the piston is currently extended
     *
     * @param percent the percentage to run the motor at
     *
     * @param deadzone the deadzone to not run the motor at (will be set to 0 if percent < deadzone)
     */
    public void setMotor(double percent, double deadzone) {
        if (ColorPistonSubsystem.getInstance().isPistonExtended() && Math.abs(percent) >= deadzone) {
            overrideSetMotor(percent);
        } else {
            overrideSetMotor(0);
        }
    }

    /**
     * Runs the motor regardless of piston state
     *
     * @param percent the percentage to run the motor at
     */
    @Override
    public void overrideSetMotor(double percent) {
        spinnerTalon.set(percent);
    }

    /**
     * Returns the current velocity of the spinner in terms of revolutions / second
     *
     * @return the current velocity of the spinner in terms of revolutions / second
     */
    @Override
    public double getVelocity() {
        return spinnerTalon.getSelectedSensorVelocity() / (32 * Math.PI * ColorWheelConstants.TICKS_PER_INCH) * 600;
    }
}