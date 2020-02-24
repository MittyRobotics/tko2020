package com.github.mittyrobotics.conveyor;

import com.github.mittyrobotics.buffer.Buffer;
import com.github.mittyrobotics.buffer.Constants;
import com.github.mittyrobotics.intake.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RemoveBall extends CommandBase {

    private double d, bufferInitPos;
    private boolean isDone;

    public RemoveBall(double d) {
        super();
        this.d = d;
        addRequirements(Conveyor.getInstance(), Buffer.getInstance(), Intake.getInstance());
    }

    @Override
    public void initialize() {
        bufferInitPos = Buffer.getInstance().getBufferPosition();
        isDone = false;
        d *= Constants.TICKS_PER_ROTATION;
        System.out.println("INIT");

    }

    @Override
    public void execute() {
        double bufferDiff = Buffer.getInstance().getBufferPosition() - bufferInitPos;
        if (bufferDiff < d) {
            Buffer.getInstance().manualBufferSpeed(0.4);
            Conveyor.getInstance().manualSetConveyorSpeed(1);
            Intake.getInstance().intakeBall();
        } else {
            isDone = true;
        }
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("END");
        Conveyor.getInstance().setConveyorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

}
