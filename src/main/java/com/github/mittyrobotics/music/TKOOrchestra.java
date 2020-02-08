package com.github.mittyrobotics.music;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import java.util.Arrays;

public class TKOOrchestra extends Orchestra {

    public TKOOrchestra(WPI_TalonFX... falcons) {
        super(Arrays.asList(falcons));
    }

}
