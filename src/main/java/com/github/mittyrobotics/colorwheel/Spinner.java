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

import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.WeakHashMap;

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
    private final Color nullTarget = ColorMatch.makeColor(NULL_R, NULL_G, NULL_B);
    private final Color alsoNullTarget = ColorMatch.makeColor(ALSO_NULL_R, ALSO_NULL_G, ALSO_NULL_B);


    private HashMap<WheelColor, WheelColor> map = new HashMap<>();

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
        //System.out.println(Spinner.getInstance().getColor());
    }

    public void initHardware() {
        //initialize talon
        talon1 = new WPI_TalonSRX(20);
        talon1.setSensorPhase(true);


        //sets color match
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
        m_colorMatcher.addColorMatch(nullTarget);
        m_colorMatcher.addColorMatch(alsoNullTarget);
        map.put(WheelColor.Blue, WheelColor.Red);
        map.put(WheelColor.Red, WheelColor.Blue);
        map.put(WheelColor.Green, WheelColor.Yellow);
        map.put(WheelColor.Yellow, WheelColor.Green);
        talon1.setNeutralMode(NeutralMode.Brake);
        //setDefaultCommand(new SpinRevs());

    }

    public void setMotorFast() {
        //sets motor to fast velocity
        talon1.set(ControlMode.Velocity, FAST_VELOCITY);

    }

    public void setMotorPID(double rpm){
        double setpoint = (rpm * (4 * Math.PI)) * TICKS_PER_INCH / 600.0; //Ticks per 100ms
        PIDController controller = new PIDController(0.000001, 0, 0);
        controller.setSetpoint(setpoint);
        //System.out.println(controller.calculate(talon1.getSelectedSensorVelocity()));
        //System.out.println("MOTOR OUT" + talon1.getMotorOutputPercent());
        talon1.set(ControlMode.PercentOutput, 1/6250.0 * setpoint
                + controller.calculate(talon1.getSelectedSensorVelocity())
        );
    }
    public void setMotorSlow() {
        //sets motor to slow velocity
        talon1.set(ControlMode.Velocity, SLOW_VELOCITY);

    }

    public void position(double pos) {
        talon1.set(ControlMode.Position, pos * TICKS_PER_INCH);
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
        return talon1.getSelectedSensorPosition() / (32*Math.PI*TICKS_PER_INCH);
    }

    public void zeroEncoder() {
        talon1.setSelectedSensorPosition(0);
    }

    public double getEncoder() {
        return talon1.getSelectedSensorPosition();
    }

    public double returnDistanceFromTarget() {
        return talon1.getClosedLoopTarget();
    }

    public boolean matching() {
        //return target color equals current color
        return getGameMessage() == map.get(getColor());
    }

    public boolean testMatching(){
        return getColor() == WheelColor.Blue;
    }

    public void setMotor(double percent) {
        talon1.set(ControlMode.PercentOutput, percent);
    }

    public double getVelocity() {
        return talon1.getSelectedSensorVelocity();
    }

}