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

    public NodeBinary obtainReplacingNode(NodeBinary replace){
        NodeBinary replaceFather = replace;
        NodeBinary replaceAux = replace;
        NodeBinary aux = replace.right;
        while(aux != null){
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