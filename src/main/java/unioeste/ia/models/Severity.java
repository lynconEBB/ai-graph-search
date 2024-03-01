package unioeste.ia.models;

import imgui.ImGui;

public enum Severity {
    INFO(ImGui.colorConvertFloat4ToU32(0f,1f,1,1)),
    WARN(ImGui.colorConvertFloat4ToU32(1f,1f,0,1)),
    ERROR(ImGui.colorConvertFloat4ToU32(1f,0f,0,1));

   public int color;

    Severity(int color) {
        this.color = color;
    }
}
