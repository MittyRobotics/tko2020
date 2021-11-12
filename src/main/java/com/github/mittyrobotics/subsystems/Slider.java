package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.OI.OI;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
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
        talon1.setNeutralMode(NeutralMode.Brake);
        talon2.setNeutralMode(NeutralMode.Brake);

        limit1 = new DigitalInput(SliderConstants.LIMIT_1_INDEX);
        limit2 = new DigitalInput(SliderConstants.LIMIT_2_INDEX);

    }

    public void setMotors(double one) {
        talon1.set(one);
        talon2.set(one);
        System.out.println(one);
    }

    public WPI_TalonSRX[] getMotors() {
        WPI_TalonSRX[] returnArray = {talon1, talon2};
        return returnArray;
    }

    public double getPosition(){

        return talon2.getSelectedSensorPosition();
    }
//musty code
    public boolean getSwitch(int ind) {
        if(ind == 1) return limit1.get();
        else return limit2.get();
    }

    public void motorsWithLimitSwitch(double val) {
        if(OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft) > 0 && Slider.getInstance().getSwitch(0)) {
            Slider.getInstance().setMotors(val);

        } else if (OI.getInstance().getXboxController().getX(GenericHID.Hand.kLeft) < 0 && Slider.getInstance().getSwitch(1)) {
            Slider.getInstance().setMotors(val);
        } else if (Slider.getInstance().getSwitch(0) || Slider.getInstance().getSwitch(1)) {
            Slider.getInstance().setMotors(0);
        } else {
            Slider.getInstance().setMotors(val);
        }

        if(Slider.getInstance().getSwitch(1)){
            System.out.println("limit switch 1 was pressed omfg");
        } else if(Slider.getInstance().getSwitch(0)) {
            System.out.println("limit switch 2 was pressed omfg");
        }
    }

    public void setMotorsTicks(double val) {
        talon1.set(ControlMode.Position, val);
        talon2.set(ControlMode.Position, val);
    }
}
