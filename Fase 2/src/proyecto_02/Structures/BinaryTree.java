package proyecto_02.Structures;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import proyecto_02.AmplitudeObject;
import proyecto_02.Layer;
import proyecto_02.Photo;
import proyecto_02.Structures.SubStructure.Node;
import proyecto_02.Structures.SubStructure.NodeBinary;
import proyecto_02.Structures.SubStructure.NodeMatrix;

/**
 *
 * @author Alexis
 */
public class BinaryTree {

    NodeBinary root;
    public List graphLayers = new List();
    public List leafLayers = new List();
    public List levels;
    public int grade;
    String imagesTreeText = "";

    public BinaryTree() {
        this.root = null;
    }

    public void add(NodeBinary layer) {
        if (root == null) {
            this.root = layer;
        } else {
            NodeBinary aux = root;
            NodeBinary father;
            while (true) {
                father = aux;
                if (layer.value.getId() < aux.value.getId()) {
                    aux = aux.left;
                    if (aux == null) {
                        father.left = layer;
                        break;
                    }
                } else {
                    aux = aux.right;
                    if (aux == null) {
                        father.right = layer;
                        break;
                    }
                }
            }
        }
    }

    public void remove(NodeBinary layer) {
        NodeBinary aux = this.root;
        NodeBinary father = this.root;
        boolean left = true;
        while (aux.value.getId() != layer.value.getId()) {
            father = aux;
            if (layer.value.getId() < aux.value.getId()) {
                left = true;
                aux = aux.left;
            } else {
                left = false;
                aux = aux.right;
            }
            if (aux == null) {
                System.out.println("El valor no fué encontrado");
                break;
            }
        }
        if (aux.left == null && aux.right == null) {
            if (aux == this.root) {
                this.root = null;
            } else if (left) {
                father.left = null;
            } else {
                father.right = null;
            }
        } else if (aux.right == null) {
            if (aux == this.root) {
                this.root = aux.left;
            } else if (left) {
                father.left = aux.left;
            } else {
                father.right = aux.left;
            }
        } else if (aux.left == null) {
            if (aux == this.root) {
                this.root = aux.right;
            } else if (left) {
                father.left = aux.right;
            } else {
                father.right = aux.left;
            }
        } else {
            NodeBinary replace = obtainReplacingNode(aux);
            if (aux == this.root) {
                this.root = replace;
            } else if (left) {
                father.left = replace;
            } else {
                father.right = replace;
            }
            replace.left = aux.left;
        }
        System.out.println("Valor eliminado con éxito");
    }

    public NodeBinary obtainReplacingNode(NodeBinary replace) {
        NodeBinary replaceFather = replace;
        NodeBinary replaceAux = replace;
        NodeBinary aux = replace.right;
        while (aux != null) {
            replaceFather = replaceAux;
            replaceAux = aux;
            aux = aux.left;
        }
        if (replaceAux != replace.right) {
            replaceFather.left = replaceAux.right;
            replaceAux.right = replace.right;
        }
        return replaceAux;
    }

    public void preOrder(NodeBinary node) {
        if (node != null) {
            graphLayers.addLayer(node.value);
            //System.out.println("Added: " + node.value.getId());
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void inOrder(NodeBinary node) {
        if (node != null) {
            graphLayers.addLayer(node.value);
            //System.out.println(node.value.getId());
            inOrder(node.right);
            inOrder(node.left);
        }
    }

    public void postOrder(NodeBinary node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            graphLayers.addLayer(node.value);
            //System.out.println(node.value.getId());
        }
    }

    private void treeGrade(NodeBinary node, int level) {
        if (node != null) {
            treeGrade(node.left, level + 1);
            if (level > grade) {
                grade = level;
            }
            treeGrade(node.right, level + 1);
        }
    }

    public int amplitudeOrder() {
        grade = 0;
        treeGrade(this.root, 0);
        return grade;
    }

    public void printLevel() {
        levels = new List();
        printLevelNodes(this.root, 0);
        /*Node single = levels.first;
        while (single != null) {
            Node current = single.valueao.getLayers().first;
            System.out.println("Same level");
            while (current != null) {
                System.out.println(current.valuel.getId());
                current = current.next;
            }
            single = single.next;
        }*/
    }

    private void printLevelNodes(NodeBinary node, int currentLevel) {
        if (node != null) {
            Node currentNode = levels.first;
            while (currentNode != null) {
                if (currentNode.valueao.getIndex() == currentLevel) {
                    break;
                }
                currentNode = currentNode.next;
            }
            if (currentNode != null) {
                List temp = new List();
                temp.addLayer(node.value);
                Node current = currentNode.valueao.getLayers().first;
                while (current != null) {
                    temp.addLayer(current.valuel);
                    current = current.next;
                }
                currentNode.valueao = new AmplitudeObject(currentLevel, temp);
            } else {
                List temp = new List();
                temp.addLayer(node.value);
                levels.addAObject(new AmplitudeObject(currentLevel, temp));
            }
            printLevelNodes(node.right, currentLevel + 1);
            printLevelNodes(node.left, currentLevel + 1);
        }
    }

    public NodeBinary searchNode(int id, NodeBinary node) {
        if (node.value.getId() == id) {
            return node;
        } else if (node.value.getId() < id) {
            return searchNode(id, node.right);
        } else if (node.value.getId() > id) {
            return searchNode(id, node.left);
        }
        return null;
    }

    public void generateImage(Photo Image) {
        SparceMatrix sorted = new SparceMatrix();
        int maxRow = 0;
        int maxCol = 0;
        Node currentLayer = Image.getLayers().first;
        while (currentLayer != null) {
            Node current = levels.first;
            while (current != null) {
                Node current2 = current.valueao.getLayers().first;
                while (current2 != null) {
                    if (currentLayer.valuel.getId() == current2.valuel.getId()) {
                        Node pixelCurrent = currentLayer.valuel.getPixels().first;
                        while (pixelCurrent != null) {
                            if (pixelCurrent.valuep.getRow() > maxRow) {
                                maxRow = pixelCurrent.valuep.getRow();
                            }
                            if (pixelCurrent.valuep.getColumn() > maxCol) {
                                maxCol = pixelCurrent.valuep.getColumn();
                            }
                            //System.out.println("added: " + pixelCurrent.valuep.getRow() + " " + pixelCurrent.valuep.getColumn() + " " + pixelCurrent.valuep.getColor());
                            sorted.addNode(pixelCurrent.valuep.getRow(), pixelCurrent.valuep.getColumn(), pixelCurrent.valuep.getColor());
                            pixelCurrent = pixelCurrent.next;
                        }
                        break;
                    }
                    current2 = current2.next;
                }
                current = current.next;
            }
            currentLayer = currentLayer.next;
        }
        String gText = "digraph G {\n"
                + "node [shape=plaintext];\n"
                + "some_node [\n"
                + "label=<\n"
                + "\n"
                + "<table border=\"0\" cellborder=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n";
        for (int i = 0; i < maxRow; i++) {
            gText += "<tr>\n";
            for (int j = 0; j < maxCol; j++) {
                String color = "";
                boolean catched = false;
                NodeMatrix aux = sorted.root;
                while (aux != null) {
                    NodeMatrix aux2 = aux;
                    while (aux2 != null) {
                        if (aux2.x == i && aux2.y == j) {
                            color = aux2.value;
                            catched = true;
                            break;
                        } else {
                            aux2 = aux2.next;
                        }
                    }
                    if (catched) {
                        break;
                    }
                    aux = aux.down;
                }

                if (catched && aux != null) {
                    gText += "<td bgcolor=\"" + color + "\" width=\"1\" height=\"1\"></td>\n";
                } else {
                    gText += "<td bgcolor=\"white\" width=\"1\" height=\"1\"></td>\n";
                }
            }
            gText += "</tr>\n";
        }
        gText += "</table>>\n"
                + "]\n"
                + "label=\"Imagen " + Image.getId() + "\";\n"
                + "}";
        drawImage(gText, 1);
    }

    public void generateLimitedImage(List layers, int limit, String type) {
        List limited = new List();
        Node lCurrent = layers.first;
        System.out.println(type);
        while (lCurrent != null) {
            if (limited.size == limit) {
                break;
            } else {
                System.out.println(lCurrent.valuel.getId());
                limited.addLayer(lCurrent.valuel);
            }
            lCurrent = lCurrent.next;
        }
        SparceMatrix sorted = new SparceMatrix();
        int maxRow = 0;
        int maxCol = 0;
        Node currentLayer = limited.first;
        while (currentLayer != null) {
            Node pixelCurrent = currentLayer.valuel.getPixels().first;
            while (pixelCurrent != null) {
                if (pixelCurrent.valuep.getRow() > maxRow) {
                    maxRow = pixelCurrent.valuep.getRow();
                }
                if (pixelCurrent.valuep.getColumn() > maxCol) {
                    maxCol = pixelCurrent.valuep.getColumn();
                }
                //System.out.println("added: " + pixelCurrent.valuep.getRow() + " " + pixelCurrent.valuep.getColumn() + " " + pixelCurrent.valuep.getColor());
                sorted.addNode(pixelCurrent.valuep.getRow(), pixelCurrent.valuep.getColumn(), pixelCurrent.valuep.getColor());
                pixelCurrent = pixelCurrent.next;
            }
            currentLayer = currentLayer.next;
        }
        String gText = "digraph G {\n"
                + "node [shape=plaintext];\n"
                + "some_node [\n"
                + "label=<\n"
                + "\n"
                + "<table border=\"0\" cellborder=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n";
        for (int i = 0; i < maxRow; i++) {
            gText += "<tr>\n";
            for (int j = 0; j < maxCol; j++) {
                String color = "";
                boolean catched = false;
                NodeMatrix aux = sorted.root;
                while (aux != null) {
                    NodeMatrix aux2 = aux;
                    while (aux2 != null) {
                        if (aux2.x == i && aux2.y == j) {
                            color = aux2.value;
                            catched = true;
                            break;
                        } else {
                            aux2 = aux2.next;
                        }
                    }
                    if (catched) {
                        break;
                    }
                    aux = aux.down;
                }

                if (catched && aux != null) {
                    gText += "<td bgcolor=\"" + color + "\" width=\"1\" height=\"1\"></td>\n";
                } else {
                    gText += "<td bgcolor=\"white\" width=\"1\" height=\"1\"></td>\n";
                }
            }
            gText += "</tr>\n";
        }
        gText += "</table>>\n"
                + "]\n"
                + "label=\"Imagen en " + type + "\";\n"
                + "}";
        drawImage(gText, 2);
    }

    public void generateMultiLayerImage(List layers) {
        List limited = layers;
        SparceMatrix sorted = new SparceMatrix();
        int maxRow = 0;
        int maxCol = 0;
        Node currentLayer = limited.first;
        while (currentLayer != null) {
            Node pixelCurrent = currentLayer.valuel.getPixels().first;
            while (pixelCurrent != null) {
                if (pixelCurrent.valuep.getRow() > maxRow) {
                    maxRow = pixelCurrent.valuep.getRow();
                }
                if (pixelCurrent.valuep.getColumn() > maxCol) {
                    maxCol = pixelCurrent.valuep.getColumn();
                }
                //System.out.println("added: " + pixelCurrent.valuep.getRow() + " " + pixelCurrent.valuep.getColumn() + " " + pixelCurrent.valuep.getColor());
                sorted.addNode(pixelCurrent.valuep.getRow(), pixelCurrent.valuep.getColumn(), pixelCurrent.valuep.getColor());
                pixelCurrent = pixelCurrent.next;
            }
            currentLayer = currentLayer.next;
        }
        String gText = "digraph G {\n"
                + "node [shape=plaintext];\n"
                + "some_node [\n"
                + "label=<\n"
                + "\n"
                + "<table border=\"0\" cellborder=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n";
        for (int i = 0; i < maxRow; i++) {
            gText += "<tr>\n";
            for (int j = 0; j < maxCol; j++) {
                String color = "";
                boolean catched = false;
                NodeMatrix aux = sorted.root;
                while (aux != null) {
                    NodeMatrix aux2 = aux;
                    while (aux2 != null) {
                        if (aux2.x == i && aux2.y == j) {
                            color = aux2.value;
                            catched = true;
                            break;
                        } else {
                            aux2 = aux2.next;
                        }
                    }
                    if (catched) {
                        break;
                    }
                    aux = aux.down;
                }

                if (catched && aux != null) {
                    gText += "<td bgcolor=\"" + color + "\" width=\"1\" height=\"1\"></td>\n";
                } else {
                    gText += "<td bgcolor=\"white\" width=\"1\" height=\"1\"></td>\n";
                }
            }
            gText += "</tr>\n";
        }
        Node Node = layers.first;
        String single = "";
        while (Node != null) {
            if (Node.next == null) {
                single += Node.valuel.getId();
            } else {
                single += Node.valuel.getId() + ", ";
            }
            Node = Node.next;
        }
        gText += "</table>>\n"
                + "]\n"
                + "label=\"Capas utilizadas " + single + "\";\n"
                + "}";
        drawImage(gText, 3);
    }

    public void generateGraphTree() {
        this.imagesTreeText = "";
        String gText = "digraph G {\n";
        inOrderGraph(this.root);
        gText += this.imagesTreeText
                + "label=\"Arbol de capas\";\n"
                + "}";
        drawImage(gText, 4);
    }

    public void generateGraphMatrix(int idLayer) {
        NodeBinary found = searchNode(idLayer, this.root);
        SparceMatrix sorted = new SparceMatrix();
        Node currentPixel = found.value.getPixels().first;
        while (currentPixel != null) {
            sorted.addNode(currentPixel.valuep.getColumn(), currentPixel.valuep.getRow(), currentPixel.valuep.getColor());
            currentPixel = currentPixel.next;
        }
        String gText = "digraph Sparce_Matrix {\n"
                + "    node [shape=box]\n"
                + "    Mt[label=\"Capa " + found.value.getId() + "\",width=1.5,style=filled,fillcolor=lightblue,group=\"-1\"];\n"
                + "    e0[ shape = point, width = 0 ];\n"
                + "    e1[ shape = point, width = 0 ];\n";
        //Vertical
        NodeMatrix aux = sorted.root;
        while (aux != null) {
            NodeMatrix aux2 = aux;
            if (aux2.x == -1 && aux2.y == -1) {
            } else {
                gText += "\"x" + aux2.x + "y" + aux2.y + "\"" + "[label=\"" + aux2.y + "\" pos=\"5.3,3.5!\" width = 1.5 style=filled,fillcolor=white,group=\"-1\"];\n";
            }
            aux = aux.down;
        }
        NodeMatrix aux2 = sorted.root;
        while (aux2 != null) {
            NodeMatrix aux3 = aux2;
            if (aux3.down != null) {
                if (aux3.x == -1 && aux3.y == -1) {
                    gText += "Mt -> \"x" + aux3.down.x + "y" + aux3.down.y + "\"\n";
                } else {
                    if (aux3.up != null) {
                        if (aux3.up.y != -1) {
                            gText += "\"x" + aux3.x + "y" + aux3.y + "\" -> \"x" + aux3.up.x + "y" + aux3.up.y + "\"\n";
                        }
                    }
                    if (aux3.down.down == null) {
                        gText += "\"x" + aux3.down.x + "y" + aux3.down.y + "\" -> \"x" + aux3.x + "y" + aux3.y + "\"\n";
                    }
                    gText += "\"x" + aux3.x + "y" + aux3.y + "\" -> \"x" + aux3.down.x + "y" + aux3.down.y + "\"\n";
                }
            }
            aux2 = aux2.down;
        }
        //Horizontal
        NodeMatrix aux3 = sorted.root;
        while (aux3 != null) {
            NodeMatrix aux4 = aux3;
            while (aux4 != null) {
                if (aux4.x == -1 && aux4.y == -1) {
                } else {
                    gText += "\"x" + aux4.x + "y" + aux4.y + "\"" + "[label=\"" + aux4.x + "\" width = 1.5 style=filled,fillcolor=white,group=\"" + aux4.x + "\"];\n";
                }
                aux4 = aux4.next;
            }
            break;
        }
        NodeMatrix aux4 = sorted.root;
        String horizontal = "";
        while (aux4 != null) {
            NodeMatrix aux5 = aux4;
            while (aux5.next != null) {
                if (aux5.x == -1 && aux5.y == -1) {
                    horizontal += "Mt;";
                    gText += "Mt -> \"x" + aux5.next.x + "y" + aux5.next.y + "\"\n";
                } else {
                    if (aux5.next.next == null) {
                        horizontal += "\"x" + aux5.next.x + "y" + aux5.next.y + "\";";
                        gText += "\"x" + aux5.next.x + "y" + aux5.next.y + "\" -> \"x" + aux5.x + "y" + aux5.y + "\"\n";
                    }
                    if (aux5.previous != null) {
                        if (aux5.previous.x != -1) {
                            gText += "\"x" + aux5.x + "y" + aux5.y + "\" -> \"x" + aux5.previous.x + "y" + aux5.previous.y + "\"\n";
                        }
                    }
                    horizontal += "\"x" + aux5.x + "y" + aux5.y + "\";";
                    gText += "\"x" + aux5.x + "y" + aux5.y + "\" -> \"x" + aux5.next.x + "y" + aux5.next.y + "\"\n";
                }
                aux5 = aux5.next;
            }
            break;
        }
        gText += "{rank=same;" + horizontal + "}\n";
        NodeMatrix aux5 = sorted.root;
        while (aux5 != null) {
            NodeMatrix aux6 = aux5;
            while (aux6 != null) {
                if (aux6.x == -1 || aux6.y == -1) {
                } else {
                    gText += "\"x" + aux6.x + "y" + aux6.y + "\"[style=filled,fillcolor=black,width=1.5,group=\"" + aux6.x + "\"];\n";
                }
                aux6 = aux6.next;
            }
            aux5 = aux5.down;
        }
        NodeMatrix aux6 = sorted.root;
        while (aux6 != null) {
            NodeMatrix aux7 = aux6;
            String row = "";
            while (aux7 != null) {
                if (aux7.x == -1 || aux7.y == -1) {
                    if (aux7.x == -1 && aux7.y != -1) {
                        row += "\"x" + aux7.x + "y" + aux7.y + "\";";
                        gText += "\"x" + aux7.x + "y" + aux7.y + "\" -> \"x" + aux7.next.x + "y" + aux7.next.y + "\"\n";
                    }
                    if (aux7.x != -1 && aux7.y == -1) {
                        row += "\"x" + aux7.x + "y" + aux7.y + "\";";
                        gText += "\"x" + aux7.x + "y" + aux7.y + "\" -> \"x" + aux7.down.x + "y" + aux7.down.y + "\"\n";
                    }
                } else {
                    if (aux7.next != null) {
                        gText += "\"x" + aux7.x + "y" + aux7.y + "\" -> \"x" + aux7.next.x + "y" + aux7.next.y + "\"\n";
                        row += "\"x" + aux7.x + "y" + aux7.y + "\";";
                        if (aux7.next.next == null) {
                            row += "\"x" + aux7.next.x + "y" + aux7.next.y + "\";";
                        }
                    }
                    if (aux7.previous != null) {
                        gText += "\"x" + aux7.x + "y" + aux7.y + "\" -> \"x" + aux7.previous.x + "y" + aux7.previous.y + "\"\n";
                    }

                    if (aux7.up != null) {
                        gText += "\"x" + aux7.x + "y" + aux7.y + "\" -> \"x" + aux7.up.x + "y" + aux7.up.y + "\"\n";
                    }
                    /*if (aux7.down != null) {
                        gText += "\"x" + aux7.x + "y" + aux7.y + "\" -> \"x" + aux7.down.x + "y" + aux7.down.y + "\"\n";
                    }*/
                }
                aux7 = aux7.next;
            }
            gText += "{rank=same;" + row + "}\n";
            aux6 = aux6.down;
        }
        gText += "}";
        drawImage(gText, 5);
        System.out.println(gText);
    }

    public static void drawImage(String text, int type) {
        createFile(text, type);
        ProcessBuilder process = null;
        if (type == 1) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "completeImage.png", "completeImage.dot");
        } else if (type == 2) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "limitedImage.png", "limitedImage.dot");
        } else if (type == 3) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "multiLayersImage.png", "multiLayersImage.dot");
        } else if (type == 4) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "layersTree.png", "layersTree.dot");
        } else if (type == 5) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "layerMatrix.png", "layerMatrix.dot");
        } else if (type == 6) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "layersLeaf.png", "layersLeaf.dot");
        } else if (type == 7) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "layersList.png", "layersList.dot");
        } else if (type == 8) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "clientsGraph.png", "clientsGraph.dot");
        }
        process.redirectErrorStream(true);
        try {
            process.start();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void createFile(String text, int type) {
        FileWriter f = null;
        PrintWriter textG = null;
        try {
            if (type == 1) {
                f = new FileWriter("completeImage.dot");
            } else if (type == 2) {
                f = new FileWriter("limitedImage.dot");
            } else if (type == 3) {
                f = new FileWriter("multiLayersImage.dot");
            } else if (type == 4) {
                f = new FileWriter("layersTree.dot");
            } else if (type == 5) {
                f = new FileWriter("layerMatrix.dot");
            } else if (type == 6) {
                f = new FileWriter("layersLeaf.dot");
            } else if (type == 7) {
                f = new FileWriter("layersList.dot");
            } else if (type == 8) {
                f = new FileWriter("clientsGraph.dot");
            }
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

    public void inOrderGraph(NodeBinary node) {
        if (node != null) {
            imagesTreeText += node.value.getId() + "\n";
            //System.out.println(node.value.getId());
            if (node.right != null) {
                imagesTreeText += node.value.getId() + " -> " + node.right.value.getId() + "\n";
                inOrderGraph(node.right);
            }
            if (node.left != null) {
                imagesTreeText += node.value.getId() + " -> " + node.left.value.getId() + "\n";
                inOrderGraph(node.left);
            }
        }
    }

    public NodeBinary getRoot() {
        return root;
    }

    public List getGraphLayers() {
        return graphLayers;
    }

    public void setGraphLayers(List graphLayers) {
        this.graphLayers = graphLayers;
    }

    public void allLeafs(NodeBinary node) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                leafLayers.addLayer(node.value);
            }
            allLeafs(node.left);
            allLeafs(node.right);
        }
    }

    public void allLeafsGraph() {
        Node current = leafLayers.first;
        String gText = "digraph {\n"
                + "  node [ shape=none fontname=Helvetica ]\n"
                + "  n [ label = <\n"
                + "    <table bgcolor=\"black\">"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Id de la capa</td>\n"
                + "       </tr>";
        while (current != null) {
            gText += "         <tr>\n"
                    + "         <td bgcolor=\"#ffcccc\"> Capa " + current.valuel.getId() + "</td>\n"
                    + "       </tr>";
            current = current.next;
        }
        gText += "</table>\n"
                + "  > ]\n"
                + "label = \"Todas las capas que son hojas\";}";

        drawImage(gText, 6);
    }

    public void listLayers(String type) {
        Node current = graphLayers.first;
        String gText = "digraph {\n"
                + "  node [ shape=none fontname=Helvetica ]\n"
                + "  n [ label = <\n"
                + "    <table bgcolor=\"black\">"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Id de la capa</td>\n"
                + "       </tr>";
        while (current != null) {
            gText += "         <tr>\n"
                    + "         <td bgcolor=\"#ffcccc\"> Capa " + current.valuel.getId() + "</td>\n"
                    + "       </tr>";
            current = current.next;
        }
        gText += "</table>\n"
                + "  > ]\n"
                + "label = \"Lista de capas en "+ type +"\";}";
        drawImage(gText, 7);
    }

}
