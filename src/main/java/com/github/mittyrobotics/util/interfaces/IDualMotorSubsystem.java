package com.github.mittyrobotics.util.interfaces;

public interface IDualMotorSubsystem extends ISubsystem{
    default void stopMotor() {
        setMotor(0, 0);
    }
    void setMotor(double percent1, double percent2);

    default void resetEncoder() {

    }

    default double getLeftPosition() {
        return 0;
    }

    default double getLeftVelocity() {
        return 0;
    }

    default double getRightPosition(){
        return 0;
    }

    default double getRightVelocity(){
        return 0;
    }

    default double getAverageVelocity(){
        return (getLeftVelocity() + getRightVelocity())/2;
    }

    default double getAveragePosition(){
        return (getLeftPosition() + getLeftPosition())/2;
    }
}