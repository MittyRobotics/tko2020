package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;

public class TurretEncoderUpdater implements Runnable {
    private MPU6050 gyro;
    private final MedianFilter velocityFilter = new MedianFilter(50);
    private final MedianFilter positionFilter = new MedianFilter(10);
    private double lastEncoderPos = 0;
    private double lastGyroPos = 0;
    private double filteredPosition = 0;
    private double lastFilteredPosition = 0;
    private double lastTime;

    public TurretEncoderUpdater() {
        try {
            gyro = new MPU6050();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gyro.start();
        gyro.calibrateSensors();
        lastTime = Timer.getFPGATimestamp();
    }

    @Override
    public void run() {
        double time = Timer.getFPGATimestamp();
        double dt = time - lastTime;
        lastTime = time;

        //Update gyro
        gyro.updateValues();

        //Get gyro position and velocity and convert to encoder ticks
        double gyroPos = gyro.getGyroAngleY() * TurretConstants.TICKS_PER_ANGLE;
        double gyroVel = gyro.getGyroAngularSpeedZ() * TurretConstants.TICKS_PER_ANGLE;

        //Get encoder position and velocity, convert velocity from ticks/100ms to ticks/s
        double encoderPos = TurretSubsystem.getInstance().getTalon().getSelectedSensorPosition();
        double encoderVel = TurretSubsystem.getInstance().getTalon().getSelectedSensorVelocity() * 10;

        //Median filter encoder position and velocity
        double filteredEncoderPos = positionFilter.calculate(encoderPos);
        double filteredEncoderVel = velocityFilter.calculate(encoderVel);

        //Get position delta for encoder and gyro
        double deltaEncoder = filteredEncoderPos - lastEncoderPos;
        double deltaGyro = gyroPos - lastGyroPos;

        //Detect encoder malfunction. Compares encoder velocity to gyro velocity, if encoder spikes unexplainable high,
        //temporarily switch to the less accurate gyro position delta. Otherwise, use the more accurate encoder
        //position delta.
        if (Math.abs(encoderVel - gyroVel) < 1000 && (Math.abs(filteredEncoderVel - encoderVel)) < 1000) {
            filteredPosition += deltaEncoder;
        } else {
            filteredPosition += deltaGyro;
        }

        //Calculate velocity of filtered position
        double filteredVelocity = (lastFilteredPosition - filteredPosition) / dt;

        //Update turret subsystem encoder values
        TurretSubsystem.getInstance().updateEncoder(gyro.getGyroAngleZ(), gyro.getGyroAngularSpeedZ(), filteredEncoderPos, encoderVel, filteredPosition, filteredVelocity);

        //Update last values
        lastEncoderPos = filteredEncoderPos;
        lastGyroPos = gyroPos;
        lastFilteredPosition = filteredPosition;
    }
}
