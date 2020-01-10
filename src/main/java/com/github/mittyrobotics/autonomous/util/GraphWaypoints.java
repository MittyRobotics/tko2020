package com.github.mittyrobotics.autonomous.util;

import com.github.mittyrobotics.autonomous.constants.AutonCoordinates;
import com.github.mittyrobotics.visualization.graphs.Graph;
import com.github.mittyrobotics.visualization.util.GraphManager;

public class GraphWaypoints {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.setVisible(true);
        for(int i = 0; i < AutonCoordinates.FIELD_WAYPOINTS.length; i++){
            graph.addDataset(GraphManager.getInstance().graphArrow(AutonCoordinates.FIELD_WAYPOINTS[i],0,0,
                    AutonCoordinates.FIELD_WAYPOINTS_NAMES[i],
                    null));
        }
    }
}
