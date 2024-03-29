/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.shooter;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class TurretSubsystem extends SubsystemBase implements IMotorSubsystem {
    /**
     * {@link TurretSubsystem} instance.
     */
    private static TurretSubsystem instance;
    /**
     * Turret {@link WPI_TalonSRX}
     */
    private WPI_TalonSRX turretTalon;
    /**
     * Left and right limit switches for the turret in the form of a {@link DigitalInput}.
     */
    private DigitalInput limitSwitchLeft, limitSwitchRight;
    /**
     * Turret's {@link PIDController}. Calculates percent output values to apply to the motor based on a PID loop.
     * <p>
     * Needs to be updated periodically with the {@link #updateTurretControlLoop()} method.
     */
    private PIDController turretController;

    /**
     * The turret's maximum percent output for the {@link PIDController} control loop.
     */
    private double maxPercent;

    private double encoderCalibration;

    private TurretSubsystem() {
        super();
        setName("Turret");
    }

    /**
     * Returns the {@link TurretSubsystem}'s {@link SubsystemBase} instance.
     *
     * @return the {@link TurretSubsystem}'s {@link SubsystemBase} instance.
     */
    public static TurretSubsystem getInstance() {
        if (instance == null) {
            instance = new TurretSubsystem();
        }
        return instance;
    }

    /**
     * Initializes all hardware associated with the class
     */
    @Override
    public void initHardware() {
        //Config talon
        turretTalon = new WPI_TalonSRX(TurretConstants.Turret_Talon_ID);
        turretTalon.configFactoryDefault();
        turretTalon.setInverted(TurretConstants.TURRET_TALON_INVERSION);
        turretTalon.config_kP(0, TurretConstants.TURRET_P);
        turretTalon.config_kI(0, TurretConstants.TURRET_I);
        turretTalon.config_kD(0, TurretConstants.TURRET_D);
      limitSwitchLeft = new DigitalInput(TurretConstants.TURRET_SWITCH_ID);
      limitSwitchRight = new DigitalInput(TurretConstants.TURRET_SWITCH_2_ID);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        turretTalon.setSensorPhase(TurretConstants.TURRET_ENCODER_INVERSION);
        turretTalon.setNeutralMode(NeutralMode.Coast);
        turretTalon.setSelectedSensorPosition(0);
        //Initialize PIDController
        turretController =
                new PIDController(TurretConstants.TURRET_P, TurretConstants.TURRET_I, TurretConstants.TURRET_D);
        turretController.enableContinuousInput(0, TurretConstants.REVOLUTION_TICKS - 1);

        encoderCalibration = turretTalon.getSelectedSensorPosition();
    }

    /**
     * Updates the {@link SmartDashboard} values associated with the class
     */
    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Turret Position", getAngle());
        SmartDashboard.putNumber("Turret Velocity", getVelocity());
    }

    /**
     * Set the turret's percent output, limiting it's range from the limit switches.
     *
     * @param percent the turret motor percent output.
     */
    public void setMotor(double percent) {
        if (((getLeftSwitch() || getAngle() >= 90) && percent > 0) || ((getRightSwitch()|| getAngle() <= -90) && percent < 0)) {
            overrideSetMotor(0);
        } else {
            overrideSetMotor(percent);
        }
    }

    /**
     * Sets the turret's robot-relative angle.
     *
     * @param angle the robot-relative angle to set the turret to.
     */
    public void setTurretAngle(double angle) {
        turretTalon.setSelectedSensorPosition(0);
    }

    /**
     * Sets the turret's maximum percent output for the {@link PIDController} control loop.
     *
     * @param maxPercent max percent output value
     */
    public void setControlLoopMaxPercent(double maxPercent) {
        this.maxPercent = maxPercent;
    }

    /**
     * Changes the turret's robot-relative angle by <code>angle</code>.
     * <p>
     * Follows standard polar coordinate system, 0 is straight forward, negative angle changes it to the right,
     * positive angle changes it to the left.
     *
     * @param angle the angle to change the turret's robot-relative angle by.
     */
    public void changeTurretAngle(double angle) {
        setTurretAngle(angle + getAngle());
    }

    /**
     * Updates the turret's {@link PIDController} control loop.
     * <p>
     * This should be called periodically whenever the turret is automated.
     */
    public void updateTurretControlLoop() {
        setMotor(MathUtil.clamp(turretController.calculate(getPosition()), -maxPercent, maxPercent));
    }

    /**
     * Caps the angle setpoint from 0 to the maximum angle of the turret. Used for the {@link PIDController}'s
     * continuous mode.
     *
     * @param angle the angle to cap.
     * @return the capped angle.
     */
    private double capAngleSetpoint(double angle) {
        angle %= TurretConstants.REVOLUTION_TICKS / TurretConstants.TICKS_PER_ANGLE;
        if (angle < 0) {
            angle += TurretConstants.REVOLUTION_TICKS / TurretConstants.TICKS_PER_ANGLE;
        }
        return angle;
    }

    @Override
    public void overrideSetMotor(double percent) {
        turretTalon.set(percent);
    }

    /**
     * Returns the turret's encoder position in ticks.
     *
     * @return the turret's encoder position in ticks.
     */
    @Override
    public double getPosition() {
        return turretTalon.getSelectedSensorPosition();
    }

    /**
     * Returns the left limit switch's value.
     *
     * @return the left limit switch's value.
     */
    @Override
    public boolean getLeftSwitch() {
        return !limitSwitchLeft.get();
    }

    /**
     * Returns the right limit switch's value.
     *
     * @return the right limit switch's value.
     */
    @Override
    public boolean getRightSwitch() {
        return !limitSwitchRight.get();
    }

    /**
     * Returns the turret's robot-relative angle calculated by the encoder value divided by a ticks per inch constant.
     *
     * @return the turret's robot-relative angle
     */
    public double getAngle() {
        return (turretTalon.getSelectedSensorPosition() - encoderCalibration) / TurretConstants.TICKS_PER_ANGLE;
//        return turretTalon.getSelectedSensorPosition();
    }

    @Override
    public double getVelocity() {
        return (turretTalon.getSelectedSensorVelocity()/TurretConstants.TICKS_PER_ANGLE) * 10;
    }

    /**
     * Returns the turret's {@link PIDController} error.
     * <p>
     * This is the difference between the setpoint and the actual value of the encoder, measured in ticks.
     *
     * @return the turret's {@link PIDController} error.
     */
    public double getError() {
        return turretController.getPositionError();
    }


    private double vP = 0.002;
    private double vFF = 0.5/111.2068;
    public double turretVelocity(double velocity){
        double vE = velocity-getVelocity();
        return velocity * vFF + vE * vP;
    }

    private double pP = 0.03;
    private double pD = 0.002;
    private double lastE = 0;
    public double turretPID(double angle){
        double pE = angle-getAngle();
        double v = pE*pP + ((pE-lastE)/0.02)*pD;
        lastE = pE;
        return v;
    }

    public double turretCascadeControl(double angle, double velocity){
        double p = turretPID(angle) * 100;

        double vel = Math.min(Math.abs(p), Math.abs(velocity)) * Math.signum(p);
        double v = turretVelocity(vel);
        return v;
    }

    /**
     * Returns the turret's {@link PIDController} setpoint.
     * <p>
     * This is the setpoint of the PID loop measured in ticks.
     *
     * @return the turret's {@link PIDController} setpoint.
     */
    public double getSetpoint() {
        return turretController.getSetpoint();
    }

    /**
     * Returns the turret's maximum percent output for the {@link PIDController} control loop.
     *
     * @return the turret's maximum percent output for the {@link PIDController} control loop.
     */
    public double getMaxPercent() {
        return maxPercent;
    }
}