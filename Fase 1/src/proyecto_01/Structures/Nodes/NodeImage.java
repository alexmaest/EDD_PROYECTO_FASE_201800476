package proyecto_01.Structures.Nodes;

import proyecto_01.Image;

/**
 *
 * @author Alexis
 */
public class NodeImage {

    public Image value;
    public NodeImage next;

    public NodeImage(Image value) {
        this.value = value;
        this.next = null;
    }
}
