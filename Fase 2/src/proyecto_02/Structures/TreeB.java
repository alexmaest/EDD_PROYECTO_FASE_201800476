package proyecto_02.Structures;

import proyecto_02.Structures.Nodes.NodeTreeB;
import proyecto_02.Structures.Nodes.NodeC;
import proyecto_02.Client;

/**
 *
 * @author Alexis
 */
public class TreeB {

    public NodeTreeB root;
    public int t;

    //Constructor
    public TreeB(int t) {
        this.t = t;
        root = new NodeTreeB();
    }

    public NodeTreeB search(NodeTreeB actual, int key) {
        int i = 0;//se empieza a buscar siempre en la primera posicion

        //Incrementa el indice mientras el valor de la clave del nodo sea menor
        while (i < actual.size && key > actual.key[i]) {
            i++;
        }

        //Si la clave es igual, entonces retornamos el nodo
        if (i < actual.size && key == actual.key[i]) {
            return actual;
        }

        //Si llegamos hasta aqui, entonces hay que buscar los hijos
        //Se revisa primero si tiene hijos
        if (actual.leaf) {
            return null;
        } else {
            //Si tiene hijos, hace una llamada recursiva
            return search(actual.child[i], key);
        }
    }

    public void add(Client value) {
        NodeTreeB node = root;

        //Si el nodo esta lleno lo debe separar antes de insertar
        if (node.size == 4) {
            //Wait
            NodeTreeB newNode = new NodeTreeB();
            root = newNode;
            newNode.leaf = false;
            newNode.size = 0;
            //newNode.child[0] = node;
            //split(newNode, 0, node);
            insert(newNode, value);
        } else {
            insert(node, value);
        }
    }

    public void insert(NodeTreeB Node, Client value) {
        //Si es una hoja
        if (Node.leaf) {
            List values = Node.values;
            NodeC Current = values.first;
            boolean active = false;
            NodeC aux = Current;
            while (Current != null) {
                if (value.getDpi() > Current.value.getDpi()) {
                    Current = Current.next;
                } else if (active) {
                    NodeC temp = Current;
                    Current = aux;
                    aux = temp;
                    Current = Current.next;
                } else {
                    active = true;
                    aux = Current;
                    NodeC newClient = new NodeC(value);
                    Current = newClient;
                    Current = Current.next;
                }
            }
            Node.size++; //aumenta la cantidad de elementos del nodo
        } else {
            int j = 0;
            List values = Node.values;
            NodeC Current = values.first;
            //Busca la posicion del hijo
            while (Current != null) {
                if (value.getDpi() > Current.value.getDpi()) {
                    Current = Current.next;
                } else {
                    break;
                }
            }
            //Si el nodo hijo esta lleno lo separa
            if (Node.sons.size2 == 5) {
                split(Node, j, Node.child[j]);
                if (value > Node.key[j]) {
                    j++;
                }
            }
            nonFullInsert(Node.child[j], value);
        }
    }

    //Print in preorder
    public void print(NodeTreeB n) {
        n.printNodeContent();

        //Si no es hoja
        if (!n.leaf) {
            //recorre los nodos para saber si tiene hijos
            for (int j = 0; j <= n.size; j++) {
                if (n.child[j] != null) {
                    System.out.println();
                    print(n.child[j]);
                }
            }
        }
    }
}
