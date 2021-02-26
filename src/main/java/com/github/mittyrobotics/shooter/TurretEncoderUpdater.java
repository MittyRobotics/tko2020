package com.github.mittyrobotics.shooter;

import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;

public class TurretEncoderUpdater implements Runnable {
    private MPU6050 gyro;
    private final MedianFilter velocityFilter = new MedianFilter(50);
    private final MedianFilter positionFilter = new MedianFilter(10);
    private double lastEncoderPos = 0;
    private double lastGyroPos = 0;
    private double lastGyroVel = 0;
    private double pos = 0;
    public TurretEncoderUpdater() {
        try {
            gyro = new MPU6050();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gyro.start();
        gyro.calibrateSensors();
    }

    @Override
    public void run() {
        double gyroPos = gyro.getGyroAngleY();
        double gyroVel = gyro.getGyroAngularSpeedZ();
        double encoderPos = TurretSubsystem.getInstance().getTalon().getSelectedSensorPosition() / TurretConstants.TICKS_PER_ANGLE;
        encoderPos = positionFilter.calculate(encoderPos);
        double encoderVel = TurretSubsystem.getInstance().getTalon().getSelectedSensorVelocity()*10/TurretConstants.TICKS_PER_ANGLE;
        double filteredEncoderVel = velocityFilter.calculate(encoderVel);

        double deltaEncoder = encoderPos-lastEncoderPos;
        double deltaGyro = gyroPos-lastGyroPos;

        if(Math.abs(encoderVel-lastGyroVel) < 100 && (Math.abs(filteredEncoderVel-encoderVel)) < 100){
            pos += deltaEncoder;
        }
        else{
            pos += deltaGyro;
        }

        TurretSubsystem.getInstance().updateEncoder(gyroPos, lastGyroVel, pos, encoderVel);
        lastEncoderPos = encoderPos;
        lastGyroVel = gyroVel;

        lastGyroPos = gyroPos;
    }

    public void resetPosition(){
        lastEncoderPos =  TurretSubsystem.getInstance().getTalon().getSelectedSensorPosition() / TurretConstants.TICKS_PER_ANGLE;
    }
}
