package com.github.mittyrobotics.shooter;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.motion.modeling.Plant;
import com.github.mittyrobotics.motion.modeling.models.FlywheelModel;
import com.github.mittyrobotics.motion.modeling.motors.Pro775Motor;
import com.github.mittyrobotics.motion.observers.KalmanFilter;
import com.github.mittyrobotics.util.Gyro;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.ejml.simple.SimpleMatrix;

import javax.print.attribute.standard.Media;

public class TurretEncoderUpdater implements Runnable {
    private double lastTime;
    private double lastPosition;

    private Plant turretPlant;
    private FlywheelModel model;

    private KalmanFilter kalmanFilter;

    private DigitalInput input;
    private DutyCycle cycle;

    private double estimatedPos = 0;

    private MedianFilter filter = new MedianFilter(10);

    public TurretEncoderUpdater() {
        lastTime = Timer.getFPGATimestamp();

        model = new FlywheelModel(new Pro775Motor(1), (1.0/2.0)*(10)*(Math.pow(.356/2, 2)), 1/462.0, 12);
        turretPlant = model.getPlant();

        SimpleMatrix A = new SimpleMatrix(new double[][]{{1}});
        SimpleMatrix B = new SimpleMatrix(new double[][]{{0.02}});
        SimpleMatrix H = new SimpleMatrix(new double[][]{{1}});
        SimpleMatrix Q = new SimpleMatrix(new double[][]{{5}});
        SimpleMatrix R = new SimpleMatrix(new double[][]{{10}});
        kalmanFilter = new KalmanFilter(A, B, H, Q, R);

        input = new DigitalInput(8);
        cycle = new DutyCycle(input);

        double pwm = cycle.getOutput();
        double position = -(pwm-TurretConstants.CENTER_PWM)/TurretConstants.PWM_PER_ANGLE;
        estimatedPos = position;
        lastPosition = 0;
    }


    @Override
    public void run() {
        double time = Timer.getFPGATimestamp();
        double dt = time-lastTime;
        lastTime = time;

        double pwm = filter.calculate(cycle.getOutput());
        double position = -(pwm-TurretConstants.CENTER_PWM)/TurretConstants.PWM_PER_ANGLE;
        double velocity = (position-lastPosition)/dt;
        lastPosition = position;
        if(Math.abs(velocity) < 200){
            TurretSubsystem.getInstance().updateEncoder(0, 0, position, velocity);
            SmartDashboard.putNumber("turret-analog", cycle.getOutput());
        }

//        SmartDashboard.putNumber("turret-estimated-vel", estimatedAngularVelocity);
//        SmartDashboard.putNumber("turret-estimated-pos", estimatedPos);
//        SmartDashboard.putNumber("turret-kalman", kalmanFilter.getxHat().get(0));
    }
}
