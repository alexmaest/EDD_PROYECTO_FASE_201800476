package proyecto_02.Structures;

import proyecto_02.Layer;
import proyecto_02.Structures.SubStructure.NodeBinary;

/**
 *
 * @author Alexis
 */
public class BinaryTree {
    
    NodeBinary root;
    
    public BinaryTree() {
        this.root = null;
    }

    //Insertar nodo en el arbol
    public void add(NodeBinary layer) {
        if (root == null) {
            this.root = layer;
        } else {
            NodeBinary aux = root;
            NodeBinary father;
            while (true) {
                father = aux;
                if (layer.value.getId()< aux.value.getId()) {
                    aux = aux.left;
                    if (aux == null) {
                        father.left = layer;
                        return;
                    }
                } else {
                    aux = aux.right;
                    if (aux == null) {
                        father.right = layer;
                        return;
                    }
                }
            }
        }
    }
    
    public void preOrder(NodeBinary node) {
        if (node != null) {
            System.out.println(node.value.getId());
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    
    public void inOrder(NodeBinary node) {
        if (node != null) {
            System.out.println(node.value.getId());
            inOrder(node.right);
            inOrder(node.left);
        }
    }
    
    public void postOrder(NodeBinary node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.value.getId());
        }
    }

    public NodeBinary getRoot() {
        return root;
    }
}
