package com.github.mittyrobotics.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RampingCommand extends CommandBase {
    private double pos;

    public RampingCommand(double pos) {
        addRequirements(DriveTrainTalon.getInstance());
        this.pos = pos;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double tempPosLeft;
        double tempPosRight;
        final double RAMP_RATE = 15;

        tempPosLeft = DriveTrainTalon.getInstance().getLeftEncoder() + RAMP_RATE;
        tempPosRight = DriveTrainTalon.getInstance().getRightEncoder() + RAMP_RATE;
        if (RAMP_RATE > (pos - DriveTrainTalon.getInstance().getLeftEncoder())) {
            tempPosLeft = pos;
        }
        if (RAMP_RATE > (pos - DriveTrainTalon.getInstance().getRightEncoder())) {
            tempPosRight = pos;
        }
//        if(DriveTrainTalon.getInstance().getLeftTalon().getClosedLoopTarget() != pos * Constants.TICKS_PER_INCH && DriveTrainTalon.getInstance().getRightTalon().getClosedLoopTarget() != pos * Constants.TICKS_PER_INCH){
        DriveTrainTalon.getInstance().movePos(tempPosLeft, tempPosRight);
//        }
        System.out.println("target: " +
                DriveTrainTalon.getInstance().getLeftTalon().getClosedLoopTarget() / Constants.TICKS_PER_INCH);


    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("end");
        DriveTrainTalon.getInstance().tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return ((Math.abs(pos - DriveTrainTalon.getInstance().getLeftEncoder()) < .5) &&
                (Math.abs(pos - DriveTrainTalon.getInstance().getLeftEncoder()) < .5));
//        return false;
    }
}