import graphviz.GraphViz;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DrawBinaryTree {
    private static DrawBinaryTree drawBinaryTree;

    private GraphViz graphViz;

    public static DrawBinaryTree getInstance() {
        if (drawBinaryTree == null) {
            synchronized (DrawBinaryTree.class) {
                if (drawBinaryTree == null) {
                    drawBinaryTree = new DrawBinaryTree();
                }
            }
        }
        return drawBinaryTree;
    }

    public void addEdge(Integer p, Integer c) {
        graphViz.addln(p + " -> " + c + ";");
    }

    public void addNull(Integer p) {
        graphViz.addln(p + " -> NULL[style=invis];");
    }

    private DrawBinaryTree() {
        graphViz = new GraphViz();
        graphViz.addln(graphViz.start_graph());
    }

    public static void release() {
        synchronized (DrawBinaryTree.class) {
            if (drawBinaryTree != null) {
                drawBinaryTree.graphViz.addln(drawBinaryTree.graphViz.end_graph());
                System.out.println(drawBinaryTree.graphViz.getDotSource());
                drawBinaryTree.graphViz.increaseDpi();   // 106 dpi

                try {
                    BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("out.gv"));
                    bufferedWriter.write(drawBinaryTree.graphViz.getDotSource());
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Runtime.getRuntime().exec("sh trans.sh");
//                    Runtime.getRuntime().exec("dot out.gv | gvpr -c -ftree.gv | neato -n -Tpng -o binaryTree.png");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                drawBinaryTree = null;
            }
        }
    }
}
