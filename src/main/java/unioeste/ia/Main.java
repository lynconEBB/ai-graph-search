package unioeste.ia;

import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiConfigFlags;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage.*;
import org.lwjgl.opengl.GL32;
import unioeste.ia.models.Graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.IntBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12C.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImage.stbi_load;

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

            int textureID = GL32.glGenTextures();
            GL32.glBindTexture(GL_TEXTURE_2D, textureID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);


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
                String filename = ImGuiFileDialog.getSelection().values().stream().findFirst().get();
                Graph graph = Parser.parseFile(filename);
                if (graph == null) {

                }
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