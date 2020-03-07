/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.commands.AltIndexerCommand;
import com.github.mittyrobotics.commands.FourBallConveyorIndexCommand;
import com.github.mittyrobotics.commands.IncreaseConveyorSetpoint;
import com.github.mittyrobotics.constants.ConveyorConstants;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class ConveyorSubsystem extends SubsystemBase implements IMotorSubsystem {
    private static ConveyorSubsystem instance;
    private WPI_TalonSRX conveyorTalon;
    private int totalBallCount;
    private boolean previousEntranceSwitchValue;
    private DigitalInput entranceOpticalSwitch;
    private int count;
    private boolean shouldIntake;
    private double currentConveyorSetpoint;

    private ConveyorSubsystem() {
        super();
        setName("Conveyor");
    }

    public static ConveyorSubsystem getInstance() {
        if (instance == null) {
            instance = new ConveyorSubsystem();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        conveyorTalon = new WPI_TalonSRX(ConveyorConstants.CONVEYOR_TALON_ID);
        conveyorTalon.configFactoryDefault();
        conveyorTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        entranceOpticalSwitch = new DigitalInput(ConveyorConstants.ENTRANCE_OPTICAL_SWITCH);
        conveyorTalon.setNeutralMode(NeutralMode.Brake);
        previousEntranceSwitchValue = false;
        totalBallCount = 0;
        count = 0;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Total Ball Count", getTotalBallCount());
        SmartDashboard.putBoolean("Is Ball Detected", getSwitch());
        SmartDashboard.putBoolean("Prev switch value", previousEntranceSwitchValue);
    }

    @Override
    public void periodic() {
//        if(DriverStation.getInstance().isOperatorControl()){
//            boolean isReverse = conveyorTalon.getMotorOutputPercent() < 0;
//            shouldIntake = IntakeSubsystem.getInstance().getVelocity() > 0;
//            if (
////        IntakePistonSubsystem.getInstance().isPistonExtended()
////                &&
////            ShooterSubsystem.getInstance().getVelocity() == 0
////                &&
////                    OI.getInstance().getXboxController().getTriggerAxis(GenericHID.Hand.kRight) < 0.5
////                            &&
////                            OI.getInstance().getXboxController().getBumper(GenericHID.Hand.kLeft)
////                &&
//                shouldIntake
////                    true
//            ) {
////                indexSensor(isReverse);
//            }
//            previousEntranceSwitchValue = getSwitch();
//        }

    }

    public void periodic2() {
//        boolean isReverse = conveyorTalon.getMotorOutputPercent() < 0;
//        shouldIntake = IntakeSubsystem.getInstance().getVelocity() > 0;
//        if (true) {
//            indexSensor(isReverse);
//        }
//        previousEntranceSwitchValue = getSwitch();
    }

    public void indexPosition(boolean isReverse) {
        if (getSwitch() && !isReverse) {
            count++;
        } else {
            count = 0;
        }
        if (count == 3) {
            totalBallCount++;
        }
        if (!isReverse && count == 3 && totalBallCount < 5) {
            if (totalBallCount == 4) {
                CommandScheduler.getInstance().schedule(new FourBallConveyorIndexCommand(4));
            } else {
                CommandScheduler.getInstance().schedule(new FourBallConveyorIndexCommand(11));
            }
        }

        if (isReverse && !getSwitch() && previousEntranceSwitchValue) {
            updateBallCount(-1);
        }
    }

    public void indexSensor(boolean isReverse) {
        if (getSwitch() && !previousEntranceSwitchValue && !isReverse
                && getTotalBallCount() < 4
        ) {
            System.out.println("HERE");
            CommandScheduler.getInstance().schedule(new AltIndexerCommand());
            updateBallCount(1);
        } else if (isReverse && !getSwitch() && previousEntranceSwitchValue) {
            updateBallCount(-1);
        }
    }

    public void index3(boolean isReverse) {
        if (getSwitch() && !previousEntranceSwitchValue && !isReverse) {
            CommandScheduler.getInstance().schedule(new IncreaseConveyorSetpoint());
            updateBallCount(1);
        }
        if (isReverse && !getSwitch() && previousEntranceSwitchValue) {
            updateBallCount(-1);
        }
    }

    public int getTotalBallCount() {
        return totalBallCount;
    }

    public void updateBallCount(int count) {
        totalBallCount = MathUtil.clamp(totalBallCount + count, 0, 5);
        if (!IntakePistonSubsystem.getInstance().isPistonExtended()) {
            resetBallCount();
        }
    }

    public void resetBallCount() {
        totalBallCount = 0;
    }

    public void indexBall() {
        if (currentConveyorSetpoint > getPosition()) {
            setMotor(1);
        } else {
            stopMotor();
        }
    }

    public void increaseSetpoint(double value) {
        currentConveyorSetpoint += value;
    }

    public void increaseSetpoint() {
        increaseSetpoint(10);
    }

    public void manualSetConveyorSpeed(double speed) { //TODO remove when done testing
        if (Math.abs(speed) > 0.2) {
            setMotor(speed);
        } else {
            stopMotor();
        }
    }

    @Override
    public boolean getSwitch() {
        return !entranceOpticalSwitch.get();
    }

    @Override
    public void overrideSetMotor(double percent) {
        conveyorTalon.set(percent);
    }

    @Override
    public double getPosition() {
        return conveyorTalon.getSelectedSensorPosition();
    }

    @Override
    public void resetEncoder() {
        conveyorTalon.setSelectedSensorPosition(0);
    }

    public void chooseMoveConveyor(){
        if(getSwitch()){
            setMotor(1);
        } else {
            setMotor(0);
        }
    }
    public void setIndexSpeed(){
        setMotor(ConveyorConstants.CONVEYOR_INDEX_SPEED);
    }

    @Override
    public double getVelocity(){
        return conveyorTalon.get();
    }
}