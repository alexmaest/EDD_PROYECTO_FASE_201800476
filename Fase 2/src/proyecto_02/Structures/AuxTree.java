package proyecto_02.Structures;

import proyecto_02.Structures.Nodes.NodeTreeB;

/**
 *
 * @author Alexis
 */
public class AuxTree {

    NodeTreeB root;
    int t;

    //Constructor
    public AuxTree(int t) {
        this.t = t;
        root = new NodeTreeB();
    }

    public int buscarClaveMayor() {
        int claveMaxima = getClaveMayor(this.root);

        return claveMaxima;
    }

    private int getClaveMayor(NodeTreeB current) {
        if (current == null) {
            return 0;//Si es cero no existe
        }

        //Mientras no sea una hoja
        while (!current.leaf) {
            //Se accede al hijo mas a la derecha
            current = current.child[current.size];
        }

        return claveMayorPorNodo(current);
    }

    private int claveMayorPorNodo(NodeTreeB current) {
        //Devuelve el valor mayor, el que esta mas a la derecha
        return current.key[current.size - 1];
    }

    public void mostrarClavesNodoMinimo() {
        NodeTreeB temp = buscarNodoMinimo(root);

        if (temp == null) {
            System.out.println("Sin minimo");
        } else {
            temp.imprimir();
        }
    }

    public NodeTreeB buscarNodoMinimo(NodeTreeB nodoActual) {
        if (root == null) {
            return null;
        }

        NodeTreeB aux = root;

        //Mientras no sea una hoja
        while (!aux.leaf) {
            //Se accede al primer hijo
            aux = aux.child[0];
        }

        //Devuelve el nodo menor, el que esta mas a la izquierda
        return aux;
    }

    //Busca el valor ingresado y muestra el contenido del nodo que contiene el valor
    public void buscarNodoPorClave(int num) {
        NodeTreeB temp = search(root, num);

        if (temp == null) {
            System.out.println("No se ha encontrado un nodo con el valor ingresado");
        } else {
            print(temp);
        }
    }

    //Search
    private NodeTreeB search(NodeTreeB actual, int key) {
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

    public void insertar(int key) {
        NodeTreeB r = root;

        //Si el nodo esta lleno lo debe separar antes de insertar
        if (r.size == ((2 * t) - 1)) {
            NodeTreeB s = new NodeTreeB();
            root = s;
            s.leaf = false;
            s.size = 0;
            s.child[0] = r;
            split(s, 0, r);
            nonFullInsert(s, key);
        } else {
            nonFullInsert(r, key);
        }
    }

    // Caso cuando la raiz se divide
    // x =          | | | | | |
    //             /
    //      |10|20|30|40|50|
    // i = 0
    // y = |10|20|30|40|50|
    private void split(NodeTreeB x, int i, NodeTreeB y) {
        //Nodo temporal que sera el hijo i + 1 de x
        NodeTreeB z = new NodeTreeB();
        z.leaf = y.leaf;
        z.size = (t - 1);

        //Copia las ultimas (t - 1) claves del nodo y al inicio del nodo z      // z = |40|50| | | |
        for (int j = 0; j < (t - 1); j++) {
            z.key[j] = y.key[(j + t)];
        }

        //Si no es hoja hay que reasignar los nodos hijos
        if (!y.leaf) {
            for (int k = 0; k < t; k++) {
                z.child[k] = y.child[(k + t)];
            }
        }

        //nuevo tamanio de y                                                    // x =            | | | | | |
        y.size = (t - 1);                                                          //               /   \
        //  |10|20| | | |
        //Mueve los hijos de x para darle espacio a z
        for (int j = x.size; j > i; j--) {
            x.child[(j + 1)] = x.child[j];
        }
        //Reasigna el hijo (i+1) de x                                           // x =            | | | | | |
        x.child[(i + 1)] = z;                                                   //               /   \
        //  |10|20| | | |     |40|50| | | |
        //Mueve las claves de x
        for (int j = x.size; j > i; j--) {
            x.key[(j + 1)] = x.key[j];
        }

        //Agrega la clave situada en la mediana                                 // x =            |30| | | | |
        x.key[i] = y.key[(t - 1)];                                              //               /    \
        x.size++;                                                                  //  |10|20| | | |      |40|50| | | |
    }

    private void nonFullInsert(NodeTreeB x, int key) {
        //Si es una hoja
        if (x.leaf) {
            int i = x.size; //cantidad de valores del nodo
            //busca la posicion i donde asignar el valor
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i] = x.key[i - 1];//Desplaza los valores mayores a key
                i--;
            }

            x.key[i] = key;//asigna el valor al nodo
            x.size++; //aumenta la cantidad de elementos del nodo
        } else {
            int j = 0;
            //Busca la posicion del hijo
            while (j < x.size && key > x.key[j]) {
                j++;
            }

            //Si el nodo hijo esta lleno lo separa
            if (x.child[j].size == (2 * t - 1)) {
                split(x, j, x.child[j]);

                if (key > x.key[j]) {
                    j++;
                }
            }

            nonFullInsert(x.child[j], key);
        }
    }

    public void showBTree() {
        print(root);
    }

    //Print en preorder
    private void print(NodeTreeB n) {
        n.imprimir();

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