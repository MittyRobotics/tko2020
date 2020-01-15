package com.github.mittyrobotics.autonomous.util.turret;

import com.github.mittyrobotics.visualization.graphs.Graph;
import com.github.mittyrobotics.visualization.util.XYSeriesCollectionWithRender;
import org.jfree.data.xy.XYSeries;

public class TestTrajectoryGenerator2 {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.setVisible(true);
        graph.getContentPane().setSize(800, 600);
        graph.resizeGraph(0, 600.0 / 60, 0, 800.0 / 60);

        XYSeriesCollectionWithRender dataset = new XYSeriesCollectionWithRender();

        double clCoefficient1 = .319;
        double clCoefficient2 = 0.002483;

        //Values to change
        double v = 12.53;
        double theta = 39.7;
        double angularVel = 40;
        double Cd = 0.45;
        double Cl = clCoefficient1*(1.0-Math.exp(-clCoefficient2*angularVel));

        //Constants
        double d = 7/39.3701;
        double m = 0.1417476;
        double A =  3.14*(d*d)/4.0;
        double p = 1.225;
        double g = 9.8;

        XYSeries series = calculateSeries(v,theta,m,Cd,Cl,A,p,g,"Simulated Trajectory");

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

        dataset.addSeries(series);
        dataset.addSeries(empiricalSeries1);
        dataset.addSeries(empiricalSeries2);
        graph.addDataset(dataset);
    }

    public static XYSeries calculateSeries(double v, double theta, double m, double Cd, double Cl, double A, double p,
                                           double g, String name) {

        double vx = v*Math.cos(Math.toRadians(theta));
        double vy = v*Math.sin(Math.toRadians(theta));
        double Fx,Fy;

        double x = 0;
        double y = 2e-16;

        XYSeries series = new XYSeries(name, false);
        while (y > 0) {
            double t = 0.01;

            Fx = (-1.0/2.0)*p*A* Math.sqrt(vx * vx + vy * vy) *(Cd*vx+Cl*vy);
            Fy = (-1.0/2.0)*p*A* Math.sqrt(vx * vx + vy * vy) *(Cd*vy-Cl*vx)-m*g;
            vx += Fx * t / m;
            vy += Fy * t / m;
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
