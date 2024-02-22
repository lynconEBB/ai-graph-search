package unioeste.ia;

import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiConfigFlags;

public class Main extends Application {
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

                }
                ImGui.separator();
                if (ImGui.menuItem("Exit")) {
                    System.exit(0);
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Actions")) {
                if (ImGui.menuItem("DFS")) {

                }
                if (ImGui.menuItem("BFS")) {

                }
                if (ImGui.menuItem("A Star (A*)")) {

                }
                ImGui.endMenu();
            }

            ImGui.endMainMenuBar();
        }
    }

    private void drawViewWindow() {
        ImGui.begin("Graph View");
        {
            ImGui.text("Hello, World!");
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

        if (ImGuiFileDialog.display("file-dialog", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
            if (ImGuiFileDialog.isOk() && !ImGuiFileDialog.getSelection().isEmpty()) {
                String a = ImGuiFileDialog.getSelection().values().stream().findFirst().get();
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
    }

    public static void main(String[] args) {
        launch(new Main());
    }
}