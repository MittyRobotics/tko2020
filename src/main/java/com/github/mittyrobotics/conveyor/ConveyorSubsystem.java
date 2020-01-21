package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
    private WPI_TalonSRX talon1;

    private static int totalBallCount = 0;

    private static ConveyorSubsystem instance;
    public static ConveyorSubsystem getInstance(){
        if(instance == null){
            instance = new ConveyorSubsystem();
        }
        return instance;
    }
    private ConveyorSubsystem(){
        super();
        setName("Conveyor");
    }

    public void initHardware(){
        talon1 = new WPI_TalonSRX(Constants.TalonID);
    }

    public int getTotalBallCount() {
        return totalBallCount;
    }
    public void updateBallCount(int count){
        totalBallCount += count;
    }
    public void resetBallCount() { totalBallCount = 0; }
    public void setConveyorSpeed (double speed) { //TODO maybe put encoder to control distance traveled? (Unsure)
        talon1.set(ControlMode.PercentOutput, speed);
    }

}
