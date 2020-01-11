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
    private WPI_TalonSRX talon1;
    private static Spinner instance;
    String prevColorString = "";
    /**
     * Change the I2C port below to match the connection of your color sensor
     */
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    /**
     * A Rev Color Sensor V3 object is constructed with an I2C port as a
     * parameter. The device will be automatically initialized with default
     * parameters.
     */
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    /**
     * A Rev Color Match object is used to register and detect known colors. This can
     * be calibrated ahead of time or during operation.
     *
     * This object uses a simple euclidian distance to estimate the closest match
     * with given confidence range.
     */
    private final ColorMatch m_colorMatcher = new ColorMatch();

    /**
     * Note: Any example colors should be calibrated as the user needs, these
     * are here as a basic example.
     */
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

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

    //Function that runs continuously regardless of any commands being run
    @Override
    public void periodic() {

    }

    //Function to initialize the hardware
    public void initHardware() {
        talon1 = new WPI_TalonSRX(TALON_DEVICE_NUMBER);


        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    public void setMotorFast() {
        talon1.set(ControlMode.Velocity, FAST_VELOCITY);

    }

    public void setMotorSlow() {
        talon1.set(ControlMode.Velocity, SLOW_VELOCITY);

    }

    public void setMotorOff() {
        talon1.set(ControlMode.PercentOutput, 0);
    }

    public String getColor() {
        Color detectedColor = m_colorSensor.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
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
        return colorString;
    }

    public char getGameMessage() {
        String s = DriverStation.getInstance().getGameSpecificMessage();
        if(s.length()>0){
            return s.charAt(0);
        } else {
            return ' ';
        }
    }

    public boolean matching() {
        return getGameMessage() == getColor().charAt(0);
    }
}