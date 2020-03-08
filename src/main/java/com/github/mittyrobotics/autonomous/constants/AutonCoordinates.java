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

import com.github.mittyrobotics.datatypes.positioning.Position;

public class AutonCoordinates {

    // FIELD ORIENTATION: LANDSCAPE

    // Universal Constants
    public static final double HEIGHT = 323.81;
    public static final double HALF_HEIGHT = HEIGHT / 2;
    public static final double BOTTOM_EDGE_TO_SCORING_ZONE_TIP = 94.66;
    public static final double BALL_DROP_TRIANGLE_TO_HORIZONTAL = 67.25;
    public static final Position ZERO = new Position();

    // ALLIANCE VALUES
    public static final Position LOADING_STARTING_POINT = new Position(0, 60.75);
    public static final Position SCORING_STARTING_POINT = new Position(0, -67.25);
    public static final Position TRENCH_STARTING_POINT = new Position(0, -134.155);

    public static final Position A_TRENCH_FRONT_CENTER = new Position(-86.63, -134.155);
    public static final Position A_TRENCH_BACK_CENTER = new Position(-302.63, -134.155);
    public static final Position SCORING_ZONE_TIP = new Position(90, -67.25); // unstable
    public static final Position SCORING_TARGET = new Position(120f, -67.25);

    public static final Position OPTIMAL_SHOOT_POSITION = new Position(-60.0, -67.25);

    // Balls (party zone)
    public static final Position BALL_1 = new Position(-130.25, -46.05);
    public static final Position BALL_2 = new Position(-114.94, -39.71);
    public static final Position BALL_3 = new Position(-107.83, -15.3);
    public static final Position BALL_4 = new Position(-114.17, 0);
    public static final Position BALL_5 = new Position(-120.51, 15.3);

    public static final Position BALL_BACK_TRENCH_1 = new Position(-257.49, -125.155);
    public static final Position BALL_BACK_TRENCH_2 = new Position(-257.49, -142.155);


    public static final Position SHOOTING_POINT_TRENCH = new Position(-259.49f, -134.155f);
    public static final Position SHOOTING_POINT_PARTY = new Position(-121f, -44.24f);

    public static final Position PICKUP_LAST_TRENCH = new Position(-259.49, -134.155);
    public static final Position PICKUP_3_TRENCH = new Position(-200, -134.155); //TODO: Find distance
    public static final Position PICKUP_2_PARTY = new Position(-121.0, -44.24);

    //Opponent coordinates
    public static final Position O_TRENCH_BACK_CENTER = new Position(-86.63, 134.155);
    public static final Position O_TRENCH_FRONT_CENTER = new Position(-302.63, 134.155);

    public static final Position[] FIELD_WAYPOINTS =
            new Position[]{ZERO, LOADING_STARTING_POINT, LOADING_STARTING_POINT,
                    SCORING_STARTING_POINT, TRENCH_STARTING_POINT, A_TRENCH_BACK_CENTER, A_TRENCH_FRONT_CENTER,
                    SCORING_ZONE_TIP,
                    BALL_1, BALL_2, BALL_3, BALL_4, BALL_5, O_TRENCH_BACK_CENTER, O_TRENCH_FRONT_CENTER};


}