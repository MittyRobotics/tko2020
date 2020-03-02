package com.github.mittyrobotics.interfaces;

public interface IMotorSubsystem extends ISubsystem {
    void stopMotor();

    void setMotor(double percent);

    default void resetEncoder() {

    }

    default double getPosition() {
        return 0;
    }

    default double getVelocity() {
        return 0;
    }

    default boolean getSwitch(){
        return false;
    }

}