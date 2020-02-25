package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.IntakeConstants;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase implements ISubsystem {
    private static IntakeSubsystem instance;
    private WPI_TalonSRX intakeWheel;

    private IntakeSubsystem() {
        super();
        setName("Intake");
    }

    public static IntakeSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        intakeWheel = new WPI_TalonSRX(IntakeConstants.INTAKE_WHEEL_ID);
        intakeWheel.setInverted(IntakeConstants.INTAKE_WHEEL_INVERSION);
        intakeWheel.configFactoryDefault();
    }

    @Override
    public void updateDashboard() {

    }

    private void moveWheel(double speed) {
        if (IntakePiston.getInstance().isExtended()) {
            intakeWheel.set(ControlMode.PercentOutput, speed);
        }
    }

    public void intakeBall() {
        if (ConveyorSubsystem.getInstance().getTotalBallCount() < 4) {
            moveWheel(IntakeConstants.INTAKE_SPEED_FAST);
        } else {
            moveWheel(IntakeConstants.INTAKE_SPEED_SLOW);
        }
    }

    public void outtakeBall() {
        moveWheel(IntakeConstants.OUTTAKE_SPEED);
    }

    public void stopWheel() {
        moveWheel(0);
    }

}