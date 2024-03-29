package unioeste.ia.models;

public interface Solver {
    void next();
    void solve();
    MyNode getCurrentNode();
    Status getStatus();
}
