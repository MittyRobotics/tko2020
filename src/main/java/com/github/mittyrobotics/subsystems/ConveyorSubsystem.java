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
        SmartDashboard.putBoolean("Ball Detected: ", getSwitch());
    }

    @Override
    public void initHardware() {
        conveyorTalon1 = new WPI_TalonSRX(ConveyorConstants.CONVEYOR_TOP_ID);
        conveyorTalon2 = new WPI_TalonSRX(ConveyorConstants.CONVEYOR_BOTTOM_ID);
        conveyorTalon1.configFactoryDefault();
        conveyorTalon2.configFactoryDefault();
        conveyorTalon1.setInverted(ConveyorConstants.CONVEYOR_TOP_INVERSION);
        conveyorTalon2.setInverted(ConveyorConstants.CONVEYOR_BOTTOM_INVERSION);
        conveyorTalon1.setSensorPhase(ConveyorConstants.CONVEYOR_ENCODER_INVERSION);
        conveyorTalon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        conveyorTalon1.setNeutralMode(ConveyorConstants.CONVEYOR_NEUTRAL_MODE);
        conveyorTalon2.setNeutralMode(ConveyorConstants.CONVEYOR_NEUTRAL_MODE);
        sensor = new DigitalInput(ConveyorConstants.BALL_SENSOR_ID);
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
    public void resetEncoder(){
        conveyorTalon1.setSelectedSensorPosition(0);
    }

    public int getBallCount(){
        return ballCount;
    }

    public void updateBallCount(int increase){
//        if(IntakePistonSubsystem.getInstance().isPistonExtended()){
            ballCount = MathUtil.clamp(ballCount + increase, ConveyorConstants.MINIMUM_BALL_COUNT, ConveyorConstants.MAXIMUM_BALL_COUNT);
//        }
    }

    public void resetBallCount(){
        ballCount = ConveyorConstants.MINIMUM_BALL_COUNT;
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
        if(getBallCount() > ConveyorConstants.MAXIMUM_BALL_COUNT - 1 && speed > 0){
            overrideSetMotor(0);
        } else {
            overrideSetMotor(speed);
        }
    }

    @Override
    public void periodic() {
        System.out.println(sensor.get());
    }

    public void shootBall(){
        setMotor(ConveyorConstants.SHOOT_SPEED);
    }

}