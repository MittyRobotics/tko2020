package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.interfaces.ISubsystem;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Winch extends SubsystemBase implements ISubsystem {
    private static Winch instance;

    private CANSparkMax leftWinch, rightWinch;

    private CANEncoder leftEncoder, rightEncoder;

    private CANPIDController leftPIDController, rightPIDController;

    private Winch() {
        super();
    }

    public static Winch getInstance() {
        if (instance == null) {
            instance = new Winch();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        leftWinch = new CANSparkMax(Constants.LEFT_WINCH_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightWinch = new CANSparkMax(Constants.RIGHT_WINCH_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftWinch.restoreFactoryDefaults();
        rightWinch.restoreFactoryDefaults();
        leftWinch.setInverted(Constants.LEFT_WINCH_INVERSION);
        rightWinch.setInverted(Constants.RIGHT_WINCH_INVERSION);
        this.leftEncoder = leftWinch.getEncoder();
        this.rightEncoder = rightWinch.getEncoder();
        this.leftPIDController = leftWinch.getPIDController();
        this.rightPIDController = rightWinch.getPIDController();
    }

    @Override
    public void updateDashboard() {

    }

    public double getEncoderTicks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            return leftEncoder.getPosition();
        } else {
            return rightEncoder.getPosition();
        }
    }


    public void setSpeed(double speed, RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftWinch.set(speed);
        } else {
            rightWinch.set(speed);
        }
    }
}