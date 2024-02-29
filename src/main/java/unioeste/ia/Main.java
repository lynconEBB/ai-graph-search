package unioeste.ia;

import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiConfigFlags;
import org.lwjgl.glfw.GLFW;
import unioeste.ia.models.GraphRenderer;
import unioeste.ia.models.MyGraph;
import unioeste.ia.models.Solver;
import unioeste.ia.solvers.AStarSolver;
import unioeste.ia.solvers.DepthSolver;

public class Main extends Application {
    private float zoom = 1;
    private boolean isSolving = false;

    private MyGraph loadedGraph;
    private GraphRenderer graphRenderer = new GraphRenderer();
    private Solver solver;

    private static ImGuiFileDialogPaneFun fileDialogCallback = new ImGuiFileDialogPaneFun() {
        @Override
        public void paneFun(String s, long l, boolean b) {

        }
    };

    private void drawMainMenu() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                if (ImGui.menuItem("Open")) {
                    ImGuiFileDialog.openModal("file-dialog", "Choose graph file descriptior", ".*", ".", fileDialogCallback, 250, 1, 42, ImGuiFileDialogFlags.None);
                }
                if (ImGui.menuItem("Close")) {
                    loadedGraph = null;
                }
                ImGui.separator();
                if (ImGui.menuItem("Exit")) {
                    System.exit(0);
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Actions")) {
                if (ImGui.menuItem("DFS")) {
                    solver = new DepthSolver();
                }
                if (ImGui.menuItem("BFS")) {

                }
                if (ImGui.menuItem("A Star (A*)")) {
                    solver = new AStarSolver();
                }

                ImGui.separator();
                ImGui.beginDisabled();
                if (ImGui.menuItem("Next")) {
                }
                if (ImGui.menuItem("Finish")) {
                }

                ImGui.endDisabled();

                ImGui.endMenu();
            }

            ImGui.endMainMenuBar();
        }
    }

    private void drawViewWindow() {
        ImGui.begin("MyGraph View");
        {
            if (loadedGraph != null) {
                float imageHeight = ImGui.getWindowHeight() - 50 * zoom;
                float imageWidth = imageHeight * graphRenderer.getAspectRatio();
                ImGui.setCursorPosX((ImGui.getWindowWidth() / 2) - (imageWidth / 2));
                ImGui.image(graphRenderer.getTextureID(),imageHeight * graphRenderer.getAspectRatio(), imageHeight);
            } else {
                ImGui.text("No graph loaded");
            }
        }
        ImGui.end();
    }

    private void drawLogWindow() {
        ImGui.begin("Log");
        {

        }
        ImGui.end();
    }

    @Override
    public void process() {
        ImGui.dockSpaceOverViewport(ImGui.getMainViewport());

        drawMainMenu();
        drawViewWindow();
        drawLogWindow();

        if (ImGuiFileDialog.display("file-dialog", ImGuiFileDialogFlags.None, 200, 400, 1200, 600)) {
            if (ImGuiFileDialog.isOk() && !ImGuiFileDialog.getSelection().isEmpty()) {
                String filename = ImGuiFileDialog.getSelection().values().stream().findFirst().get();
                loadedGraph = Parser.parseFile(filename);
                if (loadedGraph !=null) {
                    graphRenderer.render(loadedGraph);
                }
                isSolving = false;
                zoom = 1;
            }
            ImGuiFileDialog.close();
        }

    }

    @Override
    protected void configure(Configuration config) {
        config.setTitle("AI Search algorithms");
    }

    @Override
    protected void initImGui(Configuration config) {
        super.initImGui(config);
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);

        GLFW.glfwSetScrollCallback(handle, (long handle, double x, double y) -> {
            int controlActive = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_CONTROL);
            if (controlActive > 0) {
                zoom += (float) (-0.5 * y);
            }
        });
        graphRenderer.init();
    }

    public static void main(String[] args) {
        launch(new Main());
    }
}