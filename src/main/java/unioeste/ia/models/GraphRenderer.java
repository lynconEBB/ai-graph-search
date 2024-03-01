package unioeste.ia.models;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class GraphRenderer {
    private int textureID;
    private int imageWidth;
    private int imageHeight;
    private static final File DEFAULT_FILE = new File("graph.png");
    private MutableGraph currentGraph;
    Map<String, MutableNode> viewNodes;

    public void update(Solver solver, MyGraph updateGraph) {
        if (solver.isSolved()) {
            MyNode current = updateGraph.destination;

            while (!current.equals(updateGraph.origin)) {
                MutableNode fromNode = viewNodes.get(current.name);
                MutableNode toNode = viewNodes.get(current.previousNode.name);
                fromNode.attrs().add(Color.BLUE);
                fromNode.attrs().add(Color.BLUE.font());

                Link link = currentGraph
                        .edges()
                        .stream()
                        .filter(l ->
                            (l.to().name().value().equals(fromNode.name().value()) && l.from().name().value().equals(toNode.name().value()))
                            || (l.to().name().value().equals(toNode.name().value()) && l.from().name().value().equals(fromNode.name().value()))
                        ).findFirst().get();

                link.attrs().add(Color.BLUE);
                link.attrs().add(Style.BOLD);
                link.attrs().add(Color.BLUE.font());

                current = current.previousNode;
            }

            MutableNode node = viewNodes.get(current.name);
            node.attrs().add(Color.BLUE);
            node.attrs().add(Color.BLUE.font());

        } else {
            currentGraph.nodes().forEach(n -> {
                if (solver.getCurrentNode() != null && solver.getCurrentNode().name.equals(n.name().value())) {
                    n.attrs().add(Color.RED);
                    n.attrs().add(Color.RED.font());
                } else {
                    if (n.attrs().get("fontcolor") != null)
                        n.attrs().add(Color.BLACK.font());

                    if (n.attrs().get("color") != null)
                        n.attrs().add(Color.BLACK);
                }
            });
        }

        renderToTexture();
    }

    public void render(MyGraph graph) {
        viewNodes = new HashMap<>();

        currentGraph = mutGraph("Graph").use((gr, ctx) -> {
            nodeAttrs().add(Font.name("Arial"));
            linkAttrs().add(Font.name("Arial"));
            for (Edge edge : graph.edges) {
                MutableNode srcNode = viewNodes.computeIfAbsent(edge.src.name, n ->
                        mutNode(edge.src.name)
                                .add(Label.html("<b>" + edge.src.name + "</b><br/>" + edge.src.distanceToEnd))
                );
                MutableNode destNode = viewNodes.computeIfAbsent(edge.dest.name, n ->
                        mutNode(edge.dest.name)
                                .add(Label.html("<b>" + edge.dest.name + "</b><br/>" + edge.dest.distanceToEnd))
                );

                srcNode = srcNode.addLink(
                        to(destNode).with(
                                Label.of(String.valueOf(edge.distance))
                        )
                );
                viewNodes.put(srcNode.name().toString(), srcNode);
            }
        });

        renderToTexture();
    }

    private void renderToTexture() {
        try {
            Graphviz.fromGraph(currentGraph).height(1200).render(Format.PNG).toFile(DEFAULT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            ByteBuffer byteBuffer = stbi_load(DEFAULT_FILE.getAbsolutePath(), w, h, comp, 3);

            GL32.glPixelStorei(GL32.GL_UNPACK_ALIGNMENT, 2 - (w.get(0) & 1));
            GL32.glTexImage2D(GL32.GL_TEXTURE_2D, 0, GL32.GL_RGB, w.get(0), h.get(0), 0, GL32.GL_RGB, GL32.GL_UNSIGNED_BYTE, byteBuffer);

            imageWidth = w.get(0);
            imageHeight = h.get(0);
        }
    }

    public void init() {
        textureID = GL32.glGenTextures();
        GL32.glBindTexture(GL32.GL_TEXTURE_2D, textureID);
        GL32.glTexParameteri(GL32.GL_TEXTURE_2D, GL32.GL_TEXTURE_MAG_FILTER, GL32.GL_LINEAR);
        GL32.glTexParameteri(GL32.GL_TEXTURE_2D, GL32.GL_TEXTURE_MIN_FILTER, GL32.GL_LINEAR);
        GL32.glTexParameteri(GL32.GL_TEXTURE_2D, GL32.GL_TEXTURE_WRAP_S, GL32.GL_CLAMP_TO_EDGE);
        GL32.glTexParameteri(GL32.GL_TEXTURE_2D, GL32.GL_TEXTURE_WRAP_T, GL32.GL_CLAMP_TO_EDGE);
    }

    public int getTextureID() {
        return textureID;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public float getAspectRatio() {
        return (float) imageWidth / imageHeight;
    }
}
