package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.visualization.graphs.Graph;
import com.github.mittyrobotics.visualization.util.XYSeriesCollectionWithRender;
import org.jfree.data.xy.XYSeries;

public class BallTrajectoryGenerator {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.getContentPane().setSize(800, 600);
        graph.resizeGraph(0, 600.0 / 60, 0, 800.0 / 60);
        XYSeriesCollectionWithRender dataset = new XYSeriesCollectionWithRender();
        dataset.addSeries(calculateTrajectory(12.8,41,0.0889,0.141748,9.8,1.225 ,"Trajectory"));
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
        dataset.addSeries(empiricalSeries1);
        dataset.addSeries(empiricalSeries2);
        graph.addDataset(dataset);
    }

    /**
     * implementation of http://www.physics.usyd.edu.au/~cross/TRAJECTORIES/42.%20Ball%20Trajectories.pdf
     *
     * @param v initial ball velocity m/s
     * @param angle initial ball angle degrees
     * @param radius ball radius m
     * @param mass ball mass kg
     * @param gravity gravity m/s (positive)
     * @param airDensity air density kg/m^3
     * @param name name of {@link XYSeries}
     * @return
     */

    public static XYSeries calculateTrajectory(double v, double angle, double radius, double mass,
                                               double gravity, double airDensity, String name) {
        XYSeries series = new XYSeries(name, false);

        double vSpin = radius * v/radius;

        double Cd = 0.45;
        double             Cl = 1 / (2 + (v / vSpin));

        double k = (airDensity * 3.14 * (radius * radius)) / 2;

        double vx = Math.cos(Math.toRadians(angle)) * v;
        double vy = Math.sin(Math.toRadians(angle)) * v;

        double x = 0;
        double y = 0.0001;

        double Fx;
        double Fy;

        while(y > 0){
            double t = 0.001;

            v = Math.sqrt(vx*vx+vy*vy);
            
            Fx = -k*v*(Cd*vx+Cl*vy);
            Fy = k*v*(Cl*vx-Cd*vy)-gravity*mass;
            vx += Fx * t / mass;
            vy += Fy * t / mass;
            x += vx * t;
            y += vy * t;

            series.add(x, y);
        }

        return series;
    }

    public static XYSeries graphCubicFunction(double a, double b, double c, double d, String name) {
        XYSeries series = new XYSeries(name, false);
        double x = -0.1;
        double y = 2e-16;
        while (y > 0) {
            x -= 0.1;
            y = (a * (x * x * x) + b * (x * x) + c * x + d);
            series.add(-x * 0.3048, y * 0.3048);
        }
        return series;
    }
}
