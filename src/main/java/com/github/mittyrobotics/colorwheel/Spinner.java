package com.github.mittyrobotics.colorwheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;


public class Spinner extends SubsystemBase {
    //talon for spinner
    private WPI_TalonSRX spinnerTalon;

    //spinner singleton
    private static Spinner instance;

    //initialize color sensor
    private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private final ColorMatch colorMatcher = new ColorMatch();

    //calibrated
    private final Color blue = ColorMatch.makeColor(Constants.BLUE_R, Constants.BLUE_G, Constants.BLUE_B);
    private final Color green = ColorMatch.makeColor(Constants.GREEN_R, Constants.GREEN_G, Constants.GREEN_B);
    private final Color red = ColorMatch.makeColor(Constants.RED_R, Constants.RED_G, Constants.RED_B);
    private final Color yellow = ColorMatch.makeColor(Constants.YELLOW_R, Constants.YELLOW_G, Constants.YELLOW_B);
    private final Color nullTarget = ColorMatch.makeColor(Constants.NULL_R, Constants.NULL_G, Constants.NULL_B);
    private final Color alsoNullTarget = ColorMatch.makeColor(Constants.ALSO_NULL_R, Constants.ALSO_NULL_G, Constants.ALSO_NULL_B);


    private HashMap<WheelColor, WheelColor> map;

    public static Spinner getInstance() {
        if (instance == null) {
            instance = new Spinner();
        }
        return instance;
    }


    private Spinner() {
        super();
        setName("Spinner");
    }

    @Override
    public void periodic() {

    }

    public void initHardware() {
        //initialize talon
        spinnerTalon = new WPI_TalonSRX(Constants.SPINNER_TALON_ID);
        spinnerTalon.setInverted(Constants.SPINNER_TALON_INVERSION);
        spinnerTalon.setSensorPhase(Constants.SPINNER_ENCODER_INVERSION);
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
        spinnerTalon.setNeutralMode(NeutralMode.Brake);

    }

    public void setMotorFast() {
        //sets motor to fast velocity
        setMotorPID(Constants.FAST_VELOCITY);

    }

    private void setMotorPID(double rpm){ //TODO cleanup setpoint conversion
        double setpoint = (rpm * (4 * Math.PI)) * Constants.TICKS_PER_INCH / 600.0; //Ticks per 100ms
        PIDController controller = new PIDController(Constants.SPINNER_P, Constants.SPINNER_I, Constants.SPINNER_D);
        controller.setSetpoint(setpoint);
        spinnerTalon.set(ControlMode.PercentOutput, Constants.SPINNER_FF * setpoint
                + controller.calculate(spinnerTalon.getSelectedSensorVelocity())
        );
    }
    public void setMotorSlow(boolean isReversed) {
        //sets motor to slow velocity
        if(isReversed){
            setMotorPID(-Constants.SLOW_VELOCITY);
        } else {
            setMotorPID(Constants.SLOW_VELOCITY);
        }
    }

    public void setMotorOff() {
        //turn off motor
        spinnerTalon.set(ControlMode.PercentOutput, 0);
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
        if(s.length()>0){
            switch(s.toLowerCase().charAt(0)){
                case('b'):
                    return WheelColor.Blue;
                case('r'):
                    return WheelColor.Red;
                case('g'):
                    return WheelColor.Green;
                case('y'):
                    return WheelColor.Yellow;
            }
        }
        return WheelColor.None;
    }

    public double getRevolutions() {
        return spinnerTalon.getSelectedSensorPosition() / (32*Math.PI*Constants.TICKS_PER_INCH);
    }

    public void zeroEncoder() {
        spinnerTalon.setSelectedSensorPosition(0);
    }
}