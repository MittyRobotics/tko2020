package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.datatypes.positioning.Position;

public class TestAngleTo {
    public static void main(String[] args) {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(10, 5);
        System.out.println(pos2.angleTo(pos1));
    }
}
