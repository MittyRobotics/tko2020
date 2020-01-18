package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
    private WPI_TalonSRX talon1;
    public static int totalBallCount = 0;

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

    //public int getTotalBallCount() { return totalBallCount;}



}
