package proyecto_02.Structures;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import proyecto_02.Album;
import proyecto_02.Structures.SubStructure.Node;
import proyecto_02.Structures.SubStructure.NodeDouble;

/**
 *
 * @author Alexis
 */
public class DoubleList {

    public NodeDouble first;
    public NodeDouble last;
    public int size = 0;
    String graphText = "";

    public DoubleList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(Album valueToAdd) {
        NodeDouble myNode = new NodeDouble();
        myNode.setValue(valueToAdd);
        if (this.first == null) {
            first = myNode;
            first.setNext(null);
            first.setPrevious(null);
            last = first;
        } else {
            last.setNext(myNode);
            myNode.setPrevious(last);
            myNode.setNext(null);
            last = myNode;
        }
        this.size += 1;
    }

    public void printContent() {
        NodeDouble Current = first;
        if (this.size != 0) {
            while (Current != null) {
                System.out.println(Current.getValue().getName());
                Current = Current.getNext();
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void graphDoubleList() {
        this.graphText = "";
        String gText = "digraph G {\n"
                + "node [shape = rectangle];\n";
        NodeDouble current = this.first;
        String same = "";
        while (current != null) {
            same += "\"" + current.getValue().getName() + "\";";
            current = current.getNext();
        }
        gText += "{rank=same; " + same + "}\n";
        NodeDouble current2 = this.first;
        while (current2 != null) {
            if (current2.getPrevious() != null) {
                gText += "\"" + current2.getValue().getName() + "\" -> \"" + current2.getPrevious().getValue().getName() + "\" \n";
            }
            if (current2.getNext() != null) {
                gText += "\"" + current2.getValue().getName() + "\" -> \"" + current2.getNext().getValue().getName() + "\" \n";
            }
            if (current2.getNext() == null && current2.getPrevious() == null) {
                gText += "\"" + current2.getValue().getName() + "\"";
            }
            Node current3 = current2.getValue().getImages().first;
            if (current3 != null) {
                gText += "\"" + current2.getValue().getName() + "\" -> \"Imagen " + current2.getValue().getImages().first.valuei.getId() + "\" \n";
            }
            while (current3 != null) {
                if (current3.next != null) {
                    gText += "\"Imagen " + current3.valuei.getId() + "\" -> \"Imagen " + current3.next.valuei.getId() + "\" \n";
                }
                current3 = current3.next;
            }
            current2 = current2.getNext();
        }
        gText += "label=\"Albumes con sus imágenes\";\n"
                + "}";
        drawImage(gText);
    }

    public static void drawImage(String text) {
        createFile(text);
        ProcessBuilder process = null;
        process = new ProcessBuilder("dot", "-Tpng", "-o", "albumsTree.png", "albumsTree.dot");
        process.redirectErrorStream(true);
        try {
            process.start();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void createFile(String text) {
        FileWriter f = null;
        PrintWriter textG = null;
        try {
            f = new FileWriter("albumsTree.dot");
            textG = new PrintWriter(f);
            textG.write(text);
            textG.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (textG != null) {
                textG.close();
                try {
                    f.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
