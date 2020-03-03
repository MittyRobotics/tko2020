package com.github.mittyrobotics.util.interfaces;

/**
 * Interface for all motor based subsystem classes
 */
public interface IMotorSubsystem extends ISubsystem {
    /**
     * Stops the motor from moving
     */
    default void stopMotor() {
        setMotor(0);
    }

    /**
     * Sets the motor to run at a certain percent (using duty cylce)
     * @param percent the percentage to run the motor at
     */
    void setMotor(double percent);

    /**
     * Resets an encoder position
     */
    default void resetEncoder() {

    }

    /**
     * Gets an encoder position
     * @return an encoder position
     */
    default double getPosition() {
        return 0;
    }

    /**
     * Gets an encoder velocity
     * @return an encoder velocity
     */
    default double getVelocity() {
        return 0;
    }

    /**
     * Gets if a limit switch
     * @return if a limit switch is being pressed
     */
    default boolean getSwitch(){
        return false;
    }

    default boolean getLeftSwitch(){
        return false;
    }

    default boolean getRightSwitch(){
        return false;
    }

}