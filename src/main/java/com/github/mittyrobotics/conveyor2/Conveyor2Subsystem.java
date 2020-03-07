package com.github.mittyrobotics.conveyor2;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor2Subsystem extends SubsystemBase implements IMotorSubsystem {
    private WPI_TalonSRX conveyorTalon1, conveyorTalon2;
    private DigitalInput sensor;
    private boolean prevSwitchValue;
    private int ballCount;
    public Conveyor2Subsystem(){
        super();
    }
    private static Conveyor2Subsystem instance;
    public static Conveyor2Subsystem getInstance(){
        if(instance == null){
            instance = new Conveyor2Subsystem();
        }
        return instance;
    }

    @Override
    public void overrideSetMotor(double percent) {
        conveyorTalon1.set(percent);
        conveyorTalon2.set(percent);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Ball Count: ", ballCount);
    }

    @Override
    public void initHardware() {
        conveyorTalon1 = new WPI_TalonSRX(41);
        conveyorTalon2 = new WPI_TalonSRX(42);
        conveyorTalon1.configFactoryDefault();
        conveyorTalon2.configFactoryDefault();
        conveyorTalon1.setInverted(true);
        conveyorTalon2.setInverted(false);
        conveyorTalon1.setSensorPhase(true);
        conveyorTalon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        sensor = new DigitalInput(0);
        prevSwitchValue = false;
        ballCount = 0;
    }

    @Override
    public boolean getSwitch(){
        return sensor.get();
    }

    @Override
    public double getPosition(){
        return conveyorTalon1.getSelectedSensorPosition() / Conveyor2Constants.TICKS_PER_CYCLE;
    }

    @Override
    public void periodic(){
        if(!prevSwitchValue && getSwitch() && conveyorTalon1.get() >= 0){
            ballCount++;
            new WaitUntilSensorCommand().schedule();
        }
        if(prevSwitchValue && !getSwitch()){
            ballCount--;
        }
    }

    public void joystickConveyorControl(double percent) {
        if (Math.abs(percent) > 0.05) {
            setMotor(percent);
        } else {
            stopMotor();
        }
    }
    @Override
    public void resetEncoder(){
        conveyorTalon1.setSelectedSensorPosition(0);
    }

}