package com.github.mittyrobotics.util;


import com.github.mittyrobotics.interfaces.IHardware;

public class Compressor extends edu.wpi.first.wpilibj.Compressor implements IHardware {
    private static Compressor instance;

    private Compressor() {
        super();
    }

    public static Compressor getInstance() {
        if (instance == null) {
            instance = new Compressor();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        start();
        setClosedLoopControl(true);
    }
}
