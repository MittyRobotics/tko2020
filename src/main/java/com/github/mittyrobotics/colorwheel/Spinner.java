package com.github.mittyrobotics.colorwheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.github.mittyrobotics.colorwheel.Constants.*;


public class Spinner extends SubsystemBase {
    //talon for spinner
    private WPI_TalonSRX talon1;

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
        talon1 = new WPI_TalonSRX(TALON_DEVICE_NUMBER);

        //sets color match
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
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

    public String getColor() {
        //gets current rgb
        Color detectedColor = m_colorSensor.getColor();

        //matches rgb to color targets
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

            if (match.color == kBlueTarget) {
                colorString = "Blue";
            } else if (match.color == kRedTarget) {
                colorString = "Red";
            } else if (match.color == kGreenTarget) {
                colorString = "Green";
            } else if (match.color == kYellowTarget) {
                colorString = "Yellow";
            } else {
                colorString = " ";
            }
        //returns string for color
        return colorString;
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

    public char getGameMessage() {
        //return target color
        String s = DriverStation.getInstance().getGameSpecificMessage();
        if(s.length()>0){
            return s.charAt(0);
        } else {
            return ' ';
        }
    }

    public boolean matching() {
        //return target color equals current color
        return getGameMessage() == getColor().charAt(0);
    }
}