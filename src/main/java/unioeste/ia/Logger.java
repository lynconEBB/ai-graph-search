package unioeste.ia;

import imgui.ImGui;
import unioeste.ia.models.LogMessage;
import unioeste.ia.models.Origin;
import unioeste.ia.models.Severity;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<LogMessage> messages;
    private static Logger singletonLogger;

    private Logger() {
        messages = new ArrayList<>();
    }

    private static Logger getInstance() {
        if (singletonLogger == null)
            singletonLogger = new Logger();

        return singletonLogger;
    }

    public static void addMessage(String text, Origin origin, Severity severity) {
        LogMessage logMessage = new LogMessage(text, origin, severity);
        getInstance().messages.add(logMessage);
    }

    public void renderAll() {
        for (LogMessage message : messages) {
            ImGui.textColored(message.severity.color,message.origin.displayName + message.text);
        }
        if (ImGui.getScrollY() >= ImGui.getScrollMaxY()) {
            ImGui.setScrollHereY(1);
        }
    }

    public static void drawAllMessages() {
        getInstance().renderAll();
    }
}
