package unioeste.ia.models;

import guru.nidi.graphviz.attribute.EndLabel;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Factory.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class GraphRenderer {
    private int textureID;
    private int imageWidth;
    private int imageHeight;
    private static final File DEFAULT_FILE = new File("graph.png");

    public void render(MyGraph graph) {
        Map<String, Node> viewNodes = new HashMap<>();

        for (Edge edge : graph.edges) {
            Node srcNode = viewNodes.computeIfAbsent(edge.src.name, n -> {
                    Integer i = graph.heuristicMap.getOrDefault(new NodePair(graph.destination, edge.src),0);
                    return node(edge.src.name).with(Label.html("<b>" + edge.src.name + "</b><br/>" + i));
                }
            );
            Node destNode = viewNodes.computeIfAbsent(edge.dest.name, n ->  {
                    Integer i = graph.heuristicMap.getOrDefault(new NodePair(graph.destination, edge.dest),0);
                    return node(edge.dest.name).with(Label.html("<b>" + edge.dest.name + "</b><br/>" + i));
                }
            );

            srcNode = srcNode.link(
                    to(destNode).with(
                            Label.of(String.valueOf(edge.distance))
                    )
            );
            viewNodes.put(srcNode.name().toString(), srcNode);
        }

        Graph viewGraph = graph("Graph")
                .nodeAttr().with(Font.name("Arial"))
                .linkAttr().with(Font.name("Arial"))
                .with(new ArrayList<>(viewNodes.values()));
        try {
            Graphviz.fromGraph(viewGraph).height(1200).render(Format.PNG).toFile(DEFAULT_FILE);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            ByteBuffer byteBuffer = stbi_load(DEFAULT_FILE.getAbsolutePath(), w, h, comp, 3);

            GL32.glPixelStorei(GL32.GL_UNPACK_ALIGNMENT, 2 - (w.get(0) & 1));
            GL32.glTexImage2D(GL32.GL_TEXTURE_2D, 0, GL32.GL_RGB, w.get(0),h.get(0),0, GL32.GL_RGB, GL32.GL_UNSIGNED_BYTE, byteBuffer);

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

        //render(null);
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
