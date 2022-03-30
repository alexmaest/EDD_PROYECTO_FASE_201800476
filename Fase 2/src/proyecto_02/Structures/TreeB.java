package proyecto_02.Structures;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import proyecto_02.AmplitudeObject;
import proyecto_02.Client;
import proyecto_02.Structures.SubStructure.Branch;
import proyecto_02.Structures.SubStructure.Node;
import proyecto_02.Structures.SubStructure.NodeB;
import proyecto_02.Structures.SubStructure.NodeDouble;

/**
 *
 * @author Alexis
 */
public class TreeB {

    int grade = 5;
    int gradeNodes;
    public Branch root;
    public static List levelNodes = new List();
    String data = "digraph D {\n"
            + "\n"
            + "  node [shape=plaintext]\n";

    public TreeB() {
        this.root = null;
    }

    public void addValue(Client client) {
        NodeB newValue = new NodeB(client);
        if (this.root == null) {
            this.root = new Branch();
            this.root.addNode(newValue);
        } else {
            NodeB obj = addInBranch(newValue, this.root);
            if (obj != null) {
                this.root = new Branch();
                this.root.addNode(obj);
                this.root.setLeaf(false);
            }
        }
    }

    private NodeB addInBranch(NodeB newValue, Branch branch) {
        if (branch.isLeaf()) {
            branch.addNode(newValue);
            if (branch.getSize() == grade) {
                returnLast(branch);
                return split(branch);
            } else {
                return null;
            }
        } else {
            NodeB Current = branch.getFirst();
            do {
                if (newValue.getClient().getDpi() == Current.getClient().getDpi()) {
                    return null;
                } else if (newValue.getClient().getDpi() < Current.getClient().getDpi()) {
                    NodeB obj = addInBranch(newValue, Current.getLeft());
                    if (obj instanceof NodeB) {
                        branch.addNode((NodeB) obj);
                        if (branch.getSize() == grade) {
                            return split(branch);
                        }
                    }
                    return null;
                } else if (Current.getNext() == null) {
                    NodeB obj = addInBranch(newValue, Current.getRight());
                    if (obj instanceof NodeB) {
                        branch.addNode((NodeB) obj);
                        if (branch.getSize() == grade) {
                            Current.setRight(null);
                            return split(branch);
                        }
                    }
                    return null;
                }
                Current = (NodeB) Current.getNext();
            } while (Current != null);
        }
        return null;
    }

    private NodeB split(Branch branch) {
        Client client = null;
        NodeB temp;
        NodeB upperValue;
        NodeB current = branch.getFirst();
        Branch branchL = new Branch();
        Branch branchR = new Branch();

        int cont = 0;
        while (current != null) {
            cont++;
            if (cont < 3) {
                temp = new NodeB(current.getClient());
                temp.setLeft(current.getLeft());
                if (cont == 2) {
                    temp.setRight(current.getNext().getLeft());
                } else {
                    temp.setRight(current.getRight());
                }
                if (temp.getLeft() != null || temp.getRight() != null) {
                    branchL.setLeaf(false);
                }
                branchL.addNode(temp);

            } else if (cont == 3) {
                client = current.getClient();
            } else {
                temp = new NodeB(current.getClient());
                temp.setLeft(current.getLeft());
                temp.setRight(current.getRight());
                if (temp.getRight() != null || temp.getLeft() != null) {
                    branchR.setLeaf(false);
                }
                branchR.addNode(temp);
            }
            current = current.getNext();
        }
        upperValue = new NodeB(client);
        upperValue.setRight(branchR);
        upperValue.setLeft(branchL);
        branch.setFirst(null);
        branch.setSize(0);
        return upperValue;
    }

    public void removeValue(long dpi, Branch branch) {
        if (branch != null) {
            NodeB Current = branch.getFirst();
            if (branch.getSize() == 1 && dpi == branch.getFirst().getClient().getDpi()) {
                removeMaster();
            } else {
                while (Current != null) {
                    if (Current.getClient().getDpi() == dpi) {
                        if (branch.isLeaf()) {
                            removeInLeaf(Current, branch);
                        } else {
                            removeNotInLeaf(Current, branch);
                        }
                        System.out.println(dpi + " eliminado");
                        break;
                    } else if (Current.getClient().getDpi() > dpi) {
                        removeValue(dpi, Current.getLeft());
                        break;
                    } else if (Current.getNext() == null) {
                        removeValue(dpi, Current.getRight());
                        break;
                    }
                    Current = Current.getNext();
                }
                scannerSort(this.root);
            }
        } else if (branch == null && branch == this.root) {
            System.out.println("No hay clientes en el sistema");
        }
    }

    private void removeInLeaf(NodeB value, Branch branch) {
        if (value.getPrevious() != null) {
            value.getPrevious().setNext(value.getNext());
        }
        if (value.getNext() != null) {
            value.getNext().setPrevious(value.getPrevious());
        }
        if (value == branch.getFirst()) {
            branch.setFirst(value.getNext());
        }
        branch.setSize(branch.getSize() - 1);
    }

    private void removeNotInLeaf(NodeB value, Branch branch) {
        if (value.getNext() != null) {
            NodeB last = returnLast(value.getLeft());
            value.setClient(last.getClient());
            last.getPrevious().setNext(null);
            value.getLeft().setSize(value.getLeft().getSize() - 1);
        } else {
            NodeB second = value.getRight().getFirst().getNext();
            value.setClient(value.getRight().getFirst().getClient());
            value.getRight().setFirst(second);
            value.getRight().setSize(value.getRight().getSize() - 1);
        }
    }

    private NodeB returnLast(Branch branch) {
        NodeB current = branch.getFirst();
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    private void scannerSort(Branch branch) {
        NodeB current = branch.getFirst();
        boolean change = false;
        while (current != null) {
            if (current.getLeft() != null) {
                if (current.getLeft().isLeaf()) {
                    if (current.getNext() != null) {
                        if (current.getLeft().getSize() < 2 && current.getNext().getLeft().getSize() > 2) {
                            change = true;
                            sortingLeafs(current, true);
                            current.getNext().getLeft().setFirst(current.getNext().getLeft().getFirst().getNext());
                            current.getNext().getLeft().setSize(current.getNext().getLeft().getSize() - 1);
                        } else if (current.getLeft().getSize() == 2 && current.getNext().getLeft().getSize() < 2
                                || current.getLeft().getSize() < 2 && current.getNext().getLeft().getSize() == 2) {
                            change = true;
                            mergeLeafs(current, true, branch);
                            branch.setSize(branch.getSize() - 1);
                        }
                    } else {
                        if (current.getLeft().getSize() < 2 && current.getRight().getSize() > 2) {
                            change = true;
                            sortingLeafs(current, false);
                            current.getRight().setFirst(current.getRight().getFirst().getNext());
                            current.getRight().setSize(current.getRight().getSize() - 1);
                        } else if (current.getLeft().getSize() > 2 && current.getRight().getSize() < 2) {
                            change = true;
                            NodeB lastOne = new NodeB(current.getRight().getFirst().getClient());
                            NodeB copyCurrent = new NodeB(current.getClient());

                            Branch newBranch = new Branch();
                            newBranch.addNode(lastOne);
                            newBranch.addNode(copyCurrent);
                            NodeB previous = current.getPrevious();

                            current.setClient(returnLast(current.getLeft()).getClient());
                            current.setNext(null);
                            current.setPrevious(previous);
                            current.setRight(newBranch);
                            NodeB searchNode = current.getLeft().getFirst();
                            while (searchNode != null) {
                                if (searchNode.getNext() == null) {
                                    searchNode.getPrevious().setNext(null);
                                    break;
                                }
                                searchNode = searchNode.getNext();
                            }
                            current.getLeft().setSize(current.getLeft().getSize() - 1);
                        } else if (current.getLeft().getSize() == 2 && current.getRight().getSize() < 2
                                || current.getLeft().getSize() < 2 && current.getRight().getSize() == 2) {
                            change = true;
                            mergeLeafs(current, false, branch);
                            branch.setSize(branch.getSize() - 1);
                        }
                    }
                } else {
                    if (current.getPrevious() == null && current.getNext() == null
                            || current.getPrevious() != null && current.getNext() == null) {
                        if (current.getLeft().getSize() < 2 && current.getRight().getSize() > 2) {
                            change = true;
                            sortingNotLeafs(current, true);
                            current.getRight().setSize(current.getRight().getSize() - 1);
                            break;
                        } else if (current.getLeft().getSize() > 2 && current.getRight().getSize() < 2) {
                            change = true;
                            sortingNotLeafs(current, false);
                            current.getLeft().setSize(current.getLeft().getSize() - 1);
                            break;
                        } else if (current.getLeft().getSize() == 2 && current.getRight().getSize() < 2
                                || current.getLeft().getSize() < 2 && current.getRight().getSize() == 2) {
                            change = true;
                            mergeNotLeafs(current, false);
                            branch.setSize(branch.getSize() - 1);
                            break;
                        }
                    } else if (current.getPrevious() == null && current.getNext() != null) {
                        if (current.getLeft().getSize() < 2 && current.getNext().getLeft().getSize() > 2) {
                            change = true;
                            sortingNotLeafs2(current, true);
                            current.getNext().getLeft().setSize(current.getNext().getLeft().getSize() - 1);
                            break;
                        } else if (current.getLeft().getSize() > 2 && current.getNext().getLeft().getSize() < 2) {
                            change = true;
                            sortingNotLeafs2(current, false);
                            current.getLeft().setSize(current.getLeft().getSize() - 1);
                            break;
                        } else if (current.getLeft().getSize() == 2 && current.getNext().getLeft().getSize() < 2
                                || current.getLeft().getSize() < 2 && current.getNext().getLeft().getSize() == 2) {
                            change = true;
                            mergeNotLeafs(current, true);
                            branch.setSize(branch.getSize() - 1);
                            break;
                        }
                    }
                }

            }
            if (current.getLeft() != null) {
                scannerSort(current.getLeft());
            }
            if (current.getRight() != null) {
                scannerSort(current.getRight());
            }
            current = current.getNext();
        }
        if (change) {
            scannerSort(this.root);
        }
    }

    private void sortingLeafs(NodeB current, boolean mode) {
        NodeB lastOne = new NodeB(current.getLeft().getFirst().getClient());
        NodeB copyCurrent = new NodeB(current.getClient());

        Branch newBranch = new Branch();
        newBranch.addNode(lastOne);
        newBranch.addNode(copyCurrent);

        NodeB previous = current.getPrevious();
        NodeB next = current.getNext();
        if (mode) {
            current.setClient(current.getNext().getLeft().getFirst().getClient());
        } else {
            current.setClient(current.getRight().getFirst().getClient());
        }
        current.setNext(next);
        current.setPrevious(previous);
        current.setLeft(newBranch);
    }

    private void sortingNotLeafs(NodeB current, boolean mode) {
        if (mode) {
            current.getLeft().addNode(new NodeB(current.getClient()));
            current.getLeft().getFirst().getNext().setLeft(current.getLeft().getFirst().getRight());
            current.getLeft().getFirst().setRight(null);
            current.getLeft().getFirst().getNext().setRight(current.getRight().getFirst().getLeft());
            current.setClient(current.getRight().getFirst().getClient());
            current.getRight().setFirst(current.getRight().getFirst().getNext());
            current.getRight().getFirst().setPrevious(null);
        } else {
            current.getRight().getFirst().setNext(current.getRight().getFirst());
            current.getRight().getFirst().getNext().setLeft(current.getRight().getFirst().getLeft());
            current.getRight().getFirst().getNext().setRight(current.getRight().getFirst().getRight());
            current.getRight().setFirst(new NodeB(current.getClient()));
            NodeB last = returnLast(current.getLeft());
            last.getPrevious().setRight(last.getLeft());
            last.getPrevious().setNext(null);
            current.getRight().getFirst().setLeft(last.getRight());
            current.setClient(last.getClient());
        }
    }

    private void sortingNotLeafs2(NodeB current, boolean mode) {
        if (mode) {
            current.getLeft().addNode(new NodeB(current.getClient()));
            current.getLeft().getFirst().getNext().setLeft(current.getLeft().getFirst().getRight());
            current.getLeft().getFirst().setRight(null);
            current.getLeft().getFirst().getNext().setRight(current.getNext().getLeft());
            current.getNext().setLeft(null);

            current.setClient(current.getNext().getLeft().getFirst().getClient());
            current.getNext().getLeft().setFirst(current.getNext().getLeft().getFirst().getNext());
            current.getNext().getLeft().getFirst().setPrevious(null);

        } else {
            current.getNext().getLeft().addNode(new NodeB(current.getClient()));
            NodeB last = returnLast(current.getLeft());
            current.getNext().getLeft().getFirst().setLeft(last.getRight());
            last.setRight(null);
            last.getPrevious().setRight(last.getLeft());
            current.setClient(last.getClient());
        }
    }

    private void mergeLeafs(NodeB current, boolean mode, Branch branch) {
        Branch newBranch = new Branch();
        NodeB currentL = current.getLeft().getFirst();
        while (currentL != null) {
            newBranch.addNode(new NodeB(currentL.getClient()));
            currentL = currentL.getNext();
        }
        newBranch.addNode(new NodeB(current.getClient()));
        NodeB currentR;
        if (mode) {
            currentR = current.getNext().getLeft().getFirst();
        } else {
            currentR = current.getRight().getFirst();
        }
        while (currentR != null) {
            newBranch.addNode(new NodeB(currentR.getClient()));
            currentR = currentR.getNext();
        }
        if (current.getPrevious() != null && current.getNext() != null) {
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        } else if (current.getPrevious() == null && current.getNext() != null) {
            branch.setFirst(current.getNext());
        } else if (current.getPrevious() != null && current.getNext() == null) {
            current.getPrevious().setNext(null);
        }
        if (mode) {
            current.getNext().setLeft(newBranch);
        } else {
            if (current.getPrevious() != null) {
                current.getPrevious().setRight(newBranch);
            } else if (current.getPrevious() == null && current.getNext() == null) {
                this.root = newBranch;
            }
        }
    }

    private void mergeNotLeafs(NodeB current, boolean mode) {
        if (mode) {
            Branch newBranch = new Branch();
            NodeB currentL = current.getLeft().getFirst();
            Branch last = null;
            while (currentL != null) {
                if (currentL.getNext() == null) {
                    newBranch.addNode(new NodeB(currentL.getClient()));
                    NodeB newElement = returnNode(newBranch, currentL.getClient().getDpi());
                    newElement.setLeft(currentL.getLeft());
                    last = currentL.getRight();
                    currentL.setRight(null);
                } else {
                    newBranch.addNode(new NodeB(currentL.getClient()));
                    NodeB newElement = returnNode(newBranch, currentL.getClient().getDpi());
                    newElement.setLeft(currentL.getLeft());
                    newElement.setRight(currentL.getRight());
                }
                currentL = currentL.getNext();
            }
            newBranch.addNode(new NodeB(current.getClient()));
            NodeB newElement = returnNode(newBranch, current.getClient().getDpi());
            newElement.setLeft(last);
            newElement.setRight(null);

            NodeB currentR = current.getNext().getLeft().getFirst();
            while (currentR != null) {
                newBranch.addNode(new NodeB(currentR.getClient()));
                NodeB newElement2 = returnNode(newBranch, currentR.getClient().getDpi());
                newElement2.setLeft(currentR.getLeft());
                newElement2.setRight(currentR.getRight());
                currentR = currentR.getNext();
            }
            current.getNext().setLeft(newBranch);
            current.getNext().setPrevious(null);
        } else {
            Branch newBranch = new Branch();
            NodeB currentL = current.getLeft().getFirst();
            Branch last = null;
            while (currentL != null) {
                if (currentL.getNext() == null) {
                    newBranch.addNode(new NodeB(currentL.getClient()));
                    NodeB newElement = returnNode(newBranch, currentL.getClient().getDpi());
                    newElement.setLeft(currentL.getLeft());
                    last = currentL.getRight();
                    currentL.setRight(null);
                } else {
                    newBranch.addNode(new NodeB(currentL.getClient()));
                    NodeB newElement = returnNode(newBranch, currentL.getClient().getDpi());
                    newElement.setLeft(currentL.getLeft());
                    newElement.setRight(currentL.getRight());
                }
                currentL = currentL.getNext();
            }
            newBranch.addNode(new NodeB(current.getClient()));
            NodeB newElement = returnNode(newBranch, current.getClient().getDpi());
            newElement.setLeft(last);
            newElement.setRight(null);

            NodeB currentR = current.getRight().getFirst();
            while (currentR != null) {
                newBranch.addNode(new NodeB(currentR.getClient()));
                NodeB newElement2 = returnNode(newBranch, currentR.getClient().getDpi());
                newElement2.setLeft(currentR.getLeft());
                newElement2.setRight(currentR.getRight());
                currentR = currentR.getNext();
            }
            current.getPrevious().setNext(null);
            current.getPrevious().setRight(newBranch);
        }
    }

    private void removeMaster() {
        NodeB lastOne = returnTheLastOfBranch(this.root.getFirst().getLeft());
        lastOne.getPrevious().setNext(null);
        this.root.getFirst().setClient(lastOne.getClient());
    }

    private NodeB returnTheLastOfBranch(Branch branch) {
        if (branch.isLeaf()) {
            return returnLast(branch);
        } else {
            return returnTheLastOfBranch(returnLast(branch).getRight());
        }
    }

    private NodeB returnNode(Branch branch, long value) {
        NodeB current = branch.getFirst();
        while (current != null) {
            if (current.getClient().getDpi() == value) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    public Client searchValue(long dpi, Branch branch) {
        if (branch != null) {
            NodeB Current = branch.getFirst();
            while (Current != null) {
                if (Current.getClient().getDpi() == dpi) {
                    System.out.println("Encontrado");
                    return Current.getClient();
                } else if (Current.getClient().getDpi() > dpi) {
                    return searchValue(dpi, Current.getLeft());
                } else if (Current.getNext() == null) {
                    return searchValue(dpi, Current.getRight());
                }
                Current = Current.getNext();
            }
        } else if (branch == null && branch == this.root) {
            System.out.println("No hay clientes en el sistema");
        }
        return null;
    }

    public void modifyValue(long dpi, String name, String pass, Branch branch) {
        if (branch != null) {
            NodeB Current = branch.getFirst();
            while (Current != null) {
                if (Current.getClient().getDpi() == dpi) {
                    Current.setClient(new Client(dpi, name, pass));
                    break;
                } else if (Current.getClient().getDpi() > dpi) {
                    searchValue(dpi, Current.getLeft());
                    break;
                } else if (Current.getNext() == null) {
                    searchValue(dpi, Current.getRight());
                    break;
                }
                Current = Current.getNext();
            }
        } else if (branch == null && branch == this.root) {
            System.out.println("No hay clientes en el sistema");
        }
    }

    public int printTreeContent(Branch branch, int cont) {
        int intern = cont;
        if (branch != null) {
            NodeB Current = branch.getFirst();
            NodeB node = branch.getFirst();
            String name = "node" + cont;
            data += name + "[\n"
                    + "   label=<\n"
                    + "     <table border=\"0\" cellborder=\"1\" cellspacing=\"0\">\n"
                    + "       <tr>";
            while (node != null) {
                data += "<td>" + node.getClient().getDpi() + "</td>";
                node = node.getNext();
            }
            data += "</tr>\n"
                    + "     </table>>\n"
                    + "  ];\n";
            int temp = cont;
            while (Current != null) {

                if (Current.getLeft() != null) {
                    if (Current.getLeft().getSize() != 0) {
                        data += "node" + cont + " -> " + "node" + (temp + 1) + "\n";
                        temp = printTreeContent(Current.getLeft(), temp + 1);
                        temp += 1;
                    }
                }

                if (Current.getRight() != null) {
                    if (Current.getRight().getSize() != 0) {
                        //System.out.println("Empieza right");
                        data += "node" + cont + " -> " + "node" + (temp + 1) + "\n";
                        intern = printTreeContent(Current.getRight(), temp + 1);
                        //System.out.println("Termina right");
                    }
                }
                Current = Current.getNext();
            }
            intern = temp;
        }
        return intern;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void generateSearchGraph(Client cliente) {
        cliente.getImages().images = new List();
        cliente.getLayers().graphLayers = new List();
        cliente.getImages().fillComboBox(cliente.getImages().root);
        cliente.getLayers().preOrder(cliente.getLayers().root);
        String gText = "digraph {\n"
                + "  node [ shape=none fontname=Helvetica ]\n"
                + "  n [ label = <\n"
                + "    <table bgcolor=\"black\">"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Nombre</td>\n"
                + "         <td bgcolor=\"#ffcccc\">" + cliente.getName() + "</td>\n"
                + "       </tr>"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">DPI</td>\n"
                + "         <td bgcolor=\"#ffcccc\">" + cliente.getDpi() + "</td>\n"
                + "       </tr>"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Cantidad de imágenes</td>\n"
                + "         <td bgcolor=\"#ffcccc\">" + cliente.getImages().images.size + "</td>\n"
                + "       </tr>"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Cantidad de capas</td>\n"
                + "         <td bgcolor=\"#ffcccc\">" + cliente.getLayers().graphLayers.size + "</td>\n"
                + "       </tr>";
        gText += "</table>\n"
                + "  > ]\n"
                + "  n2 [ label = <\n"
                + "    <table bgcolor=\"black\">"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Nombre album</td>\n"
                + "         <td bgcolor=\"#ccccff\">Cantidad de imágenes</td>\n"
                + "       </tr>";
        NodeDouble current = cliente.getAlbums().first;
        while (current != null) {
            gText += "       <tr>\n"
                    + "         <td bgcolor=\"#ffcccc\">" + current.getValue().getName() + "</td>\n"
                    + "         <td bgcolor=\"#ffcccc\">" + current.getValue().getImages().size + "</td>\n"
                    + "       </tr>";
            current = current.getNext();
        }
        gText += "</table>\n"
                + "  > ]\n"
                + "label = \"Información del cliente " + cliente.getName() + "\";}";
        drawImage(gText, 1);
    }

    private void treeGrade(Branch branch, int level) {
        if (branch != null) {
            NodeB currently = branch.getFirst();
            while (currently != null) {
                treeGrade(currently.getLeft(), level + 1);
                currently = currently.getNext();
            }
            if (level > gradeNodes) {
                gradeNodes = level;
            }
            NodeB currently2 = branch.getFirst();
            while (currently2 != null) {
                treeGrade(currently2.getRight(), level + 1);
                currently2 = currently2.getNext();
            }
        }
    }

    public int amplitudeOrder() {
        gradeNodes = 0;
        treeGrade(this.root, 0);
        return gradeNodes;
    }

    public void printLevel() {
        levelNodes = new List();
        printLevelNodes(this.root, 0);
    }

    private void printLevelNodes(Branch branch, int currentLevel) {
        if (branch != null) {
            Node currentNode = levelNodes.first;
            while (currentNode != null) {
                if (currentNode.valueao.getIndex() == currentLevel) {
                    break;
                }
                currentNode = currentNode.next;
            }
            if (currentNode != null) {
                List temp = new List();
                NodeB currently = branch.getFirst();
                while (currently != null) {
                    temp.addC(currently.getClient());
                    currently = currently.getNext();
                }
                Node current = currentNode.valueao.getLayers().first;
                while (current != null) {
                    temp.addC(current.valuec);
                    current = current.next;
                }
                currentNode.valueao = new AmplitudeObject(currentLevel, temp);
            } else {
                List temp = new List();
                NodeB currently = branch.getFirst();
                while (currently != null) {
                    temp.addC(currently.getClient());
                    currently = currently.getNext();
                }
                levelNodes.addAObject(new AmplitudeObject(currentLevel, temp));
            }
            NodeB currently = branch.getFirst();
            while (currently != null) {
                printLevelNodes(currently.getLeft(), currentLevel + 1);
                printLevelNodes(currently.getRight(), currentLevel + 1);
                currently = currently.getNext();
            }
        }
    }

    public static void drawImage(String text, int type) {
        createFile(text, type);
        ProcessBuilder process = null;
        if (type == 1) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "searchGraph.png", "searchGraph.dot");
        } else if (type == 2) {
            process = new ProcessBuilder("dot", "-Tpng", "-o", "clientsGraph.png", "clientsGraph.dot");
        }
        process.redirectErrorStream(true);
        try {
            process.start();
            System.out.println("Archivo generado");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void createFile(String text, int type) {
        FileWriter f = null;
        PrintWriter textG = null;
        try {
            if (type == 1) {
                f = new FileWriter("searchGraph.dot");
            } else if (type == 2) {
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
                    System.out.println(ex);
                }
            }
        }
    }

    public static void clientsGraph() {
        Node current = levelNodes.first;
        String gText = "digraph {\n"
                + "  node [ shape=none fontname=Helvetica ]\n"
                + "  n [ label = <\n"
                + "    <table bgcolor=\"black\">\n"
                + "       <tr>\n"
                + "         <td bgcolor=\"#ccccff\">Nivel</td>\n"
                + "         <td bgcolor=\"#ccccff\">Nombre</td>\n"
                + "         <td bgcolor=\"#ccccff\">DPI</td>\n"
                + "         <td bgcolor=\"#ccccff\">Cantidad de imágenes</td>\n"
                + "       </tr>\n";
        while (current != null) {
            Node current2 = current.valueao.getLayers().first;
            while (current2 != null) {
                if (current2.valuec.getImages() != null) {
                    current2.valuec.getImages().images = new List();
                    current2.valuec.getImages().fillComboBox(current2.valuec.getImages().root);
                    gText += "         <tr>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current.valueao.getIndex() + "</td>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current2.valuec.getName() + "</td>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current2.valuec.getDpi() + "</td>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current2.valuec.getImages().images.size + "</td>\n"
                            + "       </tr>\n";
                } else {
                    gText += "         <tr>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current.valueao.getIndex() + "</td>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current2.valuec.getName() + "</td>\n"
                            + "         <td bgcolor=\"#ffcccc\">" + current2.valuec.getDpi() + "</td>\n"
                            + "         <td bgcolor=\"#ffcccc\">0</td>\n"
                            + "       </tr>\n";
                }
                current2 = current2.next;
            }
            current = current.next;
        }
        gText += "</table>\n"
                + "  > ]\n"
                + "label = \"Lista de clientes por niveles\";\n}";
        System.out.println(gText);
        BinaryTree.drawImage(gText, 8);
    }
}
