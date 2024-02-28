package unioeste.ia;

import unioeste.ia.models.Edge;
import unioeste.ia.models.Graph;
import unioeste.ia.models.Node;
import unioeste.ia.models.NodePair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Pattern START_PATTERN = Pattern.compile("ponto_inicial\\s*\\(\\s*(\\w+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_PATTERN = Pattern.compile("ponto_final\\s*\\(\\s*(\\w+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);
    private static final Pattern CAN_GO_PATTERN = Pattern.compile("pode_ir\\s*\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\s*,\\s*(\\d+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);
    private static final Pattern HEURISTIC_PATTERN = Pattern.compile("h\\s*\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\s*,\\s*(\\d+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);

    public static Graph parseFile(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return null;
        }

        String firstLine = scanner.nextLine().trim();
        Matcher firstLineMatcher = START_PATTERN.matcher(firstLine);
        if (!firstLineMatcher.find())
            return null;
        String startingNodeName = firstLineMatcher.group(1);

        String secondLine = scanner.nextLine().trim();
        Matcher secondLineMatcher = END_PATTERN.matcher(secondLine);
        if (!secondLineMatcher.find())
            return null;
        String finalNodeName = secondLineMatcher.group(1);

        Map<String, Node> nodesMap = new HashMap<>();
        Map<NodePair, Integer> heuristicsMap = new HashMap<>();

        while (scanner.hasNext())  {
            String line = scanner.nextLine().trim();
            if (line.isBlank())
                continue;

            Matcher matcher = CAN_GO_PATTERN.matcher(line);
            if (matcher.find()) {
                String firstNodeName = matcher.group(1);
                String secondNodeName = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                if (firstNodeName == null || secondNodeName == null || distance <= 0)
                    return null;

                Node firstNode = nodesMap.computeIfAbsent(firstNodeName, k -> new Node(firstNodeName));
                Node secondNode = nodesMap.computeIfAbsent(secondNodeName, k -> new Node(secondNodeName));

                firstNode.edges.add(new Edge(secondNode, distance));
                secondNode.edges.add(new Edge(firstNode,distance));

            } else {
                Matcher heuristicMatcher = HEURISTIC_PATTERN.matcher(line);
                if (!heuristicMatcher.find())
                    continue;
                String firstNodeName = heuristicMatcher.group(1);
                String secondNodeName = heuristicMatcher.group(2);
                int distance = Integer.parseInt(heuristicMatcher.group(3));

                if (firstNodeName == null || secondNodeName == null || distance <= 0)
                    return null;

                Node firstNode = nodesMap.computeIfAbsent(firstNodeName, k -> new Node(firstNodeName));
                Node secondNode = nodesMap.computeIfAbsent(secondNodeName, k -> new Node(secondNodeName));

                NodePair pair = new NodePair(firstNode, secondNode);
                if (heuristicsMap.containsKey(pair))
                    return null;

                heuristicsMap.put(pair, distance);
            }
        }

        Node originNode = nodesMap.get(startingNodeName);
        if (originNode == null)
            return null;

        Node finalNode = nodesMap.get(finalNodeName);
        if (finalNode == null)
            return null;

        return new Graph(originNode, finalNode, heuristicsMap);
    }

}
