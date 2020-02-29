package com.github.mittyrobotics.util;

import com.github.mittyrobotics.interfaces.ISubsystem;

import java.util.ArrayList;
import java.util.Arrays;

public class SubsystemManager implements ISubsystem{
    ArrayList<ISubsystem> subsystems;
    private SubsystemManager(){
        subsystems = new ArrayList<>();
    }

    private static SubsystemManager instance;

    public static SubsystemManager getInstance(){
        if(instance == null){
            instance = new SubsystemManager();
        }
        return instance;
    }

    public void addSubsystems(ISubsystem... subsystems){
        this.subsystems.addAll(Arrays.asList(subsystems));
    }

    @Override
    public void initHardware(){
        for(ISubsystem subsystem: subsystems){
            subsystem.initHardware();
        }
    }

    @Override
    public void updateDashboard() {
        for(ISubsystem subsystem: subsystems){
            subsystem.updateDashboard();
        }
    }
}