package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.ConveyorConstants;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class ConveyorSubsystem extends SubsystemBase implements IMotorSubsystem {
    private WPI_TalonSRX conveyorTalon1, conveyorTalon2;
    private DigitalInput sensor;
    private int ballCount;
    public ConveyorSubsystem(){
        super();
    }
    private static ConveyorSubsystem instance;
    public static ConveyorSubsystem getInstance(){
        if(instance == null){
            instance = new ConveyorSubsystem();
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
        ballCount = 0;
    }

    @Override
    public boolean getSwitch(){
        return sensor.get();
    }

    @Override
    public double getPosition(){
        return conveyorTalon1.getSelectedSensorPosition() / ConveyorConstants.TICKS_PER_CYCLE;
    }

    @Override
    public void periodic(){

    }

    @Override
    public void resetEncoder(){
        conveyorTalon1.setSelectedSensorPosition(0);
    }

    public int getBallCount(){
        return ballCount;
    }

    public void updateBallCount(int increase){
        ballCount = MathUtil.clamp(ballCount + increase, 0, 5);
    }

    public void resetBallCount(){
        ballCount = 0;
    }

    public void indexBall(){
        setMotor(ConveyorConstants.INDEX_SPEED);
    }

    public void outtakeBall(){
        setMotor(ConveyorConstants.OUTTAKE_SPEED);
    }

    public void shoveBall(){
        setMotor(ConveyorConstants.SHOVE_SPEED);
    }

    @Override
    public void setMotor(double speed){
        if(getBallCount() > 4 && speed > 0){
            overrideSetMotor(0);
        } else {
            overrideSetMotor(speed);
        }
    }

    public void shootBall(){
        setMotor(ConveyorConstants.SHOOT_SPEED);
    }

}