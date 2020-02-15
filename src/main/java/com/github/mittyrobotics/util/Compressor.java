package com.github.mittyrobotics.util;

public class Compressor extends edu.wpi.first.wpilibj.Compressor {
    private static Compressor instance;
    private Compressor(){
        super();
    }
    public Compressor getInstance(){
        if(instance == null){
            instance = new Compressor();
        }
        return instance;
    }
}
