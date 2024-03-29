package unioeste.ia;

import unioeste.ia.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Pattern START_PATTERN = Pattern.compile("ponto_inicial\\s*\\(\\s*(\\w+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_PATTERN = Pattern.compile("ponto_final\\s*\\(\\s*(\\w+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);
    private static final Pattern CAN_GO_PATTERN = Pattern.compile("pode_ir\\s*\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\s*,\\s*(\\d+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);
    private static final Pattern HEURISTIC_PATTERN = Pattern.compile("h\\s*\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\s*,\\s*(\\d+)\\s*\\)\\s*\\.", Pattern.CASE_INSENSITIVE);

    public static MyGraph parseFile(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            Logger.addMessage("Could not open file!", Origin.PARSER, Severity.ERROR);
            return null;
        }

        String firstLine = scanner.nextLine().trim();
        Matcher firstLineMatcher = START_PATTERN.matcher(firstLine);
        if (!firstLineMatcher.find()) {
            Logger.addMessage("Could not extract first node!", Origin.PARSER, Severity.ERROR);
            return null;
        }
        String startingNodeName = firstLineMatcher.group(1);

        String secondLine = scanner.nextLine().trim();
        Matcher secondLineMatcher = END_PATTERN.matcher(secondLine);
        if (!secondLineMatcher.find()) {
            Logger.addMessage("Could not extract final node!", Origin.PARSER, Severity.ERROR);
            return null;
        }
        String finalNodeName = secondLineMatcher.group(1);

        Map<String, MyNode> nodesMap = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        int lineCount = 2;
        while (scanner.hasNext())  {
            lineCount++;

            String line = scanner.nextLine().trim();
            if (line.isBlank()) {
                Logger.addMessage("Blank line found on file! skipping...", Origin.PARSER, Severity.WARN);
                continue;
            }

            Matcher matcher = CAN_GO_PATTERN.matcher(line);
            if (matcher.find()) {
                String firstNodeName = matcher.group(1);
                String secondNodeName = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                if (firstNodeName == null || secondNodeName == null || distance <= 0) {
                    Logger.addMessage("Incorrect format on line " + lineCount, Origin.PARSER, Severity.ERROR);
                    return null;
                }

                MyNode firstNode = nodesMap.computeIfAbsent(firstNodeName, k -> new MyNode(firstNodeName));
                MyNode secondNode = nodesMap.computeIfAbsent(secondNodeName, k -> new MyNode(secondNodeName));

                Edge edge = new Edge(firstNode,secondNode, distance);
                edges.add(edge);
                firstNode.edges.add(edge);
                secondNode.edges.add(new Edge(secondNode,firstNode,distance));

            } else {
                Matcher heuristicMatcher = HEURISTIC_PATTERN.matcher(line);
                if (!heuristicMatcher.find()) {
                    Logger.addMessage("Duplicate heuristic found! skipping...", Origin.PARSER, Severity.WARN);
                    continue;
                }
                String firstNodeName = heuristicMatcher.group(1);
                String secondNodeName = heuristicMatcher.group(2);
                int distance = Integer.parseInt(heuristicMatcher.group(3));

                if ((!firstNodeName.equals(finalNodeName) && !secondNodeName.equals(finalNodeName)) || distance <= 0) {
                    Logger.addMessage("Heuristic not related to final node on line " + lineCount + "! skipping ", Origin.PARSER, Severity.WARN);
                    continue;
                }

                String nodeName = firstNodeName.equals(finalNodeName) ? secondNodeName : firstNodeName;

                MyNode node = nodesMap.computeIfAbsent(nodeName, k -> new MyNode(firstNodeName));
                node.distanceToEnd = distance;
            }
        }

        MyNode originNode = nodesMap.get(startingNodeName);
        if (originNode == null) {
            Logger.addMessage("Origin node not found on graph!", Origin.PARSER, Severity.ERROR);
            return null;
        }

        MyNode finalNode = nodesMap.get(finalNodeName);
        if (finalNode == null) {
            Logger.addMessage("Final node not found on graph!", Origin.PARSER, Severity.ERROR);
            return null;
        }

        finalNode.distanceToEnd = 0;

        Logger.addMessage("File parsed successfully!", Origin.PARSER, Severity.INFO);
        return new MyGraph(originNode, finalNode, edges);
    }

}
