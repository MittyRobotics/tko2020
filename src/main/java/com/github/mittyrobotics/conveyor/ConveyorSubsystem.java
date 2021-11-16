package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Conveyor subsystem to move balls from the {@link IntakeSubsystem} to the {@link ShooterSubsystem}
 */
public class ConveyorSubsystem extends SubsystemBase implements IMotorSubsystem {

    /**
     * {@link ConveyorSubsystem} instance
     */
    private static ConveyorSubsystem instance;

    /**
     * Conveyor {@link WPI_TalonSRX}s
     */
    private WPI_TalonSRX conveyorTalonBottom, conveyorTalonTop;

    /**
     * Conveyor {@link DigitalInput} to detect when balls enter
     */
    private DigitalInput ballSensor;
    private DigitalInput shooterBallSensor;

    /**
     * Counter of the current # of balls in the conveyor
     */

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Conveyor"
     */
    public ConveyorSubsystem(){
        super();
        setName("Conveyor");
    }

    /**
     * Returns the {@link ConveyorSubsystem} instance.
     *
     * @return the {@link ConveyorSubsystem} instance.
     */
    public static ConveyorSubsystem getInstance(){
        if(instance == null){
            instance = new ConveyorSubsystem();
        }
        return instance;
    }

    /**
     * Sets the conveyor to move at a certain voltage, regardless of other conditions
     *
     * @param percent the percent of both sides of the conveyor
     */
    @Override
    public void overrideSetMotor(double percent) {
        conveyorTalonBottom.set(percent);
        conveyorTalonTop.set(percent);
    }

    /**
     * Update the conveyor's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Ball Detected: ", isBallDetected());
    }

    /**
     * Initialize the conveyor's hardware
     */
    @Override
    public void initHardware() {
        conveyorTalonBottom = new WPI_TalonSRX(ConveyorConstants.CONVEYOR_BOTTOM_ID);
        conveyorTalonTop = new WPI_TalonSRX(ConveyorConstants.CONVEYOR_TOP_ID);
        conveyorTalonBottom.configFactoryDefault();
        conveyorTalonTop.configFactoryDefault();
        conveyorTalonBottom.setInverted(ConveyorConstants.CONVEYOR_TOP_INVERSION);
        conveyorTalonTop.setInverted(ConveyorConstants.CONVEYOR_BOTTOM_INVERSION);
        conveyorTalonBottom.setSensorPhase(ConveyorConstants.CONVEYOR_ENCODER_INVERSION);
        conveyorTalonBottom.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        conveyorTalonBottom.setNeutralMode(ConveyorConstants.CONVEYOR_NEUTRAL_MODE);
        conveyorTalonTop.setNeutralMode(ConveyorConstants.CONVEYOR_NEUTRAL_MODE);
        ballSensor = new DigitalInput(ConveyorConstants.BALL_SENSOR_ID);
        shooterBallSensor = new DigitalInput(ConveyorConstants.SHOOTER_BALL_SENSOR_ID);
    }

    /**
     * Returns if a ball is detected
     *
     * @return if a ball is detected
     */
    public boolean isBallDetected(){
        return !ballSensor.get();
    }

    public boolean isShooterBallDetected() {
        return !shooterBallSensor.get();
    }

    /**
     * Returns the current position of the conveyor
     *
     * @return the current position of the conveyor
     */
    @Override
    public double getPosition(){
        return conveyorTalonBottom.getSelectedSensorPosition() / ConveyorConstants.TICKS_PER_CYCLE;
    }

    /**
     * Resets the encoder value to 0
     */
    @Override
    public void resetEncoder(){
        conveyorTalonBottom.setSelectedSensorPosition(0);
    }

    /**
     * Sets the conveyor to the speed to outtake a ball
     */
    public void outtakeBall(){
        setMotor(ConveyorConstants.OUTTAKE_SPEED);
    }

    /**
     * Runs the conveyor to a certain speed if the ball count is not already maximized and the speed is greater than 0 (inputting balls)
     *
     * @param speed speed to run conveyor at
     */
    @Override
    public void setMotor(double speed){
        overrideSetMotor(speed);
    }


    /**
     * Runs the conveyor at the speed to shoot balls (ignores ball count since releasing balls)
     */
    public void shootBall(){
        overrideSetMotor(ConveyorConstants.SHOOT_SPEED);
    }

}