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
}
