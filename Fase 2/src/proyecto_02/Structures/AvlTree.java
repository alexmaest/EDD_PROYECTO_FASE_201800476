package proyecto_02.Structures;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import proyecto_02.Photo;
import proyecto_02.Structures.SubStructure.Node;
import proyecto_02.Structures.SubStructure.NodeAvl;
import proyecto_02.Structures.SubStructure.NodeBinary;

/**
 *
 * @author Alexis
 */
public class AvlTree {

    public NodeAvl root;
    public List images = new List();
    public List sortImages = new List();
    public String imagesTreeText = "";

    public AvlTree() {
        this.root = null;
    }

    public NodeAvl Search(int d, NodeAvl r) {
        if (this.root == null) {
            return null;
        } else if (r.dato.getId() == d) {
            return r;
        } else if (r.dato.getId() < d) {
            return Search(d, r.right);
        } else {
            return Search(d, r.left);
        }
    }

    public int obtainFE(NodeAvl x) {
        if (x == null) {
            return -1;
        } else {
            return x.fe;
        }
    }

    private NodeAvl rotateLeft(NodeAvl c) {
        NodeAvl aux = c.left;
        c.left = aux.right;
        aux.right = c;
        c.fe = Math.max(obtainFE(c.left), obtainFE(c.right)) + 1;
        aux.fe = Math.max(obtainFE(aux.left), obtainFE(aux.right)) + 1;
        return aux;
    }

    private NodeAvl rotateRight(NodeAvl c) {
        NodeAvl aux = c.right;
        c.right = aux.left;
        aux.left = c;
        c.fe = Math.max(obtainFE(c.left), obtainFE(c.right)) + 1;
        aux.fe = Math.max(obtainFE(aux.left), obtainFE(aux.right)) + 1;
        return aux;
    }

    private NodeAvl rotateDoubleLeft(NodeAvl c) {
        NodeAvl aux;
        c.left = rotateRight(c.left);
        aux = rotateLeft(c);
        return aux;
    }

    private NodeAvl rotateDoubleRight(NodeAvl c) {
        NodeAvl aux;
        c.right = rotateLeft(c.right);
        aux = rotateRight(c);
        return aux;
    }

    private NodeAvl Add(NodeAvl newValue, NodeAvl subNode) {
        NodeAvl newFather = subNode;
        if (newValue.dato.getId() < subNode.dato.getId()) {
            if (subNode.left == null) {
                subNode.left = newValue;
            } else {
                subNode.left = Add(newValue, subNode.left);
                if ((obtainFE(subNode.left) - obtainFE(subNode.right)) == 2) {
                    if (newValue.dato.getId() < subNode.left.dato.getId()) {
                        newFather = rotateLeft(subNode);
                    } else {
                        newFather = rotateDoubleLeft(subNode);
                    }
                }
            }
        } else if (newValue.dato.getId() > subNode.dato.getId()) {
            if (subNode.right == null) {
                subNode.right = newValue;
            } else {
                subNode.right = Add(newValue, subNode.right);
                if ((obtainFE(subNode.right) - obtainFE(subNode.left)) == 2) {
                    if (newValue.dato.getId() > subNode.right.dato.getId()) {
                        newFather = rotateRight(subNode);
                    } else {
                        newFather = rotateRight(subNode);
                    }
                }
            }
        } else {
            System.out.println("Nodo duplicado");
        }
        //Actualizando la altura
        if (subNode.left == null && subNode.right != null) {
            subNode.fe = subNode.right.fe + 1;
        } else if (subNode.right == null && subNode.left != null) {
            subNode.fe = subNode.left.fe + 1;
        } else {
            subNode.fe = Math.max(obtainFE(subNode.left), obtainFE(subNode.right)) + 1;
        }
        return newFather;
    }

    public void insert(Photo d) {
        NodeAvl newValue = new NodeAvl(d);
        if (this.root == null) {
            this.root = newValue;
        } else {
            this.root = Add(newValue, this.root);
        }
    }

    public void generateGraphTree() {
        this.imagesTreeText = "";
        String gText = "digraph G {\n"
                + "node [shape=square];\n";
        preOrder(this.root);
        gText += this.imagesTreeText
                + "label=\"Arbol de imágenes utilizadas\";\n"
                + "}";
        drawImage(gText, 1);
    }

    public static void drawImage(String text, int type) {
        createFile(text, type);
        ProcessBuilder process = null;
        if (type == 1) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "imagesTree.png", "imagesTree.dot");
        } else if (type == 2) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "imagesTreeLayers.png", "imagesTreeLayers.dot");
        } else if (type == 3) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "top5Images.png", "top5Images.dot");
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
                f = new FileWriter("imagesTree.dot");
            } else if (type == 2) {
                f = new FileWriter("imagesTreeLayers.dot");
            } else if (type == 3) {
                f = new FileWriter("top5Images.dot");
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

    public void preOrder(NodeAvl node) {
        imagesTreeText += node.dato.getId() + "\n";
        //System.out.println(dato);
        if (node.left != null) {
            imagesTreeText += node.dato.getId() + " -> " + node.left.dato.getId() + "\n";
            preOrder(node.left);
        }
        if (node.right != null) {
            imagesTreeText += node.dato.getId() + " -> " + node.right.dato.getId() + "\n";
            preOrder(node.right);
        }
    }

    public void generateGraphTreeAndLayers(int idImage) {
        this.imagesTreeText = "";
        String gText = "digraph G {\n";
        preOrderTwo(this.root, idImage);
        gText += this.imagesTreeText
                + "label=\"Arbol de imágenes y capas\";\n"
                + "}";
        drawImage(gText, 2);
    }

    public void preOrderTwo(NodeAvl node, int idImage) {
        imagesTreeText += "node [shape=square];\n";
        imagesTreeText += "Imagen" + node.dato.getId() + "\n";
        if (idImage == node.dato.getId()) {
            BinaryTree bt = new BinaryTree();
            Node layer = node.dato.getLayers().first;
            while (layer != null) {
                bt.add(new NodeBinary(layer.valuel));
                layer = layer.next;
            }
            imagesTreeText += "node [shape=oval];\n";
            imagesTreeText += "Imagen" + node.dato.getId() + " -> Capa" + bt.root.value.getId() + "\n";
            innerTree(bt.root);
        }
        //System.out.println(dato);
        if (node.left != null) {
            imagesTreeText += "node [shape=square];\n";
            imagesTreeText += "Imagen" + node.dato.getId() + " -> Imagen" + node.left.dato.getId() + "\n";
            preOrderTwo(node.left, idImage);
        }
        if (node.right != null) {
            imagesTreeText += "node [shape=square];\n";
            imagesTreeText += "Imagen" + node.dato.getId() + " -> Imagen" + node.right.dato.getId() + "\n";
            preOrderTwo(node.right, idImage);
        }
    }

    private void innerTree(NodeBinary node) {
        if (node.left != null) {
            imagesTreeText += "node [shape=oval];\n";
            imagesTreeText += "Capa" + node.value.getId() + " -> Capa" + node.left.value.getId() + "\n";
            innerTree(node.left);
        }
        if (node.right != null) {
            imagesTreeText += "node [shape=oval];\n";
            imagesTreeText += "Capa" + node.value.getId() + " -> Capa" + node.right.value.getId() + "\n";
            innerTree(node.right);
        }
    }

    public void inOrder(NodeAvl node) {
        this.root.inOrder();
    }

    public void postOrder(NodeAvl node) {
        this.root.postOrder();
    }

    public void fillComboBox(NodeAvl node) {
        if (node != null) {
            images.addImage(node.dato);
            fillComboBox(node.left);
            fillComboBox(node.right);
        }
    }

    public void top5Images(NodeAvl node) {
        if (node != null) {
            if (!sortImages.SearchPhoto(node.dato.getId())) {
                sortImages.sortHigherPhoto(node.dato);
                top5Images(node.left);
                top5Images(node.right);
            }
        }
    }

    public void top5ImagesGraph() {
        Node current = sortImages.first;
        String gText = "digraph {\n"
                + "  node [ shape=none fontname=Helvetica ]\n"
                + "  n [ label = <\n"
                + "    <table bgcolor=\"black\">"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Id de imagen</td>\n"
                + "         <td bgcolor=\"#ccccff\">Cantidad de capas</td>\n"
                + "       </tr>";
        int cont = 1;
        while (current != null) {
            if (cont == 5) {
                break;
            }
            gText += "         <tr>\n"
                    + "         <td bgcolor=\"#ccffcc\">" + current.valuei.getId() + "</td>\n"
                    + "         <td bgcolor=\"#ffcccc\">" + current.valuei.getLayers().size + "</td>"
                    + "       </tr>";
            current = current.next;
            cont += 1;
        }
        gText += "</table>\n"
                + "  > ]\n"
                + "label = \"Top 5 imágenes con más capas\";}";
        drawImage(gText, 3);
    }
}
