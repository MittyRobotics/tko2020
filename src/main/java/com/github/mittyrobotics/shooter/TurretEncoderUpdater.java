package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.Timer;

public class TurretEncoderUpdater implements Runnable {

    TurretGyro gyro;
    MedianFilter filter = new MedianFilter(50);
    MedianFilter filterPos = new MedianFilter(10);

    double lastTime = 0;
    public TurretEncoderUpdater(){
        try {
            gyro = new TurretGyro();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gyro.start();
        gyro.calibrateSensors();
        lastTime = Timer.getFPGATimestamp();
    }

    double calibEncoderPosition = 0;
    double lastEncoderVel = 0;
    double lastEncoderPosCalib = 0;
    double lastEncoderPos = 0;
    double lastGyroPos = 0;
    double pos = 0;

    @Override
    public void run() {
        double time = Timer.getFPGATimestamp();
        double dt = time-lastTime;
        lastTime = time;
        gyro.updateValues();
        double gyroPos = gyro.getGyroAngleY();
        double gyroVel = gyro.getGyroAngularSpeedZ();
        double encoderPos = TurretSubsystem.getInstance().getTalon().getSelectedSensorPosition() / TurretConstants.TICKS_PER_ANGLE;
        encoderPos = filterPos.calculate(encoderPos);
        double encoderVel = TurretSubsystem.getInstance().getTalon().getSelectedSensorVelocity()*10/TurretConstants.TICKS_PER_ANGLE;
        double filteredEncoderVel = filter.calculate(encoderVel);

        double deltaEncoder = encoderPos-lastEncoderPos;
        double deltaGyro = gyroPos-lastGyroPos;

        if(Math.abs(encoderVel-gyroVel) < 100 && (Math.abs(filteredEncoderVel-encoderVel)) < 100){
            pos += deltaEncoder;
        }
        else{
            pos += deltaGyro;
        }
        TurretSubsystem.getInstance().updateEncoder(gyro.getGyroAngleZ(), gyro.getGyroAngularSpeedZ(), pos, encoderVel);
        lastEncoderPos = encoderPos;
        lastGyroPos = gyroPos;
    }
}
