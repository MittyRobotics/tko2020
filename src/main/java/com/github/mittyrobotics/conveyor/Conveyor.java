package com.github.mittyrobotics.conveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase implements ISubsystem {
    private static Conveyor instance;
    private WPI_TalonSRX conveyorTalon;
    private int totalBallCount = 0;
    private boolean previousEntranceSwitchValue;
    private boolean previousExitSwitchValue;
    private boolean ballCountHasChanged;
    private DigitalInput entranceOpticalSwitch;
    private DigitalInput exitOpticalSwitch;
    private boolean isReverse;

    private Conveyor() {
        super();
        setName("Conveyor");
    }

    public static Conveyor getInstance() {
        if (instance == null) {
            instance = new Conveyor();
        }
        return instance;
    }

    @Override
    public void initHardware() {

        conveyorTalon = new WPI_TalonSRX(Constants.CONVEYOR_TALON_ID);
        conveyorTalon.configFactoryDefault();
        conveyorTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        entranceOpticalSwitch = new DigitalInput(Constants.ENTRANCE_OPTICAL_SWITCH);
        exitOpticalSwitch = new DigitalInput(Constants.EXIT_OPTICAL_SWITCH);

        previousEntranceSwitchValue = false;
        previousExitSwitchValue = false;
        ballCountHasChanged = false;
        totalBallCount = 0;
        isReverse = false;
    }

    @Override
    public void updateDashboard() {

    }

    @Override
    public void periodic() {
        if (!previousEntranceSwitchValue &&
                getEntranceSwitch()) { //no ball before and now ball detected before conveyor
            if (isReverse) {
                updateBallCount(-1);
            } else {
                CommandScheduler.getInstance().schedule(new MoveConveyorAddBall(2.1));
                updateBallCount(1);
            }
            ballCountHasChanged = true;
        } else {
            ballCountHasChanged = false;
        }


        if (previousExitSwitchValue && !getExitSwitch()) { //no ball before and now ball detected before conveyor
            updateBallCount(-1);
            ballCountHasChanged = true;
        } else {
            ballCountHasChanged = false;
        }

        System.out.println("Current: " + getEntranceSwitch() + " prev: " + previousEntranceSwitchValue);
        previousEntranceSwitchValue = getEntranceSwitch();
        previousExitSwitchValue = getExitSwitch();
    }

    public int getTotalBallCount() {
        return totalBallCount;
    }

    public void updateBallCount(int count) {
        totalBallCount += count;
    }

    public void resetBallCount() {
        totalBallCount = 0;
    }

    public void setConveyorSpeed(double speed) {

        conveyorTalon.set(ControlMode.PercentOutput, speed);
//        conveyorWheel2.set(ControlMode.PercentOutput, speed);

    }

    public void manualSetConveyorSpeed(double speed) {
        if (Math.abs(speed) > 0.1) {
            conveyorTalon.set(ControlMode.PercentOutput, speed);
            System.out.println("Conveyor Percent Output: " + speed);
        } else {
            conveyorTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    public boolean hasBallCountChanged() {
        return ballCountHasChanged;
    }

    public void moveConveyor(double distance) {
        conveyorTalon.set(ControlMode.Position,
                conveyorTalon.getSelectedSensorPosition() + distance * Constants.TICKS_PER_INCH);
    }

    public boolean getEntranceSwitch() {
        return !entranceOpticalSwitch.get();
    }

    public boolean getExitSwitch() {
        return !exitOpticalSwitch.get();
    }

    public double getPosition() {
        return conveyorTalon.getSelectedSensorPosition();
    }

    public void resetEncoder() {
        conveyorTalon.setSelectedSensorPosition(0);
    }

    public void setReverse(boolean value) {
        isReverse = value;
    }


}
