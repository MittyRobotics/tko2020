package com.github.mittyrobotics.music;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PlayOrchestra extends CommandBase {

    TKOOrchestra orchestra;
    Timer timer;
    double seconds;

    public PlayOrchestra(TKOOrchestra orchestra, double seconds) {
        super();

        this.orchestra = orchestra;
        this.timer = new Timer();
        this.seconds = seconds;
    }

    @Override
    public void initialize() {
        timer.start();
        orchestra.play();
    }

    @Override
    public void execute() {
        if(timer.get() >= seconds) {
            orchestra.stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
