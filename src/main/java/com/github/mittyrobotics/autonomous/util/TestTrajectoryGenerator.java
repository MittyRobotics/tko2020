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

import com.github.mittyrobotics.datatypes.positioning.Position;
import com.github.mittyrobotics.visualization.graphs.Graph;
import com.github.mittyrobotics.visualization.util.XYSeriesCollectionWithRender;
import org.jfree.data.xy.XYSeries;

public class TestTrajectoryGenerator {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.setVisible(true);
        graph.getContentPane().setSize(800, 600);
        graph.resizeGraph(0, 600.0 / 60, 0, 800.0 / 60);

        double v = 12.53;
        double a = 39.7;

        double b = 0.001;
        double Cl = 0.13;

        XYSeriesCollectionWithRender dataset = new XYSeriesCollectionWithRender();
        XYSeries series = calculateSeries(v, a, 0.0, 0.0, 0.1417476, 0.024829, 1.225, 9.8, "Perfect trajectory");
        XYSeries series1 = calculateSeries(v, a, b, 0.0, 0.1417476, 0.024829, 1.225, 9.8, "Trajectory w/ " +
                "drag");
        XYSeries series2 =
                calculateSeries(v, a, b, Cl, 0.1417476, 0.024829, 1.225, 9.8, "Trajectory w/ drag and " +
                        "magnus");


        XYSeries empiricalSeries1 = graphCubicFunction(
                .0009271,
                .01013,
                -.8279,
                -.08069,
                "Empirical Series 1");

        XYSeries empiricalSeries2 = graphCubicFunction(
                .002098,
                .007780,
                -.9177,
                -.1390,
                "Empirical Series 2");
        //dataset.addSeries(series);
        //dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(empiricalSeries1);
        dataset.addSeries(empiricalSeries2);
        graph.addDataset(dataset);
    }

    public static XYSeries calculateSeries(double v, double a, double b, double Cl, double m, double A, double p,
                                           double g, String name) {
        double vx = v * Math.cos(Math.toRadians(a));
        double vy = v * Math.sin(Math.toRadians(a));
        double Fx =
                -b * vx * Math.sqrt(vx * vx + vy * vy) - (1 / (2 * m)) * p * A * Cl * vx * Math.sqrt(vx * vx + vy * vy);
        double Fy = -m * g - b * vy * Math.sqrt(vx * vx + vy * vy) -
                (1 / 2.0) * p * A * Cl * vy * Math.sqrt(vx * vx + vy * vy);
        double x = 0;
        double y = 2e-16;
        XYSeries series = new XYSeries(name, false);
        while (y > 0) {
            double t = 0.01;
            series.add(x, y);
            x += vx * t;
            y += vy * t;
            vx += Fx * t / m;
            vy += Fy * t / m;
            Fx = -b * vx * Math.sqrt(vx * vx + vy * vy) -
                    (1 / (2 * m)) * p * A * Cl * vx * Math.sqrt(vx * vx + vy * vy);
            Fy = -m * g - b * vy * Math.sqrt(vx * vx + vy * vy) -
                    (1 / 2.0) * p * A * Cl * vy * Math.sqrt(vx * vx + vy * vy);
        }
        return series;
    }

    public static XYSeries graphCubicFunction(double a, double b, double c, double d, String name){
        XYSeries series = new XYSeries(name,false);
        double x = -0.1;
        double y = 2e-16;
        while(y > 0){
            x -= 0.1;
            y = (a*(x*x*x)+b*(x*x)+c*x+d);
            series.add(-x*0.3048,y*0.3048);
        }
        return series;
    }
}
