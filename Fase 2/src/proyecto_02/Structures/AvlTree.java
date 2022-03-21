package proyecto_02.Structures;

import proyecto_02.Photo;
import proyecto_02.Structures.SubStructure.NodeAvl;

/**
 *
 * @author Alexis
 */
public class AvlTree {

    public NodeAvl root;
    public List images;

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
                        newFather = rotateDoubleLeft(subNode);
                    } else {
                        newFather = rotateDoubleRight(subNode);
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
                        newFather = rotateDoubleRight(subNode);
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

    public void preOrder(NodeAvl node) {
        if (node != null) {
            System.out.println(node.dato);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void inOrder(NodeAvl node) {
        if (node != null) {
            System.out.println(node.dato);
            inOrder(node.right);
            inOrder(node.left);
        }
    }

    public void postOrder(NodeAvl node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.dato);
        }
    }
    
    public void fillComboBox(NodeAvl node) {
        if (node != null) {
            images.addImage(node.dato);
            inOrder(node.right);
            inOrder(node.left);
        }
    }
}
