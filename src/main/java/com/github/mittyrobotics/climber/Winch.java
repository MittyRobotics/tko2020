package com.github.mittyrobotics.climber;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;


public class Winch extends SubsystemBase {

    private static Winch ourInstance = new Winch();

    private CANSparkMax leftSpark;
    private CANSparkMax rightSpark;
    private CANPIDController leftController, rightController;

    private Winch() {
        super();
    }

    public static Winch getInstance() {
        return ourInstance;
    }

    public void initHardware() {
        leftSpark = new CANSparkMax(Constants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark = new CANSparkMax(Constants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftSpark.restoreFactoryDefaults();
        rightSpark.restoreFactoryDefaults();

        //TODO setup PID Here, not in the command
        leftController = new CANPIDController(leftSpark);
        rightController = new CANPIDController(rightSpark);
    }

    //TODO change to use the getPIDController() function instead of making a new one
    public CANPIDController getLeftController(){
        return leftController;
    }

    public CANEncoder getLeftEncoder(){
        return leftSpark.getEncoder();
    }

    //TODO change to use the getPIDController() function instead of making a new one
    public CANPIDController getRightController(){
        return rightController;
    }

    public CANEncoder getRightEncoder(){
        return rightSpark.getEncoder();
    }
}

