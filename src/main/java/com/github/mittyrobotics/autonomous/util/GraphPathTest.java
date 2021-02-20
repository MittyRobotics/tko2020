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

package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.datatypes.geometry.Circle;
import com.github.mittyrobotics.datatypes.positioning.Rotation;
import com.github.mittyrobotics.datatypes.positioning.Transform;
import com.github.mittyrobotics.path.generation.Path;
import com.github.mittyrobotics.path.generation.PathGenerator;
import com.github.mittyrobotics.visualization.Graph;
import com.github.mittyrobotics.visualization.GraphUtil;
import com.github.mittyrobotics.visualization.XYSeriesWithRenderer;
import org.jfree.data.xy.XYDataItem;

import java.awt.*;
import java.util.Random;

public class GraphPathTest {
    public static void main(String[] args) {
        Path path1 = new Path(PathGenerator.generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.TRENCH_STARTING_POINT, Math.PI),
                        new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, Math.PI)
                })
        );
        Path path2 = new Path(PathGenerator.generateQuinticHermiteSplinePath(
                new Transform[]{new Transform(AutonCoordinates.A_TRENCH_FRONT_CENTER, Math.PI),
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, Math.PI)}));

        Path path3 = new Path(PathGenerator.generateQuinticHermiteSplinePath(
                new Transform[]{
                        new Transform(AutonCoordinates.PICKUP_3_TRENCH, 0),
                        new Transform(AutonCoordinates.OPTIMAL_SHOOT_POSITION, Math.PI/4)
                })
        );
        Path[] paths =  new Path[]{
                path1,
                path2,
                path3,
        };



        Graph graph = new Graph();
        graph.getChart().removeLegend();
        graph.resizeGraph(-200,100,-300,0);
        for(int i = 0; i < paths.length; i++){
            Random rand = new Random();
            float hue = rand.nextFloat(); //hue
            float saturation = 1.0f; //saturation
            float brightness = 1.0f; //brightness

            Color color = Color.getHSBColor(hue, saturation, brightness);
            Path path = paths[i];
            graph.addSeries(
                    GraphUtil.populateSeries(new XYSeriesWithRenderer("Path" + i, null, true, false, null), GraphUtil.parametric(path, .01, 1)));
        }

        graph.addSeries(new XYSeriesWithRenderer("Points", null, false, true, null));
        for(int i = 0; i < AutonCoordinates.FIELD_WAYPOINTS.length; i++){
            graph.addToSeries("Points", new XYDataItem(AutonCoordinates.FIELD_WAYPOINTS[i].getX(),AutonCoordinates.FIELD_WAYPOINTS[i].getY()));
        }
    }
}