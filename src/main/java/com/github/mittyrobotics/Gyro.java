package com.github.mittyrobotics;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Gyro extends ADXRS450_Gyro {
	private Gyro instance = new Gyro();
	private Gyro getInstance(){
		if(instance == null){
			instance = new Gyro();
		}
		return instance;
	}
	private Gyro(){
		super();
	}
	private double getAngle360(){
		double angle = getAngle();
		angle %= 360;
		if(angle < 0){
			angle += 360;
		}
		return angle;
	}
}
