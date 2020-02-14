package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//TODO create a quick command that just runs the conveyor infinitely at a certain speed
public class ConveyorSubsystem extends SubsystemBase {
    private WPI_TalonSRX conveyorTalon;

    private int totalBallCount = 0;
    private boolean previousEntranceSwitchValue; //TODO set all of these to false in initHardware
    private boolean previousExitSwitchValue;
    private boolean ballCountHasChanged;

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

        conveyorTalon = new WPI_TalonSRX(Constants.CONVEYOR_TALON_ID);
//        conveyorWheel2 = new WPI_TalonSRX(Constants.conveyorWheel2ID);

        conveyorTalon.config_kP(0, Constants.CONVEYOR_P);
        conveyorTalon.config_kI(0, Constants.CONVEYOR_I);
        conveyorTalon.config_kD(0, Constants.CONVEYOR_D);

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

        conveyorTalon.set(ControlMode.PercentOutput, speed);
//        conveyorWheel2.set(ControlMode.PercentOutput, speed);

    }

    public boolean hasBallCountChanged() {return ballCountHasChanged;}

    public void moveConveyor(double distance) {
        conveyorTalon.set(ControlMode.Position, distance*Constants.TICKS_PER_INCH);
    }

}
