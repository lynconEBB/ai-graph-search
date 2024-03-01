package unioeste.ia.models;

public class LogMessage {
    public Severity severity;
    public Origin origin;

    public String text;

    public LogMessage(String text, Origin origin, Severity severity) {
        this.severity = severity;
        this.origin = origin;
        this.text = text;
    }
}
