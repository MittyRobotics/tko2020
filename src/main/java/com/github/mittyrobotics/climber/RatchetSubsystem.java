package com.github.mittyrobotics.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Ratchet Subsystem to lock or unlock the {@link ClimberSubsystem}
 */
public class RatchetSubsystem extends SubsystemBase {

    /**
     * {@link RatchetSubsystem} instance
     */
    private static RatchetSubsystem instance;

    /**
     * Ratchet {@link DoubleSolenoid}s
     */
    private DoubleSolenoid leftPiston, rightPiston;

    /**
     * Calls {@link SubsystemBase} constructor and names the subsystem "Ratchet"
     */
    private RatchetSubsystem() {
        super();
        setName("Ratchet");
    }

    /**
     * Returns the {@link RatchetSubsystem} instance.
     *
     * @return the {@link RatchetSubsystem} instance.
     */
    public static RatchetSubsystem getInstance() {
        if (instance == null) {
            instance = new RatchetSubsystem();
        }
        return instance;
    }

    /**
     * Initializes the ratchet hardware
     */
    public void initHardware() {
        leftPiston = new DoubleSolenoid(ClimberConstants.LEFT_SOLENOID_FOWARD_CHANNEL,
                ClimberConstants.LEFT_SOLENOID_REVERSE_CHANNEL);
        rightPiston = new DoubleSolenoid(ClimberConstants.RIGHT_SOLENOID_FOWARD_CHANNEL,
                ClimberConstants.RIGHT_SOLENOID_REVERSE_CHANNEL);
    }

    /**
     * Locks the left ratchet
     */
    public void lockLeftWinch() {
        leftPiston.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Locks the right ratchet
     */
    public void lockRightWinch() {
        rightPiston.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Unlocks the left ratchet
     */
    public void unlockLeftWinch() {
        leftPiston.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Unlocks the right ratchet
     */
    public void unlockRightWinch() {
        rightPiston.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Locks both winches
     */
    public void lockWinch() {
        lockLeftWinch();
        lockRightWinch();
    }

    /**
     * Unlocks both winches
     */
    public void unlockWinch() {
        unlockLeftWinch();
        unlockRightWinch();
    }

    /**
     * Returns if the left winch is locked
     * @return if the left winch is locked
     */
    public boolean isLeftWinchLocked() {
        return leftPiston.get() != DoubleSolenoid.Value.kForward;
    }

    /**
     * Returns if the right winch is locked
     * @return if the right winch is locked
     */
    public boolean isRightWinchLocked() {
        return rightPiston.get() != DoubleSolenoid.Value.kForward;
    }

    /**
     * Returns if both winches are locked
     * @return if both winches are locked
     */
    public boolean isWinchLocked() {
        return (isLeftWinchLocked() && isRightWinchLocked());
    }
}