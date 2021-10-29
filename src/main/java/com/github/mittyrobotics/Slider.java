package com.github.mittyrobotics;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Slider extends SubsystemBase {
    private WPI_TalonSRX talon1;
    private WPI_TalonSRX talon2;
    private static Slider instance;

    private DigitalInput limit1;
    private DigitalInput limit2;

    private Slider() {
        super();
        setName("Slider");
    }

    public static Slider getInstance(){
        if (instance == null) {
            instance = new Slider();
        }
        return instance;
    }

    public void initHardware() {
        talon1 = new WPI_TalonSRX(SliderConstants.TALON_1_INDEX);
        talon2 = new WPI_TalonSRX(SliderConstants.TALON_2_INDEX);

        talon2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        talon1.configFactoryDefault();
        talon2.configFactoryDefault();
        //resets position
        talon1.setSelectedSensorPosition(0);
        talon2.setSelectedSensorPosition(0);
        talon1.setInverted(SliderConstants.TALON_1_INVERTED);
        talon2.setInverted(SliderConstants.TALON_2_INVERTED);

        limit1 = new DigitalInput(SliderConstants.LIMIT_1_INDEX);
        limit2 = new DigitalInput(SliderConstants.LIMIT_2_INDEX);

    }

    public void setMotors(double one) {
        talon1.set(one);
        talon2.set(one);
    }

    public double getMotors() {
        return talon1.get();
    }

    public boolean getSwitch(int ind) {
        if(ind == 1) return limit1.get();
        else return limit2.get();
    }
}
