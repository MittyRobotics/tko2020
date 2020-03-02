package com.github.mittyrobotics.util;

import com.github.mittyrobotics.interfaces.ISubsystem;

import java.util.ArrayList;
import java.util.Arrays;

public class SubsystemManager implements ISubsystem {
    private static SubsystemManager instance;
    ArrayList<ISubsystem> subsystems;

    private SubsystemManager() {
        subsystems = new ArrayList<>();
    }

    public static SubsystemManager getInstance() {
        if (instance == null) {
            instance = new SubsystemManager();
        }
        return instance;
    }

    public void addSubsystems(ISubsystem... subsystems) {
        this.subsystems.addAll(Arrays.asList(subsystems));
    }

    @Override
    public void initHardware() {
        subsystems.forEach(ISubsystem::initHardware);
    }

    @Override
    public void updateDashboard() {
        subsystems.forEach(ISubsystem::updateDashboard);
    }
}