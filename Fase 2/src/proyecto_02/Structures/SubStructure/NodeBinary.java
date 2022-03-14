package proyecto_02.Structures.SubStructure;

import proyecto_02.Layer;

/**
 *
 * @author Alexis
 */
public class NodeBinary {

    public Layer value;
    public NodeBinary left;
    public NodeBinary right;

    public NodeBinary(Layer value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
