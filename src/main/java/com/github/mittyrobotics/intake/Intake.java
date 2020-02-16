package com.github.mittyrobotics.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.conveyor.Conveyor;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase implements ISubsystem {
    private static Intake instance;
    private WPI_TalonSRX intakeWheel;
    private DoubleSolenoid extendIntake;
    private boolean isExtended;

    private Intake() {
        super();
        setName("Intake");
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        intakeWheel = new WPI_TalonSRX(Constants.INTAKE_WHEEL_ID);
        extendIntake = new DoubleSolenoid(Constants.SOLENOID_FORWQRD_CHANNEL, Constants.SOLENOID_REVERSE_CHALLENGE);
        isExtended = false;
    }

    @Override
    public void updateDashboard() {

    }

    private void moveWheel(double speed) {
        if(isExtended){
            intakeWheel.set(ControlMode.PercentOutput, speed);
        }
    }

    public void extendIntake() {
        extendIntake.set(DoubleSolenoid.Value.kForward);
        isExtended = true;
    }

    public void retractIntake() {
        extendIntake.set(DoubleSolenoid.Value.kReverse);
        stopWheel();
        isExtended = false;
    }
    public void intakeBall(){
        if(Conveyor.getInstance().getTotalBallCount() < 4){
            moveWheel(Constants.INTAKE_SPEED_FAST);
        } else {
            moveWheel(Constants.INTAKE_SPEED_SLOW);
        }
    }
    public void outtakeBall(){
        moveWheel(Constants.OUTTAKE_SPEED);
    }

    public void stopWheel(){
        moveWheel(0);
    }
}