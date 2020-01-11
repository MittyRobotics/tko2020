package com.github.mittyrobotics.Subsystems;

import com.github.mittyrobotics.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Winch extends SubsystemBase {

    private static Winch ourInstance = new Winch();

    private CANSparkMax leftSpark;
    private CANSparkMax rightSpark;

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

    }

    

}
