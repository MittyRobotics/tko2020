/*
 * MIT License
 *
 * Copyright (c) 2020 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mittyrobotics.autonomous.constants;

import com.github.mittyrobotics.datatypes.positioning.Transform;

public class AutonCoordinates {

    // FIELD ORIENTATION: LANDSCAPE

    // Universal Constants
    public static final Transform ZERO = new Transform();
    public static final double HEIGHT = 323.81;
    public static final double HALF_HEIGHT = HEIGHT / 2;
    public static final double BOTTOM_EDGE_TO_SCORING_ZONE_TIP = 94.66;
    public static final double BALL_DROP_TRIANGLE_TO_HORIZONTAL = 67.25;

    // ALLIANCE VALUES
    public static final Transform LOADING_STARTING_POINT = new Transform(0, 60.75, 0);
    public static final Transform SCORING_STARTING_POINT = new Transform(0, -67.25, 0);
    public static final Transform TRENCH_STARTING_POINT = new Transform(0, -134.155, 0);

    public static final Transform A_TRENCH_FRONT_CENTER = new Transform(-86.63, -134.155);
    public static final Transform A_TRENCH_BACK_CENTER = new Transform(-302.63, -134.155);
    public static final Transform SCORING_ZONE_TIP = new Transform(90, -67.25); // unstable
    public static final Transform SCORING_TARGET = new Transform(); //TODO: Figure this out!
    // Balls (party zone)
    public static final Transform BALL_1 = new Transform(-130.25, -46.05, 0);
    public static final Transform BALL_2 = new Transform(-114.94, -39.71, 0);
    public static final Transform BALL_3 = new Transform(-107.83, -15.3, 0);
    public static final Transform BALL_4 = new Transform(-114.17, 0, 0);
    public static final Transform BALL_5 = new Transform(-120.51, 15.3, 0);

    public static final Transform SHOOTING_POINT_TRENCH = new Transform(-259.49f, -134.155f);
    public static final Transform SHOOTING_POINT_PARTY = new Transform(-121f, -44.24f);

    // OPPONENT VALUES
    public static final Transform O_TRENCH_BACK_CENTER = new Transform(-86.63, 134.155);
    public static final Transform O_TRENCH_FRONT_CENTER = new Transform(-302.63, 134.155);

    public static final Transform[] FIELD_WAYPOINTS =
            new Transform[]{ZERO, LOADING_STARTING_POINT, LOADING_STARTING_POINT,
                    SCORING_STARTING_POINT, TRENCH_STARTING_POINT, A_TRENCH_BACK_CENTER, A_TRENCH_FRONT_CENTER,
                    SCORING_ZONE_TIP,
                    BALL_1, BALL_2, BALL_3, BALL_4, BALL_5, O_TRENCH_BACK_CENTER, O_TRENCH_FRONT_CENTER};
}