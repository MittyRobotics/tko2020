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
    private CANPIDController leftController, rightController; //TODO why are these here

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
    }

    //TODO change this to return CANPIDController with leftSpark
    public CANSparkMax getLeftSpark(){
        return leftSpark;
    }

    public CANEncoder getLeftEncoder(){
        return leftSpark.getEncoder();
    }

    //TODO change this to return CANPIDController with rightSpark
    public CANSparkMax getRightSpark(){
        return rightSpark;
    }

    public CANEncoder getRightEncoder(){
        return rightSpark.getEncoder();
    }
}

