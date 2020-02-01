package com.github.mittyrobotics.drive;

import com.github.mittyrobotics.util.OI;
import com.github.mittyrobotics.controls.TKODifferentialDrive;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase {
    CANSparkMax left;
    CANSparkMax right;
    TKODifferentialDrive differentialDrive;

    public Drive() {
        super();
        addRequirements(DriveTrainSparks.getInstance());
    }

    @Override
    public void initialize() {
        left = DriveTrainSparks.getInstance().leftSpark1;
        right = DriveTrainSparks.getInstance().rightSpark1;

        differentialDrive = new TKODifferentialDrive(left, right);
    }


    @Override
    public void execute() {
        differentialDrive.joystickCarSteering(OI.getInstance().getXboxWheel().getX() / 3,
                OI.getInstance().getJoystick1().getY() / 3, OI.getInstance().getJoystick1().getTrigger());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
