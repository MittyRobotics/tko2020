package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.climber.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANPIDController;

public class Winch extends SubsystemBase {

    private static Winch ourInstance = new Winch();

    private CANSparkMax leftSpark;
    private CANSparkMax rightSpark;
    private CANPIDController leftController, rightController;

    public Winch() {
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

        leftController = new CANPIDController(leftSpark);       //TODO move to a command
        leftController.setP(0.5);
        leftController.setI(0);
        leftController.setD(0);
        leftController.setOutputRange(-.5, .5);

        rightController = new CANPIDController(leftSpark);      //TODO move to command
        rightController.setP(0.5);
        rightController.setI(0);
        rightController.setD(0);
        rightController.setOutputRange(-.5, .5);
    }

<<<<<<< HEAD:src/main/java/com/github/mittyrobotics/climber/Winch.java
    public void moveLeftWinch(double position) {        //TODO move this to a command
=======
    public void moveLeftWinch(double position) {

<<<<<<< HEAD:src/main/java/com/github/mittyrobotics/climber/Winch.java
>>>>>>> 7a3314c7a3f5cae8dbdbdac93da7e10bddaac41e:src/main/java/com/github/mittyrobotics/Subsystems/Winch.java
=======
>>>>>>> 7a3314c7a3f5cae8dbdbdac93da7e10bddaac41e:src/main/java/com/github/mittyrobotics/Subsystems/Winch.java
        while (Math.abs(position - leftSpark.getAlternateEncoder().getPosition()) < 1) {
            double tempPos = Math.min(position - leftSpark.getAlternateEncoder().getPosition(),
                    leftSpark.getAlternateEncoder().getPosition() + 10);
            leftController.setReference(tempPos, ControlType.kPosition);
        }
    }

    public void moveRightWinch(double position) {       //TODO move this to a command
        while (Math.abs(position - rightSpark.getAlternateEncoder().getPosition()) < 1) {
            double tempPos = Math.min(position - rightSpark.getAlternateEncoder().getPosition(),
                    rightSpark.getAlternateEncoder().getPosition() + 10);
            rightController.setReference(tempPos, ControlType.kPosition);
        }
    }

}
