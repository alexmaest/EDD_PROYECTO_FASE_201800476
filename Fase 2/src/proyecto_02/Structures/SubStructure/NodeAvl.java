package proyecto_02.Structures.SubStructure;

import proyecto_02.Photo;

/**
 *
 * @author Alexis
 */
public class NodeAvl {

    public Photo dato;
    public int fe;
    public NodeAvl left, right;

    public NodeAvl(Photo d) {
        this.dato = d;
        this.fe = 0;
        this.left = null;
        this.right = null;
    }

    public void inOrder() {
        if (left != null) {
            left.inOrder();
        }
        System.out.println(dato.getId());
        if (right != null) {
            right.inOrder();
        }
    }

    public void postOrder() {
        if (left != null) {
            left.postOrder();
        }
        if (right != null) {
            right.postOrder();
        }
        System.out.println(dato);
    }
}
