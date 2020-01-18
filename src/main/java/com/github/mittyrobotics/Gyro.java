package com.github.mittyrobotics;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Gyro extends ADXRS450_Gyro {
	private static Gyro instance = new Gyro();
	public static Gyro getInstance(){
		if(instance == null){
			instance = new Gyro();
		}
		return instance;
	}
	private Gyro(){
		super();
	}
}
