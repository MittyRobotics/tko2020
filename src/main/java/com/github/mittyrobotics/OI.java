package com.github.mittyrobotics;

import com.github.mittyrobotics.controls.controllers.XboxWheel;
import com.github.mittyrobotics.shooter.SpinFlywheel;
import com.github.mittyrobotics.turret.MagEncoderTesting;
import com.github.mittyrobotics.turret.TurretSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;

public class OI {
    private static OI instance;
    private XboxWheel xboxWheel;
    private XboxController xboxController;
    private Joystick joystick1;
    private Joystick joystick2;

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public XboxWheel getXboxWheel() {
        if (xboxWheel == null) {
            xboxWheel = new XboxWheel(Constants.XBOX_WHEEL_ID);
        }
        return xboxWheel;
    }

    public XboxController getXboxController() {
        if (xboxController == null) {
            xboxController = new XboxController(Constants.XBOX_CONTROLLER_ID);
        }
        return xboxController;
    }

    public Joystick getJoystick1() {
        if (joystick1 == null) {
            joystick1 = new Joystick(Constants.JOYSTICK_1_ID);
        }
        return joystick1;
    }

    public Joystick getJoystick2() {
        if (joystick2 == null) {
            joystick2 = new Joystick(Constants.JOYSTICK_2_ID);
        }
        return joystick2;
    }

    public void digitalInputControls() {
//        Button spinFlyWheel = new Button() {
//            @Override
//            public boolean get() {
//                return getJoystick1().getRawButton(5);
//            }
//        };
//        spinFlyWheel.whenPressed(new SpinFlywheel(Constants.ShooterSpeed, Constants.ShooterBangThreshold));
//
//        Button turretAngle = new Button() {
//            @Override
//            public boolean get() {
//                return getJoystick1().getRawButton(4);
//            }
//        };
//        turretAngle.whenPressed(new SetTurretAngle(Constants.turretAngle));

            public boolean get() {
//        Button resetEncoder = new Button() {
//            @Override
//            public boolean get() {
//                return getJoystick1().getRawButton(3);
//            }
//        };
//        resetEncoder.whenPressed(new SpinFlywheel(4100,75));


//        Button test = new Button() {
//            @Override
//            public boolean get(){
//                return getJoystick1().getRawButton(4);
//            }
//        };
//        test.whenPressed(new MagEncoderTesting(100));
    }
}