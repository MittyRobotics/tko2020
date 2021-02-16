package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.climber.commands.StopClimberCommand;
import com.github.mittyrobotics.util.interfaces.IDualMotorSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Climber subsystem to climb
 */
public class ClimberSubsystem extends SubsystemBase implements IDualMotorSubsystem {

    /**
     * {@link ClimberSubsystem} instance
     */
    private static ClimberSubsystem instance;

    /**
     * Climber {@link CANSparkMax}s
     */
    private CANSparkMax climberLeftSparkMaster, climberRightSparkMaster;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Climber"
     */
    private ClimberSubsystem() {
        super();
        setName("Climber");
    }

    /**
     * Returns the {@link ClimberSubsystem} instance.
     *
     * @return the {@link ClimberSubsystem} instance.
     */
    public static ClimberSubsystem getInstance() {
        if (instance == null) {
            instance = new ClimberSubsystem();
        }
        return instance;
    }

    /**
     * Initializes the climber's hardware.
     */
    @Override
    public void initHardware() {
        climberLeftSparkMaster =
                new CANSparkMax(ClimberConstants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberLeftSparkMaster.restoreFactoryDefaults();
        climberLeftSparkMaster.setInverted(ClimberConstants.LEFT_SPARK_INVERSION);

        climberRightSparkMaster =
                new CANSparkMax(ClimberConstants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberRightSparkMaster.restoreFactoryDefaults();
        climberRightSparkMaster.setInverted(ClimberConstants.RIGHT_SPARK_INVERSION);
        setDefaultCommand(new StopClimberCommand());
    }

    /**
     * Update the climber's dashboard values
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Left Encoder Position", getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder Position", getRightEncoderPosition());
    }

    /**
     * Sets each climber to move if the {@link RatchetSubsystem} is not locked
     *
     * @param leftPercent percent to set the left climber
     *
     * @param rightPercent percent to set the right climber
     */
    public void setSparks(double leftPercent, double rightPercent) {
        if(RatchetSubsystem.getInstance().isLeftWinchLocked()){
            leftPercent = 0;
        }
        if(RatchetSubsystem.getInstance().isRightWinchLocked()){
            rightPercent = 0;
        }
        setMotorPercent(leftPercent, rightPercent);
    }

    /**
     * Stops the climber from moving
     */
    public void stopSparks() {
        setSparks(0, 0);
    }

    /**
     * Returns the left encoder position
     *
     * @return the left encoder position
     */
    public double getLeftEncoderPosition() {
        return climberLeftSparkMaster.getEncoder().getPosition();
    }

    /**
     * Returns the right encoder position
     *
     * @return the right encoder position
     */
    public double getRightEncoderPosition() {
        return climberRightSparkMaster.getEncoder().getPosition();
    }

    /**
     * Sets the climber to move regardless if the {@link RatchetSubsystem} is locked
     *
     * @param leftPercent the percent of the left motor
     *
     * @param rightPercent the percent of the right motor
     */
    public void setMotorPercent(double leftPercent, double rightPercent) {
        climberLeftSparkMaster.set(leftPercent);
        climberRightSparkMaster.set(rightPercent);
    }
}