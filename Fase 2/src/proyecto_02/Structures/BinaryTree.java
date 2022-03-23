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
    public List levels;
    int grade;
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
            //System.out.println(node.value.getId());
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
        preOrder(found);
        String gText = "digraph TrafficLights {\n"
                + "node=[shape=box];\n";
        SparceMatrix sorted = new SparceMatrix();
        Node currentPixel = graphLayers.first;
        while (currentPixel != null) {
            sorted.addNode(currentPixel.valuep.getRow(), currentPixel.valuep.getColumn(), currentPixel.valuep.getColor());
            currentPixel = currentPixel.next;
        }
        NodeMatrix aux = sorted.root;
        while (aux != null) {
            NodeMatrix aux2 = aux;
            String row = "";
            while (aux2 != null) {
                if (aux2.next == null) {
                    row += "x"+aux2.x + "y"+aux2.y;
                } else {
                    row += "x"+aux2.x + "y"+aux2.y+",";
                }
                aux2 = aux2.next;
            }
            gText += "rank=same {" + row + "}\n";
            aux = aux.down;
        }
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
}
