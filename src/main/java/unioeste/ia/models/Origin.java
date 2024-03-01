package unioeste.ia.models;

public enum Origin {
    ASTAR_SOLVER("[A Start Solver] "),
    DFS_SOLVER("[DFS Solver] "),
    BFS_SOLVER("[BFS Solver] "),
    PARSER("[PARSER] "),
    GRAPH_RENDERER("[Graph Renderer] "),
    DIJKSTRA_SOLVER("[Dijkstra Solver] ");

    public String displayName;

    Origin(String displayName) {
        this.displayName = displayName;
    }
}
