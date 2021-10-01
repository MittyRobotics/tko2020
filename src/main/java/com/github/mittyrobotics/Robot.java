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

package com.github.mittyrobotics;

import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import com.github.mittyrobotics.conveyor.IntakePistonSubsystem;
import com.github.mittyrobotics.conveyor.IntakeSubsystem;
import com.github.mittyrobotics.drivetrain.DrivetrainSubsystem;
import com.github.mittyrobotics.shooter.ShooterSubsystem;
import com.github.mittyrobotics.shooter.TurretSubsystem;
import com.github.mittyrobotics.util.Compressor;
import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.util.SubsystemManager;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * Robot Class to run the robot code (uses timed robot)
 */
public class Robot extends TimedRobot {
    /**
     * Sets the Robot to loop at 20 ms cycle
     */

//    WPI_TalonSRX t;
    DoubleSolenoid s;

    public Robot() {
        super(0.02);
    }

    DigitalInput b;

    /**
     * Initializes all the hardware
     */
    @Override
    public void robotInit() {
        SubsystemManager.getInstance().addSubsystems(
                //ColorPistonSubsystem.getInstance(),
                ConveyorSubsystem.getInstance(),
                DrivetrainSubsystem.getInstance(),
                IntakePistonSubsystem.getInstance(),
                IntakeSubsystem.getInstance(),
                ShooterSubsystem.getInstance(),
                //SpinnerSubsystem.getInstance(),
                TurretSubsystem.getInstance()
        );
        SubsystemManager.getInstance().initHardware();
//        Gyro.getInstance().initHardware();
        Compressor.getInstance().initHardware();
    }

    /**
     * Runs Scheduler for commands and updates the dashboard and OI
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SubsystemManager.getInstance().updateDashboard();
//        OI.getInstance().updateOI();
    }

    /**
     * Brakes the drivetrain when disabling
     */
    @Override
    public void disabledInit() {
        DrivetrainSubsystem.getInstance().brake();
    }

    /**
     * Initializes and starts autonomous command
     */
    @Override
    public void autonomousInit() {

    }

    /**
     * Stops autonomous command and initializes controls
     */
    @Override
    public void teleopInit() {
        OI.getInstance().setupControls();

//        ConveyorSubsystem.getInstance().setDefaultCommand(new AutoIntakeCommand());

//        DrivetrainSubsystem.getInstance().setDefaultCommand(new TankDriveCommand());
//        Button spinWheelColor = new Button(() -> OI.getInstance().getJoystick2().getTriggerPressed());
//        spinWheelColor.whenPressed(new VisionTurretAim());
//        CommandScheduler.getInstance().schedule(new VisionTurretAim());
//        IntakePistonSubsystem.getInstance().extendPiston();
//        IntakePistonSubsystem.getInstance().extendPiston();
    }

    @Override
    public void teleopPeriodic() {
//        System.out.println(b.get());
//        if(b.get()) {
//            ConveyorSubsystem.getInstance().overrideSetMotor(0);
//        } else {
//            ConveyorSubsystem.getInstance().overrideSetMotor(-0.3);
//
//        }

//        System.out.println(ConveyorSubsystem.getInstance().isBallDetected());
//
//        double val = OI.getInstance().getJoystick1().getY();
//        IntakeSubsystem.getInstance().overrideSetMotor(val * 0.5);

//        double val2 = OI.getInstance().getJoystick2().getY();
//        ConveyorSubsystem.getInstance().overrideSetMotor(val2 * 0.5);

//        if(OI.getInstance().getJoystick1().getTriggerPressed()) {
//            CommandScheduler.getInstance().schedule(new ReverseConveyor());
//        }

//        if(OI.getInstance().getJoystick2().getTriggerPressed()) {
//            CommandScheduler.getInstance().schedule(new ShootBalls());
//        }


//        System.out.println(ConveyorSubsystem.getInstance().getBallCount());
//
        if(OI.getInstance().getJoystick1().getTriggerPressed()) {
            System.out.println( " | in");
            IntakePistonSubsystem.getInstance().retractPiston();
        } else if(OI.getInstance().getJoystick2().getTriggerPressed()) {
            System.out.println(" | out");
            IntakePistonSubsystem.getInstance().extendPiston();
        }

    }

    /**
     * Function for initializing test code
     */
    @Override
    public void testInit() {
//        IntakePistonSubsystem.getInstance().retractPiston();
//        IntakePistonSubsystem.getInstance().retractPiston();
    }

    /**
     * Function for testing code
     */
    @Override
    public void testPeriodic() {
//        t.set(-0.2);

//        DrivetrainSubsystem.getInstance().ov
//        errideSetMotor(.2, .2);

//        if(j.getTriggerPressed()) {
//            CommandScheduler.getInstance().schedule(new JerkLowerIntake());
//        }
    }

}