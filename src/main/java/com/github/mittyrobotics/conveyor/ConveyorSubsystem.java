package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
    private WPI_TalonSRX conveyorWheel1;

    private int totalBallCount = 0;
    private boolean previousEntranceSwitchValue;
    private boolean previousExitSwitchValue;
    private static boolean ballCountHasChanged;

    private DigitalInput entranceOpticalSwitch;
    private DigitalInput exitOpticalSwitch;

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

        conveyorWheel1 = new WPI_TalonSRX(Constants.conveyorWheel1ID);
//        conveyorWheel2 = new WPI_TalonSRX(Constants.conveyorWheel2ID);

        conveyorWheel1.config_kP(0, Constants.CONVEYOR_P);
        conveyorWheel1.config_kI(0, Constants.CONVEYOR_I);
        conveyorWheel1.config_kD(0, Constants.CONVEYOR_D);

        entranceOpticalSwitch = new DigitalInput(com.github.mittyrobotics.Constants.ENTRANCE_OPTICAL_SWITCH);
        exitOpticalSwitch = new DigitalInput(com.github.mittyrobotics.Constants.EXIT_OPTICAL_SWITCH);
    }

    @Override
    public void periodic() {
        if (!previousEntranceSwitchValue && entranceOpticalSwitch.get()) { //no ball before and now ball detected before conveyor
            updateBallCount(1);
            ballCountHasChanged = true;
        } else {
            ballCountHasChanged = false;
        }


        if (previousExitSwitchValue && !exitOpticalSwitch.get()) { //no ball before and now ball detected before conveyor
            updateBallCount(-1);
            ballCountHasChanged = true;
        } else {
            ballCountHasChanged = false;
        }

        previousEntranceSwitchValue = entranceOpticalSwitch.get();
        previousExitSwitchValue = exitOpticalSwitch.get();
    }

    public int getTotalBallCount() {
        return totalBallCount;
    }
    public void updateBallCount(int count){
        totalBallCount += count;
    }
    public void resetBallCount() {
        totalBallCount = 0;
    }
    public void setConveyorSpeed (double speed) {

        conveyorWheel1.set(ControlMode.PercentOutput, speed);
//        conveyorWheel2.set(ControlMode.PercentOutput, speed);

    }

    public static boolean hasBallCountChanged() {return ballCountHasChanged;}

    public void moveConveyor(double distance) {
        conveyorWheel1.set(ControlMode.Position, distance*Constants.TICKS_PER_INCH);
    }

}
