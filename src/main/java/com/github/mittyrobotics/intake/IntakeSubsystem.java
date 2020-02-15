package com.github.mittyrobotics.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private WPI_TalonSRX intakeWheel;
    //ballSensor may not exist... Intake could be running forever
    //private DigitalInput ballSensor;
    private DoubleSolenoid extendIntake;
    private static IntakeSubsystem instance;
    private boolean isExtended;
    public static IntakeSubsystem getInstance(){
        if(instance == null){
            instance = new IntakeSubsystem();
        }
        return instance;
    }
    private IntakeSubsystem(){
        super();
        setName("Intake");
    }

    public void initHardware(){

        intakeWheel = new WPI_TalonSRX(Constants.Talon2ID);
        extendIntake = new DoubleSolenoid(Constants.solenoidForwqrdChannel, Constants.solenoidReverseChallenge);

    }
    public void intakeBall(double speed){
        if(Conveyor.getInstance().getTotalBallCount() < 5){
            intakeWheel.set(ControlMode.Velocity, speed);
        }

    }
    public void extendIntake(){
        extendIntake.set(DoubleSolenoid.Value.kForward);
        isExtended = true;

    }
    public void retractIntake(){
        extendIntake.set(DoubleSolenoid.Value.kReverse);
        isExtended = false;
    }

    public boolean isExtended() {
        return isExtended;
    }
}
